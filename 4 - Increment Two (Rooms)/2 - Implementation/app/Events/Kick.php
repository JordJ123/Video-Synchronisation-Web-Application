<?php

namespace App\Events;

use Illuminate\Broadcasting\Channel;
use Illuminate\Broadcasting\InteractsWithSockets;
use Illuminate\Broadcasting\PresenceChannel;
use Illuminate\Broadcasting\PrivateChannel;
use Illuminate\Contracts\Broadcasting\ShouldBroadcast;
use Illuminate\Foundation\Events\Dispatchable;
use Illuminate\Queue\SerializesModels;
use App\Models\User;

class Kick implements ShouldBroadcast
{
    use Dispatchable, InteractsWithSockets, SerializesModels;

    public $roomCode;
    public $roomName;
    public $user;
    public $id;

    /**
     * Create a new event instance.
     *
     * @return void
     */
    public function __construct($room, User $user, $id)
    {
        $this->roomCode = $room->code;
        $this->roomName = $room->name;
        $this->user = $user;
        $this->id = $id;
    }
    
    /**
     * Get the channels the event should broadcast on.
     *
     * @return \Illuminate\Broadcasting\Channel|array
     */
    public function broadcastOn()
    {
        return [
            new PrivateChannel($this->roomCode),
            new PrivateChannel('index.' . $this->id)
        ];
    }
}
