<div class="usersList p-3" 
    style="background-color:{{ $room->theme()->first()->backgroundColour }}">

    <!-- Cross Button -->
    <span onclick="onSpanBlockedButton()">&times;</span>

    <!-- Blocked Users -->
    @foreach ($room->blocked()->get() as $blocked)

        <div class='row g-0 align-items-center m-2'>

            <div class="col-auto">
                <img id="blocked{{ $blocked->id }}"  
                    src="{{  $blocked->avatarURL() }}" 
                    class="rounded-circle img-fluid me-2" alt="" 
                    style="height: 30px; width: 30px; max-height: 30px; 
                        max-width: 30px;">
            </div>

            <div class="col-auto">
                <span class="col-form-label">{{ $blocked->name }}</span>
            </div>

            <div class="col-auto">
                <button wire:click="unblock({{ $blocked->id }})"
                    class="text-secondary btn-danger ms-2">Unblock</button>
            </div>

        </div>

    @endforeach

</div>
