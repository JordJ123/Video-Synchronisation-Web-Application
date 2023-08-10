<div class="overflow-hidden d-flex flex-column px-2 py-3 vh-100 bg-primary">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        body {
            min-height: 100vh;
            min-height: -webkit-fill-available;
        }

        html {
            height: -webkit-fill-available;
        }

        .b-example-divider {
            flex-shrink: 0;
            width: 1.5rem;
            height: 100vh;
            background-color: rgba(0, 0, 0, .1);
            border: solid rgba(0, 0, 0, .15);
            border-width: 1px 0;
            box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
        }

        .bi {
            vertical-align: -.125em;
            pointer-events: none;
            fill: currentColor;
        }

        .dropdown-toggle { 
            outline: 0; 
        }

        .nav-flush .nav-link {
            border-radius: 0;
        }

        .btn-toggle {
            display: inline-flex;
            align-items: center;
            padding: .25rem .5rem;
            font-weight: 600;
            color: rgba(0, 0, 0, .65);
            background-color: transparent;
            border: 0;
        }
        
        .btn-toggle:hover, .btn-toggle:focus {
            color: rgba(0, 0, 0, .85);
            background-color: #d2f4ea;
        }

        .btn-toggle::before {
            width: 1.25em;
            line-height: 0;
            content: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='16'" 
                + "height='16' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='rgba%280,0,0,.5%29'"
                + "stroke-linecap='round' stroke-linejoin='round' stroke-width='2' "
                + "d='M5 14l6-6-6-6'/%3e%3c/svg%3e");
            transition: transform .35s ease;
            transform-origin: .5em 50%;
        }

        .btn-toggle[aria-expanded="true"] {
            color: rgba(0, 0, 0, .85);
        }

        .btn-toggle[aria-expanded="true"]::before {
            transform: rotate(90deg);
        }

        .btn-toggle-nav a {
            display: inline-flex;
            padding: .1875rem .5rem;
            margin-top: .125rem;
            margin-left: 1.25rem;
            text-decoration: none;
        }

        .btn-toggle-nav a:hover, .btn-toggle-nav a:focus {
            background-color: #d2f4ea;
        }

        .scrollarea {
            overflow-x: hidden;
            overflow-y: auto;
        }

        .fw-semibold {
            font-weight: 600; 
        }

        .lh-tight { 
            line-height: 1.25; 
        }

        .item {
            padding: 8px 10px;
            font-size: 15px;
            color: #eee;
        }
        
    </style>

    <!-- Title -->
    <a href="{{ route('rooms.index') }}" class="m-0 text-center text-secondary text-decoration-none ">
        <img id="imageUser" src="{{ auth()->user()->avatarURL() }}" 
            class="rounded-circle img-fluid m-1" alt="" 
            style="height: 60px; width: 60px; max-height: 60px; max-width: 60px;">
            <div class="">Rooms</div>
    </a>
    
    <hr class="bg-secondary text-secondary">

    <!-- Rooms -->
    <script> let rooms = []; let count = 0;</script>
    <ul class="scrollarea flex-column flex-nowrap px-1 mb-auto w-100">
        @foreach (auth()->user()->rooms() as $sideRoom)
            @if (!$sideRoom->blocked()->find(auth()->user()->id))
                <li id = "room{{ $sideRoom->id}}" class="d-flex align-items-center mb-1">
                    @if ($sideRoom->code == $highlightRoom)
                        @php
                            $border = "border border-3 border-warning"
                        @endphp
                    @else
                        @php
                            $border = ""
                        @endphp
                    @endif
                    <a href="{{ route('rooms.show', ['code' => $sideRoom->code]) }}" 
                        class="px-1 py-1 text-center me-1 " 
                        aria-current="page" id="{{ $sideRoom->code }}">
                        <img id="image{{ $sideRoom->code }}" src="{{ $sideRoom->imageURL() }}" class="rounded-circle img-fluid m-1 {{ $border }}"
                            alt="" style="height: 60px; width: 60px; max-height: 60px; max-width: 60px;">
                    </a>
                    <div class="bg-secondary p-1"
                        style="position: fixed; z-index: 10000; border-radius: 5px; display: none;" id="roomMenu{{ $sideRoom->id}}">
                        <span>{{ $sideRoom->name }}</span>
                        <button wire:click="delete({{ $sideRoom->id }}, '{{ $sideRoom->code == $roomCode }}')" 
                            class="px-2 py-1 btn btn-danger text-secondary">X</button>
                    </div>
                    <script>
                        rooms[count] = document.getElementById("roomMenu{{ $sideRoom->id}}");
                        document.getElementById("room{{ $sideRoom->id}}")
                            .addEventListener("contextmenu", (event) => {
                            rooms.forEach((room) => {
                                if (event.target.offsetParent != room) {
                                    room.style.display = "none";
                                }
                            })
                            var roomMenu = document.getElementById("roomMenu{{ $sideRoom->id}}");
                            event.preventDefault();
                            const { clientX: mouseX, clientY: mouseY } = event;
                            roomMenu.style.top = `${mouseY}px`;
                            roomMenu.style.left = `${mouseX}px`;
                            roomMenu.style.display = "block";
                        });
                        document.querySelector("body").addEventListener("click", (event) => {
                            rooms.forEach((room) => {
                                if (event.target.offsetParent != room) {
                                    room.style.display = "none";
                                }
                            })
                        });
                        count++;
                    </script>
                </li>
            @endif
        @endforeach  
    </ul>
    
    <hr class="bg-secondary text-secondary">

    <!--- Settings Button -->
    <div class="dropdown text-center">
        <a href="{{ route('users.show') }}" class="text-secondary btn btn-warning mb-1 w-100">Settings</a>
    </div>

    <!-- Logout Button -->
    <div class="dropdown text-center">
        <form method="POST" action="{{ route('logout') }}">
            @csrf
            <button type="submit" class="text-secondary btn btn-danger mt-1 w-100">Logout</button>
        </form>
    </div>

</div>