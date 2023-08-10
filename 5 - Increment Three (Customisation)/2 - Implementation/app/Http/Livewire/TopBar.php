<?php

namespace App\Http\Livewire;

use Livewire\Component;

class TopBar extends Component
{
    public $room;
    public $src;
    public $image;
    public $isModerator;

    public $listeners = ['render', 'topBarRender' => 'render', 'topBarSetImageURL' => 'setImageURL', 
        'setIsModerator'];

    public function render()
    {
        return view('livewire.top-bar');
    }

    public function mount($room) {
        $this->room = $room;
        $this->setImageURL($this->room->imageURL());
        $this->setIsModerator();
    }

    public function setImageURL($imageURL) {
        $this->src = $imageURL;
        $this->image++;
    }

    public function setIsModerator() {
        $this->isModerator = $this->room->isModerator();
    }
}
