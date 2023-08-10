<div class="d-flex align-items-center">

    <!-- Avatar -->
    <img src="{{ $src }}" class="rounded-circle img-fluid m-1 mh-100" alt=""
        style="height: 50px; width: 50px; max-height: 50px; max-width: 50px;"
        id="{{ $image }}">

    <!-- Room Name -->
    <h1 class="m-0 ms-2 me-2" style="color:{{ $room->theme()->first()->foregroundColour }}">
        {{ $room->name }}</h1>

    <!-- Users Box Button -->
    <input class="img-fluid h-100 m-1" onclick="onUsersButton()" type="image" 
        src="{{ URL::asset('users.png') }}"/>

    <!-- Blocked Box Button -->
    @if ($room->admin()->get()->first()->id == auth()->user()->id || $isModerator)
        <input class="img-fluid h-100 m-1" onclick="onBlockedButton()" type="image" 
            src="{{ URL::asset('blocked.png') }}"/>
    @endif

    <!-- Settings Box Button -->
    @if ($room->admin()->get()->first()->id == auth()->user()->id || $isModerator)
        <input class="img-fluid h-100 m-1" onclick="onSettingsButton()" type="image" 
            src="{{ URL::asset('settings.png') }}"/>
    @endif

</div>
