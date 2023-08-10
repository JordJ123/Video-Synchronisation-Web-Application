<x-layouts.room :room="$room">

    <style>
        .userBox {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }    
        .usersList {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            border: 1px solid #888;
            overflow: hidden;
            width: 75%; /* Could be more or less, depending on screen size */
        }
    </style>

    <!-- Top Bar -->
    <livewire:top-bar :room="$room"/>

    <!-- Main Section -->
    <div class="d-flex">
        <livewire:video-player :room="$room"/>
        <livewire:message-box roomCode="{{ $room->code }}"/>     
    </div>

    <!-- Pop-Ups -->
    <div id="usersBox" class="userBox">
        <livewire:users-box :room="$room"/>
    </div>
    <div id="blockedBox" class="userBox">
        <livewire:blocked-box :room="$room"/>
    </div>

    <script type="text/javascript" src="{{ asset('js/rooms.show.js') }}"></script>

</x-layouts.room>