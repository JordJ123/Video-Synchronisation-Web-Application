<x-layouts.app>

    <style>
        body {
            background-color: #3490DC;
        }
    </style>

    <div class="bg-primary text-center position-absolute top-50 start-50 p-3 
        translate-middle">
        <img class="img-fluid mx-auto d-block" src="{{ URL::asset('logo.png') }}"/>
        <h1 class="text-secondary text-center">Home</h1>
        <a class="btn btn-secondary m-1" href="{{ route('login') }}">Sign In</a>
        <a class="btn btn-secondary m-1" href="{{ route('register') }}">Register Account</a>
    </div>

</x-layouts.app>