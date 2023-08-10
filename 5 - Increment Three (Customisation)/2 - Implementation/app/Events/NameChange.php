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

class NameChange implements ShouldBroadcast
{
    use Dispatchable, InteractsWithSockets, SerializesModels;

    public $roomCode;
    public $user;
    public $name;
    public $oldName;

    private $channels = array();

    /**
     * Create a new event instance.
     *
     * @return void
     */
    public function __construct($room, User $user, $name, $oldName)
    {
        $this->roomCode = $room->code;
        $this->user = $user;
        $this->name = $name;
        $this->oldName = $oldName;
        array_push($this->channels, new PrivateChannel($this->roomCode));
        foreach($room->users() as $roomUser) {
            array_push($this->channels, new PrivateChannel('index.' . $roomUser->id));
        }
    }

    /**
     * Get the channels the event should broadcast on.
     *
     * @return \Illuminate\Broadcasting\Channel|array
     */
    public function broadcastOn()
    {
        return $this->channels;
    }
}
