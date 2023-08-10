<?php

namespace App\Http\Livewire;

use Livewire\Component;

class MessageBox extends Component
{

    public $messages = array();
    public $textBox = "";

    public function post() 
    {
        array_push($this->messages, auth()->user()->name . ": " . $this->textBox);
        $this->textBox = "";
    }

    public function render()
    {
        return view('livewire.message-box');
    }

}
