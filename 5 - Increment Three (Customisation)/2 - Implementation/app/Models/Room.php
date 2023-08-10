<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Room extends Model
{
    use HasFactory;

    public function imageURL() {
        $imageURL = 'storage/avatars/rooms/'.$this->code.'.jpg';
        if (file_exists(public_path($imageURL))) {
            return asset($imageURL);
        } else {
            return asset('storage/avatars/default.jpg');
        }
    }

    public function users() {
        return $this->admin()->get()->merge(
            $this->moderators()->get()->merge(
            $this->participants()->get()));
    }

    public function admin() {
        return $this->belongsTo(User::class);
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

    public function theme() {
        return $this->belongsTo(Theme::class);
    }

    public function setModerator($id, $isModerator) {
        $this->belongsToMany(User::class)
            ->updateExistingPivot($id, ['isModerator' => $isModerator]);
    }

    public function isModerator() {
        return $this->moderators()->get()->contains('id', auth()->user()->id);;
    }

    public function block($id) {
        $this->belongsToMany(User::class)
            ->updateExistingPivot($id, ['isModerator' => false, 'isBlocked' => true]);
    }

    public function unblock($id) {
        $this->belongsToMany(User::class)->detach($id);
    }

}
