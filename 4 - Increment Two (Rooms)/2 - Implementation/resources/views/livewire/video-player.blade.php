<div class="flex-fill w-50 me-3 mh-100">
    
    <div class="mb-2 ratio ratio-16x9">
        <video id='video' muted=true>
            <source id="source" type="video/mp4"></source>
        </video>
    </div>

    @if($isAdmin || $isModerator)

        @if($isVideo)
            <div class="d-flex flex-wrap">
                @if($isPlaying)
                    <button class="btn-primary text-secondary me-2 mb-2" type='button' id="pause" 
                        onclick="pause()">Pause</button>
                @else
                    <button class="btn-primary text-secondary me-2 mb-2" type='button' id="play" 
                        onclick="play()">Play</button>
                @endif 
                <button class="btn-primary text-secondary me-2 mb-2" type='button' onclick='volume(-.1)'>
                    Vol-</button>
                <button class="btn-primary text-secondary me-2 mb-2" type='button' onclick='volume(+.1)'>
                    Vol+</button>
                <button class="btn-danger text-secondary me-2 mb-2" type='button' onclick='remove()'>
                    Remove</button>   
            </div>        
            <div class="mb-1">
                <input class="form-range" id="slider" type="range" 
                    min="0" max="100" value="0" oninput='slider()'>
            </div>
        @endif    

        <div>
            <form id='fileForm' enctype='multipart/form-data'>
                @csrf
                <div class="d-flex">
                    <input id='file' class="form-control me-2" type="file" name="file">
                    <button type="submit" class="text-secondary btn-primary">Upload</button>
                </div>
            </form>
        </div>

    @endif


    <script type="text/javascript" src="{{ asset('js/video-player.js') }}"></script>
  
</div>

