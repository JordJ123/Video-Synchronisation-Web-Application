@if ($errors->any()) 
    <div class="text-nowrap" style="color:red">
        Errors:
        <ul>
            @foreach ($errors->all() as $error)
                <li>{{ $error }}</li>
            @endforeach
        </ul>
    </div>
@endif