<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Events\Unblock;

class BlockedBox extends Component
{
    public $room;

    public $listeners = ['render', 'blockedBoxRender' => 'render'];

    public function render()
    {
        return view('livewire.blocked-box');
    }

    public function mount($room) {
        $this->room = $room;
    }

    public function unblock($id) {
        $this->room->unblock($id);
        broadcast(new Unblock($this->room->code, auth()->user(), $id))->toOthers();
    }
}
