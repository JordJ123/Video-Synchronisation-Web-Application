<?php

namespace App\Http\Livewire;

use Livewire\Component;
use App\Models\Room;
use App\Events\Close;

class SideBar extends Component
{
    public $listeners = ['sideBarRender' => 'render'];
    
    public $roomCode;

    public function render()
    {
        return view('livewire.side-bar');
    }

    public function mount($roomCode) {
        $this->roomCode = $roomCode;
    }

    public function delete($id, $isRedirect) {
        $room = Room::findOrFail($id);
        auth()->user()->rooms()->detach($room);
        if ($room->admin()->get()->first()->id == auth()->user()->id) {
            $close = new Close($room, auth()->user());
            broadcast($close)->toOthers();
            $room->delete();
        }
        if ($isRedirect) {
            return redirect()->route('rooms.index');
        }
    }
}
