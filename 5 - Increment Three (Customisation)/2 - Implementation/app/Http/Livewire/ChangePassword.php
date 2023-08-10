<?php

namespace App\Http\Livewire;

use Livewire\Component;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\Rules;
use App\Rules\MatchCurrentPassword;

class ChangePassword extends Component
{
    public $isSuccess = false;
    public $errorMessages = array();
    public $current = ""; 
    public $new = "";
    public $confirm = ""; 

    public function render()
    {
        return view('livewire.change-password');
    }

    public function change() {
        $this->errorMessages = array();
        $validator = Validator::make(
            [
                'current_password' => $this->current, 
                'new_password' => $this->new, 
                'new_password_confirmation' => $this->confirm
            ], 
            [
                'current_password' => ['required', Rules\Password::defaults(), new MatchCurrentPassword],
                'new_password' => ['required', 'confirmed', Rules\Password::defaults()],
            ]
        );
        if (!$validator->fails()) {
            auth()->user()->password = Hash::make($this->new);
            auth()->user()->save();
            $this->isSuccess = true;
        } else {
            foreach($validator->messages()->all() as $errorMessage) {
                array_push($this->errorMessages, $errorMessage);
            }
        }
        $this->current = "";
        $this->new = "";
        $this->confirm = "";
    }
}
