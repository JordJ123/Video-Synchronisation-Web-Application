@extends('layouts.app')

@section('content')

    <script>
        jQuery(document).ready(function() {    
            jQuery("#codeForm").on('submit',function(e){
                e.preventDefault();
                var code = jQuery("#code").val();
                window.location.href 
                    = jQuery(this).prop('action') +"/" + encodeURIComponent(code)        
            });
        });
    </script>
    
    <div class="section">
        <h1>Create Room</h1>
        <form method="POST" action="{{ route('rooms.store') }}">
            @csrf
            Room Name <input type="text" name="name" value="{{ old('name') }}">
            <input type="submit" value="Create">
        </form>
    </div>

    <div class="section">
        <h1>Join Room</h1>
        <form id="codeForm" method="GET" 
            action="{{ route('rooms.show', ['code' => '/']) }}">
            @csrf
            Room Code <input id="code" type="text" name="code" 
                value="{{ old('code') }}" required>
            <input type="submit" value="Join">
        </form>
    </div>

    <div class="section">
        <form method="POST" action="{{ route('logout') }}">
            @csrf
            <input type="submit" value="Logout">
        </form>
    </div>

@endsection