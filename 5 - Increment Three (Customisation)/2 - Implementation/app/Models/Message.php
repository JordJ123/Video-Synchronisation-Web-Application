<?php

namespace App\Models;

class Message
{
    public User $user;
    public $text;

    public function __construct($id, User $user, $text)
    {
        $this->id = $id;
        $this->user = $user;
        $this->text = $text;
    }

    public function toArray() {
        return [
            'id' => $this->id,
            'userName' => $this->user->name,
            'userAvatar' => $this->user->avatarURL(),
            'text' => $this->text
        ];
    }
}
