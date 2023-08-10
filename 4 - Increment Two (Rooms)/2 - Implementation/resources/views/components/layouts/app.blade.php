<!doctype html>
<html lang="en">

    <head>

        <title>VideoSync</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="{{ URL::asset('favicon.png') }}">
        <meta name="csrf-token" content="{{ csrf_token() }}" />

        <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
        <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
        <script src="{{ asset('js/app.js') }}"></script>
        @livewireScripts

        <link href="{{ asset('css/app.css') }}" rel="stylesheet">
        @livewireStyles

    </head>
    
    <body>

        <div id="content" class="containter">
            
            @if (session('message'))
                <div>
                    <p><b>{{ session('message') }}</b></p>
                </div>
            @endif

            {{ $slot }}

        </div>

    </body>

</html>