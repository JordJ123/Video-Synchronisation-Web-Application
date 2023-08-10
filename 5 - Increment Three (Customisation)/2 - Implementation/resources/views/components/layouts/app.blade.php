<!doctype html>
<html lang="en">

    <head>

        <title>VideoSync</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="{{ URL::asset('favicon.png') }}">
        <meta name="csrf-token" content="{{ csrf_token() }}" />

        <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
        <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="{{ asset('js/app.js') }}"></script>
        @livewireScripts

        <link href="{{ asset('css/app.css') }}" rel="stylesheet">
        @livewireStyles

    </head>
    
    <body>

        <div id="content" class="containter" style="background-color:{{ $theme ?? '#ffffff' }}">

            {{ $slot }}

        </div>

    </body>

</html>