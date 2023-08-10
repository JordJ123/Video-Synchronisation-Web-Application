<div class="mb-4">
    <form wire:submit.prevent="change">
        
        <!-- Title -->
        <h4 class="text-primary"><b>Change Password</b></h4>

        <!-- Messages -->
        @if ($isSuccess)
            <div class="text-nowrap mb-3">
                <span class="text-success"><b>Password has been changed successfully</b></span>
            </div>
        @endif
        @if (count($errorMessages) != 0)
            <div class="text-nowrap mb-1">
                <span class="text-danger">Errors:</span>
                <ul>
                    @foreach($errorMessages as $errorMessage)
                        <li class="text-danger">{{ $errorMessage }}</li>
                    @endforeach
                </ul>
            </div>
        @endif
        
        <!-- Current Password -->
        <div class="row g-3 align-items-center mb-3">
            <div class="col-auto">
                <label for="current" class="col-form-label text-primary">Current Password</label>
            </div>
            <div class="col-auto">
                <input type="password" class="form-control" name="current" wire:model="current" required maxlength="16">
            </div>
        </div>

        <!-- New Password -->
        <div class="row g-3 align-items-center mb-3">
            <div class="col-auto">
                <label for="new" class="col-form-label text-primary">New Password</label>
            </div>
            <div class="col-auto">
                <input type="password" class="form-control" name="new" wire:model="new" required maxlength="16">
            </div>
        </div>

        <!-- Confirm New Password -->
        <div class="row g-3 align-items-center mb-3">
            <div class="col-auto">
                <label for="confirm" class="col-form-label text-primary">Confirm New Password</label>
            </div>
            <div class="col-auto">
                <input type="password" class="form-control" name="confirm" wire:model="confirm" required maxlength="16">
            </div>
            <div class="col-auto">
                <button type="submit" class="text-secondary btn-primary">Change Password</button>
            </div>
        </div>

    </form>
</div>