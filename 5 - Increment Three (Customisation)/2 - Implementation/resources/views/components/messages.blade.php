@if (session('message'))
    <div class="text-nowrap mb-2">
        <span class="text-success"><b>{{ session('message') }}</b></span>
    </div>
@endif

@if ($errors->any()) 
    <div class="text-nowrap mb-2">
        <span class="text-danger">Errors:</span>
        <ul>
            @foreach ($errors->all() as $error)
                <li class="text-danger">{{ $error }}</li>
            @endforeach
        </ul>
    </div>
@endif