<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Events;
use App\Models;

class MessageBox extends Component
{

    public $room;
    public $roomCode;
    public $messages = array();
    public $messageId = 0;
    public $textBox = "";

    protected $listeners = ['render', 'retrieve'];

    public function render()
    {
        return view('livewire.message-box');
    }

    public function mount($room) {
        $this->room = $room;
        $this->roomCode = $room->code;
    }

    public function post() 
    {
        $message = new Models\Message($this->messageId, auth()->user(), 
            $this->textBox);
        array_push($this->messages, $message->toArray());
        broadcast(new Events\Message($this->roomCode, auth()->user(), 
            $message->toArray()))->toOthers();
        $this->messageId++;
        $this->textBox = "";
    }

    public function retrieve($message) {
        array_push($this->messages, $message);
    }

}
