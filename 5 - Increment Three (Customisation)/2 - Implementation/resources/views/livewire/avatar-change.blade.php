<div class="mb-4">
        
    <!-- Title -->
    <h4 class="text-primary"><b>Change User Avatar</b></h4>

    <!-- Error -->
    @error('avatar') <span class="text-danger">{{ $message }}</span> @enderror

    <!-- User Avatar -->
    <div class="row g-3 align-items-center mb-3">
        <div class="col-auto">
            <label for="file" class="col-form-label text-primary">User Avatar</label>
        </div>
        <div class="col-auto">
            <input wire:model="avatar" type="file" class="form-control" name="file" 
                id="fileUpload{{ $fileUpload }}" required>
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
            <button class="text-secondary btn-primary" wire:click="save">Upload</button>
        </div>
    </div>

</div>