<?php

namespace App\Models;

use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Sanctum\HasApiTokens;
use App\Events\Close;
use Illuminate\Support\Facades\Storage;

class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var string[]
     */
    protected $fillable = [
        'name',
        'email',
        'password',
    ];

    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    /**
     * The attributes that should be cast.
     *
     * @var array
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];

    public function avatarURL() {
        $imageURL = 'storage/avatars/users/'.auth()->user()->id.'.jpg';
        if (file_exists(public_path($imageURL))) {
            return asset($imageURL);
        } else {
            return asset('storage/avatars/default.jpg');
        }
    }

    public function rooms() {
        return $this->belongsToMany(Room::class, 'room_user')->get()->merge(
            $this->hasMany(Room::class, 'admin_id')->get());
    }

    public function addRoom($roomId) {
        $this->belongsToMany(Room::class)->syncWithoutDetaching([$roomId]);
    }

    public function leaveRoom($room) {
        if ($room->admin()->first()->id == auth()->user()->id) {
            broadcast(new Close($room, auth()->user()))->toOthers();
            Storage::delete('public/avatars/rooms/' . $room->code . '.jpg');
            Storage::delete('videos/' . $room->code . '.mp4');
            $room->delete();
        } else {
            $this->belongsToMany(Room::class)->detach($room);
        }    
    }
}
