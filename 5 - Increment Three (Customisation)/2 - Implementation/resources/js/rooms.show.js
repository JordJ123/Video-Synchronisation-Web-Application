window.Echo.private(document.getElementById("roomCode").textContent)
    .listen('NameChange', (e) => {
        Livewire.emit('sideBarRender');
        Livewire.emit('topBarRender');
        Livewire.emit('setOldName', e.name);
        alert("The adminstrator or a moderator has changed the room name")
    })
    .listen('RoomAvatar', (e) => {
        document.getElementById("image" + e.roomCode).src = e.imageUrl;
        Livewire.emit('topBarSetImageURL', e.imageUrl);
        Livewire.emit('setSrcUpdate', e.imageUrl);
        alert("The adminstrator or a moderator has changed the room avatar")
    })
    .listen('ThemeChange', (e) => {
        onThemeChange(e.colour);
        alert("The adminstrator or a moderator has changed the room theme")
    })
    .listen('NewUser', (e) => {
        Livewire.emit('usersBoxRender');
    })
    .listen('Join', (e) => {
        Livewire.emit('set', document.getElementById("video").currentTime, e.user);
    })
    .listen('Moderator', (e) => {
        if (e.id == document.getElementById("userId").textContent) {
            Livewire.emit('setIsModerator');
            if (e.isModerator == false) {
                document.getElementById("blockedBox").style.display = "none";
                document.getElementById("settingsBox").style.display = "none";
            }
        } else {
            Livewire.emit('render');
        }
    })
    .listen('Kick', (e) => {
        if (e.id == document.getElementById("userId").textContent) {
            window.location.assign(document.getElementById("kick").textContent);
        } else {
            Livewire.emit('blockedBoxRender');
            Livewire.emit('usersBoxRender');
        }
    })
    .listen('Unblock', (e) => {
        Livewire.emit('blockedBoxRender');
    })
    .listen('Play', (e) => {
        setPlay(e);
        Livewire.emit('setIsPlaying', true);
    })
    .listen('Pause', (e) => {
        setPause(e);
        Livewire.emit('setIsPlaying', false);
    })
    .listen('Remove', (e) => {
        document.getElementById("source").setAttribute('src', "");
        document.getElementById("video").load();
        Livewire.emit('setIsVideo', false)
        Livewire.emit('setIsPlaying', false);
    })
    .listen('Upload', (e) => {
        document.getElementById("source").setAttribute('src', window.location.href + "/video");
        document.getElementById("video").load();
        document.getElementById("video").currentTime = 0;
        Livewire.emit('setIsVideo', true);
    })
    .listen('Message', (e) => {
        Livewire.emit('retrieve', e.message);
    })
    .listen('Close', (e) => {
        window.location.assign(document.getElementById("close").textContent);
    });

window.Echo.private(document.getElementById("roomCode").textContent
    + "." + document.getElementById("userId").textContent)
    .listen('Set', (e) => {
        window.Echo.private(document.getElementById("roomCode").textContent
            + "." + document.getElementById("userId").textContent)
            .stopListening('Set');
        if (e.isPlaying) {
            setPlay(e);
        } else {
            setPause(e);
        }
        Livewire.emit('setIsPlaying', e.isPlaying);
    });

//Theme
function onThemeChange(colour) {
    document.getElementById("content").style.backgroundColor = colour;
    Livewire.emit('render');
}

//All Boxes
window.onclick = function (event) {
    if (event.target == document.getElementById("usersBox")) {
        document.getElementById("usersBox").style.display = "none";
    } else if (event.target == document.getElementById("blockedBox")) {
        document.getElementById("blockedBox").style.display = "none";
    } else if (event.target == document.getElementById("settingsBox")) {
        document.getElementById("settingsBox").style.display = "none";
    }
}

//Users Box
function onUsersButton() {
    document.getElementById("usersBox").style.display = "block";
}
function onSpanButton() {
    document.getElementById("usersBox").style.display = "none";
}

//Blocked Box
function onBlockedButton() {
    document.getElementById("blockedBox").style.display = "block";
}
function onSpanBlockedButton() {
    document.getElementById("blockedBox").style.display = "none";
}

//Settings Box
function onSettingsButton() {
    Livewire.emit("settingsReset");
    document.getElementById("settingsBox").style.display = "block";
}
function onSpanSettingsButton() {
    document.getElementById("settingsBox").style.display = "none";
}

//Video
function setPlay(e) {
    $start = Date.now();
    $.ajax({
        type: 'GET',
        headers: { 'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content') },
        url: window.location.href + "/time",
        success: function (data) {
            $responseTime = ((Date.now() - $start) / 1000) / 2;
            document.getElementById("video").currentTime =
                e.time + ((data - e.timeStamp) / 1000) + $responseTime;
            document.getElementById("video").play();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}
function setPause(e) {
    document.getElementById("video").currentTime = e.time;
    document.getElementById("video").pause();
}