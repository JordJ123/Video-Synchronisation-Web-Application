<?php

namespace App\Http\Livewire;

use Livewire\Component;

class TopBar extends Component
{
    public $room;
    public $isModerator;

    public $listeners = ['setIsModerator'];

    public function render()
    {
        return view('livewire.top-bar');
    }

    public function mount($room) {
        $this->room = $room;
        $this->setIsModerator();
    }

    public function setIsModerator() {
        $this->isModerator = $this->room->isModerator();
    }
}
