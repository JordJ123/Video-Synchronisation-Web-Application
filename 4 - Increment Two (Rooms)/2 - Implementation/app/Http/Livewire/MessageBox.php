<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Events\Message;

class MessageBox extends Component
{

    public $roomCode;
    public $messages = array();
    public $textBox = "";

    protected $listeners = ['retrieve' => 'retrieve'];

    public function render()
    {
        return view('livewire.message-box');
    }

    public function post() 
    {
        array_push($this->messages, auth()->user()->name . ": " . $this->textBox);
        broadcast(new Message($this->roomCode, auth()->user(), $this->textBox))->toOthers();
        $this->textBox = "";
    }

    public function retrieve($message) {
        array_push($this->messages, $message);
    }

}
