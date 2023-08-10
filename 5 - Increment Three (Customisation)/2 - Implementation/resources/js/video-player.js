window.onload = function() {
    Livewire.emit('newUser');
    $.ajax({
        type: 'GET',
        headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
        url: window.location.href + "/video",
        success: function () {
            document.getElementById("source").setAttribute('src', window.location.href + "/video");
            document.getElementById("video").load();
            document.getElementById("video").currentTime = 0;
            Livewire.emit('join');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            document.getElementById("source").removeAttribute("src");
            document.getElementById("video").load();
            document.getElementById("video").currentTime = 0;
        }
    });
    setSliderValue();
}

document.getElementById('video').addEventListener('ended', videoEnd, false);
function videoEnd(e) {
    Livewire.emit('setIsPlaying', false)
}

function play() {
    Livewire.emit('play', document.getElementById("video").currentTime);
    document.getElementById('video').play()
    
}

function pause() {
    Livewire.emit('pause', document.getElementById("video").currentTime);
    document.getElementById('video').pause()
}

function volume(volumeChange) {
    var currentVolume = document.getElementById('video').volume;
    if (document.getElementById('video').muted == true) {
        document.getElementById('video').muted
            = !document.getElementById('video').muted;
        currentVolume = 0;
    }
    currentVolume += volumeChange;
    if (currentVolume >= 0 && currentVolume <= 1) {
        document.getElementById("video").volume = currentVolume;
    } else {
        document.getElementById("video").volume = (currentVolume < 0) ? 0 : 1;
    }
}

function remove() {
    $.ajax({
        type: 'DELETE',
        headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
        url: window.location.href + "/video",
        success: function () {
            document.getElementById("source").removeAttribute("src");
            document.getElementById("video").load();
            document.getElementById("video").currentTime = 0;
            Livewire.emit('remove');
            alert("Deletion Successful");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });   
}

function slider() {
    let video = document.getElementById('video');
    let percentage = document.getElementById('slider').value / 100;
    video.currentTime = video.duration * percentage;
    Livewire.emit('slider', document.getElementById("video").currentTime);
}

jQuery('#fileForm').submit(function(e) {
    e.preventDefault();
    if (document.getElementById('file').value != "") {
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
            url: window.location.href,
            processData: false,
            contentType: false,
            cache: false,
            data: new FormData(this),
            success: function () {
                document.getElementById("source").setAttribute('src', window.location.href + "/video");
                document.getElementById("video").load();
                document.getElementById("video").currentTime = 0;
                Livewire.emit('upload')
                alert("Upload Successful");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Error: ' + jqXHR.responseText)
            }
        });
    } else {
        alert("Please select a file to upload");
    }
})

async function setSliderValue() {
    while (true) {
        await new Promise(r => setTimeout(r, 100));
        if (document.getElementById('slider') != null) {
            let slider = document.getElementById("slider");
            let video = document.getElementById("video");
            slider.value = Math.ceil((video.currentTime / video.duration) * 100)
        }   
    }
}