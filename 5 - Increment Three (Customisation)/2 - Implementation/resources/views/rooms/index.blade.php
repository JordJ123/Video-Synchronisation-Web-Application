<x-layouts.room>

    <div>

        <h1 id="userId" hidden>{{ auth()->user()->id }}</h1>

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
        
        <div class="mb-4">
            <h1 class="text-primary"><b>Create Room</b></h1>
            <form method="POST" action="{{ route('rooms.store') }}">
                @csrf
                <div class="row g-3 align-items-center">
                    <div class="col-auto">
                        <label for="name" class="col-form-label text-primary">Room Name</label>
                    </div>
                    <div class="col-auto">
                        <input type="text" class="form-control" name="name" 
                            value="{{ old('name') }}" maxlength="16" required>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="text-secondary btn-primary">Create</button>
                    </div>
                </div>
            </form>
        </div>

        <div class="mb-4">
            <h1 class="text-primary"><b>Join Room</b></h1>
            <form id="codeForm" method="GET" 
                action="{{ route('rooms.show', ['code' => '/']) }}">
                @csrf
                <div class="row g-3 align-items-center">
                    <div class="col-auto">
                        <label for="code" class="col-form-label text-primary">Room Code</label>
                    </div>
                    <div class="col-auto">
                        <input id="code" type="text" name="code" class="form-control" 
                            value="{{ old('code') }}" maxlength="8" required>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="text-secondary btn-primary">Join</button>
                    </div>
                </div>
            </form>
        </div>

    </div>

    <script type="text/javascript" src="{{ asset('js/rooms.index.js') }}"></script>

</x-layouts.room>