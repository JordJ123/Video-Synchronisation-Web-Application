<div class="d-flex align-items-center">
    <h1 class="text-primary m-0 me-2">{{ $room->name }}</h1>
    <h1 id="roomCode" hidden>{{ $room->code }}</h1>
    <h1 id="userId" hidden>{{ auth()->user()->id }}</h1>
    <h1 id="close" hidden>{{ route('rooms.index', ['closed' => true]) }}</h1>
    <h1 id="kick" hidden>{{ route('rooms.index', ['kicked' => true]) }}</h1>
    <input class="img-fluid h-100" onclick="onUsersButton()" type="image" 
        src="{{ URL::asset('users.png') }}"/>
    @if ($room->admin()->get()->first()->id == auth()->user()->id || $isModerator)
        <input class="img-fluid h-100" onclick="onBlockedButton()" type="image" 
            src="{{ URL::asset('blocked.png') }}"/>
    @endif
</div>
