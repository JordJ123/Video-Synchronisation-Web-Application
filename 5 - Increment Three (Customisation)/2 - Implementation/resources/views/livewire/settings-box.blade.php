<div class="usersList p-3" style="background-color:{{ $room->theme()->first()->backgroundColour }}">

    <!-- Exit Button -->
    <div class="d-flex">
        <span onclick="onSpanSettingsButton()">&times;</span>
        <h3 class="mb-0 ms-3" style="color:{{ $room->theme()->first()->foregroundColour }}">Settings</h3>
    </div>

    <!-- Name Change Box -->
    <div class="row g-2 align-items-center m-1 ms-4">
        <div class="col-auto">
            <label for="name" class="col-form-label" 
                style="color:{{ $room->theme()->first()->foregroundColour }}">Room Name</label>
        </div>
        <div class="col-auto">
            <input wire:model="name" type="text" class="form-control" name="name" 
                value="{{ old('name') }}" maxlength="16" required>
        </div>
        <div class="col-auto">
            <button class="ms-1" wire:click="nameSave"
                style="color:{{ $room->theme()->first()->buttonForegroundColour }};
                    background-color:{{ $room->theme()->first()->buttonBackgroundColour }};
                    border-color:{{ $room->theme()->first()->buttonBackgroundColour }}">Save</button>
        </div>
    </div>

    <!-- Avatar Change Box -->
    <div class="row g-2 align-items-center m-1 ms-4">
        <div class="col-auto">
            <label for="file" class="col-form-label" 
                style="color:{{ $room->theme()->first()->foregroundColour }}">Room Avatar</label>
        </div>
        <div class="col-auto">
            <input wire:model="avatar" type="file" class="form-control" name="file" id="fileUpload{{ $fileUpload }}" required>
            @error('avatar') <span class="text-danger">{{ $message }}</span> @enderror
        </div>
        <div class="col-auto">
            @if ($avatar)
                <img src="{{ $avatar->temporaryUrl() }}" class="rounded-circle img-fluid m-1 mh-100" alt=""
                    style="height: 50px; width: 50px; max-height: 50px; max-width: 50px;"
                    id="temp{{ $fileUpload }}">
            @else 
                <img src="{{ $src }}" class="rounded-circle img-fluid m-1 mh-100" alt=""
                    style="height: 50px; width: 50px; max-height: 50px; max-width: 50px;"
                    id="main{{ $fileUpload }}">
            @endif
        </div>
        <div class="col-auto">
            <button class="ms-1" wire:click="avatarSave"
                style="color:{{ $room->theme()->first()->buttonForegroundColour }};
                    background-color:{{ $room->theme()->first()->buttonBackgroundColour }};
                    border-color:{{ $room->theme()->first()->buttonBackgroundColour }}">Upload</button>
        </div>
    </div>

    <!-- Theme Drop Down -->
    <div class="row g-2 align-items-center m-1 ms-4">
        <div class="col-auto">
            <label style="color:{{ $room->theme()->first()->foregroundColour }}" for="themes">
                Theme</label>
        </div>
        <div class="col-auto">
            <select wire:model="select" name="themes" class="form-select" 
                aria-label="Default select example">
                @foreach($themes as $theme)
                    @if ($room->theme()->first()->id == $theme->id)
                        <option value="{{ $theme->id }}">{{ $theme->name }}</option>
                    @else
                        <option value="{{ $theme->id }}">{{ $theme->name }}</option>
                    @endif
                @endforeach
            </select>
        </div>
        <div class="col-auto">
            <button class="ms-1" wire:click="themeSave"
                style="color:{{ $room->theme()->first()->buttonForegroundColour }};
                    background-color:{{ $room->theme()->first()->buttonBackgroundColour }};
                    border-color:{{ $room->theme()->first()->buttonBackgroundColour }}">Save</button>
        </div>
    </div>

    <script type="text/javascript" src="{{ asset('js/settings-box.js') }}"></script>

</div>
