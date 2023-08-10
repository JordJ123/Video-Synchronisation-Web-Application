@extends('layouts.app')

@section('content')
    
    <div class="section">
        <h1>{{ $room->name }}</h1>
    </div>    

    <div class="section" style="display:flex;">
        <div class="subsection">
            <video id="video" width='384' height='216' controls>
                <source src="{{ URL::asset('videos/'.$room->code.'.mp4') }}" 
                type="video/mp4"></source>
            </video>
        </div>
        <livewire:message-box />        
    </div>
                      
    </div>

    <div class="section">
        <form id='fileForm' enctype='multipart/form-data'>
            @csrf
            <input id='file' type="file" name="file">
            <input type="submit" value="Upload">
        </form>
    </div>

    <script>
        jQuery('#fileForm').submit(function(e) {
            e.preventDefault();
            if (document.getElementById('file').value != "") {
                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                    url: "{{ route('rooms.storeVideo', ['code' => $room->code]) }}",
                    processData: false,
                    contentType: false,
                    cache: false,
                    data: new FormData(this),
                    success: function () {
                        alert("Upload Successful");
                        document.getElementById("video").load();
                        document.getElementById("video").currentTime = 0;
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert('Error: ' + jqXHR.responseText)
                    }
                });
            } else {
                alert("Please select a file to upload");
            }
        });
    </script>

    @livewireScripts

@endsection