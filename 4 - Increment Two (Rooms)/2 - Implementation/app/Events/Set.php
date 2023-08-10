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
use App\Helpers\Time;

class Set implements ShouldBroadcast
{
    use Dispatchable, InteractsWithSockets, SerializesModels;

    public $roomCode;
    public $joinedUserId;
    public $user;
    public $time;
    public $timeStamp;
    public $isPlaying;

    /**
     * Create a new event instance.
     *
     * @return void
     */
    public function __construct($roomCode, $joinedUser, User $user, $time, $timeStamp, $isPlaying)
    {
        $this->roomCode = $roomCode;
        $this->joinedUserId = $joinedUser['id'];
        $this->user = $user;
        $this->time = $time;
        $this->timeStamp = $timeStamp;
        $this->isPlaying = $isPlaying;
    }

    /**
     * Get the channels the event should broadcast on.
     *
     * @return \Illuminate\Broadcasting\Channel|array
     */
    public function broadcastOn()
    {
        return new PrivateChannel($this->roomCode . "." . $this->joinedUserId);
    }
}
