<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Events\Kick;
use App\Events\Moderator;
use App\Events\NewUser;

class UsersBox extends Component
{
    public $room;
    public $isModerator;

    public $listeners = ['render', 'usersBoxRender' => 'render', 'setIsModerator', 'newUser'];

    public function render()
    {
        return view('livewire.users-box');
    }

    public function mount($room) {
        $this->room = $room;
        $this->setIsModerator();
    }

    public function setIsModerator() {
        $this->isModerator = $this->room->isModerator();
    }

    public function newUser() {
        broadcast(new NewUser($this->room->code, auth()->user()))->toOthers();
    }

    public function moderator($id, $isModerator) {
        $this->room->setModerator($id, $isModerator);
        broadcast(new Moderator($this->room->code, auth()->user(), $id, $isModerator))->toOthers();
    }

    public function kick($id) {
        $this->room->block($id);
        $this->emit('blockedBoxRender');
        broadcast(new Kick($this->room, auth()->user(), $id))->toOthers();
    }

}
