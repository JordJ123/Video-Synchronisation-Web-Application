<?php

namespace App\Http\Livewire;

use Livewire\Component;
use Livewire\WithFileUploads;
use App\Models\Theme;
use App\Events\NameChange;
use App\Events\RoomAvatar;
use App\Events\ThemeChange;
use Intervention\Image\ImageManagerStatic as Image;

class SettingsBox extends Component
{
    use WithFileUploads;

    public $room;
    public $name = "";
    public $oldName = "";
    public $avatar;
    public $fileUpload = 0;
    public $src;
    public $srcUpdate;
    public $themes;
    public $select;

    public $listeners = ['nameReset', 'setSrcUpdate', 'settingsReset', 'settingsBoxSetImageURL' => 'setImageURL'];

    public function render()
    {
        
        return view('livewire.settings-box');
    }

    public function mount($room) 
    {
        $this->room = $room;
        $this->name = $room->name;
        $this->oldName = $room->name;
        $this->src = $this->room->imageURL();
        $this->srcUpdate = $this->room->imageURL(); 
        $this->themes = Theme::get();
        $this->select = $room->theme()->first()->id;
    }

    public function setSrcUpdate($srcUpdate) {
        $this->srcUpdate = $srcUpdate;
    }

    public function settingsReset() 
    {
        $this->name = $this->room->name;
        $this->oldName = $this->room->name;
        $this->src = $this->srcUpdate;
        $this->select = $this->room->theme()->first()->id;
        $this->avatar = null;
        $this->fileUpload++;
    }

    public function nameReset()
    {
        $this->name = $this->oldName;
    }

    public function nameSave() 
    {
        
        $this->room->name = $this->name;
        $this->room->save();
        $this->emit('sideBarRender');
        $this->emit('topBarRender');
        broadcast(new NameChange($this->room, auth()->user(), $this->room->name, 
            $this->oldName))->toOthers();
        $this->oldName = $this->room->name;
    }

    public function avatarSave()
    {
        $this->validate([
            'avatar' => 'image', // 1MB Max
        ]);
        Image::make($this->avatar)->resize(100, 100)->save(
            storage_path('app/public/avatars/rooms/').$this->room->code.".jpg");
        $this->setImageURL($this->avatar->temporaryUrl());
        $this->emit('sideBarRender');
        $this->emit('topBarSetImageURL', $this->src);
        broadcast(new RoomAvatar($this->room, auth()->user(), $this->src))->toOthers();
        $this->avatar = null;
        $this->fileUpload++;
    }

    public function setImageURL($imageURL) {
        $this->src = $imageURL;
    }

    public function themeSave() 
    {
        $this->room->theme_id = $this->select;
        $this->room->save();
        $this->emit('themeSave', $this->room->theme()->first()->backgroundColour);
        broadcast(new ThemeChange($this->room->code, auth()->user(), 
            $this->room->theme()->first()->backgroundColour))->toOthers();
    }
}
