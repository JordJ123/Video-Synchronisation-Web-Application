<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Events\Join;
use App\Events\Play;
use App\Events\Pause;
use App\Events\Upload;
use App\Events\Remove;
use App\Events\Set;
use App\Helpers\Time;

class VideoPlayer extends Component
{
    public $room;
    public $roomCode;
    public $isAdmin;
    public $isModerator;
    public $isVideo = false;
    public $isPlaying = false;

    public $listeners = ['render', 'setIsModerator', 'setIsVideo', 'setIsPlaying', 'join', 'set', 
        'play', 'pause', 'remove', 'upload', 'slider'];

    public function mount($room) {
        $this->room = $room;
        $this->roomCode = $room->code;
        $this->isAdmin = $room->admin()->get()->first()->id == auth()->user()->id;
        $this->setIsModerator();
    }

    public function render()
    {
        return view('livewire.video-player');
    }

    public function setIsModerator() {
        $this->isModerator = $this->room->isModerator();
    }

    public function setIsVideo($isVideo) 
    {
        $this->isVideo = $isVideo;
    }

    public function setIsPlaying($isPlaying) 
    {
        $this->isPlaying = $isPlaying;
    }

    public function join()
    {
        $this->setIsVideo(true);
        broadcast(new Join($this->roomCode, auth()->user()))->toOthers();
    }

    public function set($time, $joinedUser) {
        broadcast(new Set($this->roomCode, $joinedUser, auth()->user(), $time,
            Time::getMilliseconds(), $this->isPlaying))->toOthers();
    }

    public function play($time) 
    {
        broadcast(new Play($this->roomCode, auth()->user(), $time, Time::getMilliseconds()))
            ->toOthers();
        $this->setIsPlaying(true);
        
    }

    public function pause($time) 
    {
        broadcast(new Pause($this->roomCode, auth()->user(), $time))->toOthers();
        $this->setIsPlaying(false);
    }

    public function remove()
    {
        $this->setIsVideo(false);
        $this->setIsPlaying(false);
        broadcast(new Remove($this->roomCode, auth()->user()))->toOthers();
    }

    public function upload()
    {
        $this->setIsVideo(true);
        broadcast(new Upload($this->roomCode, auth()->user()))->toOthers();
    }

    public function slider($time) 
    {
        if ($this->isPlaying) {
            $this->play($time, Time::getMilliseconds());
        } else {
            $this->pause($time);
        }
    }
}
