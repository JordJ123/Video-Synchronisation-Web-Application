<div class="usersList p-3" 
    style="background-color:{{ $room->theme()->first()->backgroundColour }}">

    <!-- Cross Button -->
    <span onclick="onSpanButton()">&times;</span>

    <!-- Administrator -->
    <div class='row g-0 align-items-center m-2'>

        <div class="col-auto">
            <img id="admin{{ $room->admin()->get()->first()->id }}" 
                src="{{  $room->admin()->get()->first()->avatarURL() }}" 
                class="rounded-circle img-fluid me-2" alt="" 
                style="height: 30px; width: 30px; max-height: 30px; 
                    max-width: 30px;">
        </div>

        <div class="col-auto text-warning">
            <b>{{ $room->admin()->get()->first()->name }}</b>
        </div>
    
    </div>
    

    <!-- Moderators -->
    @foreach ($room->moderators()->get() as $moderator)

        <div class='row g-0 align-items-center m-2'>

            <div class="col-auto">
                <img id="moderator{{ $moderator->id }}" 
                    src="{{  $moderator->avatarURL() }}" 
                    class="rounded-circle img-fluid me-2" alt="" 
                    style="height: 30px; width: 30px; max-height: 30px; 
                        max-width: 30px;">
            </div>

            <div class="col-auto">
                <span class="col-form-label" style="color:{{ 
                    $room->theme()->first()->buttonBackgroundColour }}">
                    {{ $moderator->name }}
                </span>
            </div>

            @if ($room->admin()->get()->first()->id == auth()->user()->id 
                || ($isModerator && $moderator->id != auth()->user()->id))

                <div class="col-auto">
                    <button wire:click="moderator({{ $moderator->id }}, false)" 
                        class="text-secondary btn-danger ms-2">
                        Remove Moderator
                    </button>
                </div>

                <div class="col-auto">
                    <button wire:click="kick({{ $moderator->id }})"
                        class="text-secondary btn-danger ms-2">Kick</button>
                </div>

            @endif

        </div>

    @endforeach

    <!-- Participants -->
    @foreach ($room->participants()->get() as $participant)

        @if (!($room->admin()->get()->first()->id == $participant->id))
            
            <div class='row g-0 align-items-center m-2'>

                <div class="col-auto">
                    <img id="participant{{ $participant->id }}"  
                        src="{{  $participant->avatarURL() }}" 
                        class="rounded-circle img-fluid me-2" alt="" 
                        style="height: 30px; width: 30px; max-height: 30px; 
                            max-width: 30px;">
                </div>

                <div class="col-auto">
                    <span>{{ $participant->name }}</span>
                </div>
                
                @if ($room->admin()->get()->first()->id == auth()->user()->id 
                    || ($isModerator && $participant->id != auth()->user()->id))

                    <div class="col-auto">
                        <button 
                            wire:click="moderator({{ $participant->id }}, true)" 
                            class="text-secondary btn-primary ms-2">
                            Set Moderator
                        </button>
                    </div>

                    <div class="col-auto">
                        <button wire:click="kick({{ $participant->id }})"
                            class="text-secondary btn-danger ms-2">Kick</button>
                    </div>

                @endif

            </div>

        @endif

    @endforeach

</div>
