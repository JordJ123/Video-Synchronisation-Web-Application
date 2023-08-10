<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Room extends Model
{
    use HasFactory;

    public function admin() {
        return $this->belongsTo(User::class);
    }

    public function users() {
        return $this->belongsToMany(User::class)->withPivot(['isModerator', 'isBlocked']);
    }

    public function moderators() {
        return $this->belongsToMany(User::class)->wherePivot('isModerator', 1);
    }

    public function participants() {
        return $this->belongsToMany(User::class)->wherePivot('isModerator', 0)
            ->wherePivot('isBlocked', 0);
    }

    public function blocked() {
        return $this->belongsToMany(User::class)->wherePivot('isBlocked', 1);
    }

    public function isModerator() {
        return $this->moderators()->get()->contains('id', auth()->user()->id);;
    }

}
