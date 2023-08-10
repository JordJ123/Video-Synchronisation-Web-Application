<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Models\Room;

class SideBar extends Component
{
    public $listeners = ['sideBarRender' => 'render', 'highlightRoom'];
    
    public $roomCode;
    public $highlightRoom = "";

    public function render()
    {
        return view('livewire.side-bar');
    }

    public function mount($roomCode) {
        $this->roomCode = $roomCode;
    }

    public function delete($id, $isRedirect) {
        $room = Room::findOrFail($id);
        auth()->user()->leaveRoom($room);
        if ($isRedirect) {
            return redirect()->route('rooms.index');
        }
    }

    public function highlightRoom($roomCode) {
        $this->highlightRoom = $roomCode;
    }
}
