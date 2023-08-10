<?php

namespace App\Http\Livewire;

use Livewire\Component;
use Livewire\WithFileUploads;
use Intervention\Image\ImageManagerStatic as Image;

class AvatarChange extends Component
{
    use WithFileUploads;
    
    public $avatar;
    public $src;
    public $fileUpload = 0;

    public function render()
    {
        return view('livewire.avatar-change');
    }

    public function mount() {
        $this->src = auth()->user()->avatarURL();
    }

    public function save()
    {
        $this->validate([
            'avatar' => 'image', // 1MB Max
        ]);
        Image::make($this->avatar)->resize(100, 100)->save(
            storage_path('app/public/avatars/users/').auth()->user()->id.".jpg");
        $this->src = $this->avatar->temporaryUrl();
        $this->avatar = null;
        $this->fileUpload++;
    }
}
