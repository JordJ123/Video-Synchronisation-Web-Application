<div class="usersList p-3">

    <span onclick="onSpanBlockedButton()">&times;</span>

    <!-- Blocked Users -->
    @foreach ($room->blocked()->get() as $blocked)

        <div class='row g-0 align-items-center m-2'>

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
