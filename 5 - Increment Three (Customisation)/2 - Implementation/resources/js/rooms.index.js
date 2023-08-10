window.Echo.private("index." + document.getElementById("userId").textContent)
    .listen('RoomAvatar', (e) => {
        Livewire.emit("highlightRoom", e.roomCode);
        document.getElementById("image" + e.roomCode).src = e.imageUrl;
    })
    .listen('NameChange', (e) => {
        Livewire.emit("highlightRoom", e.roomCode);
        alert("An adminstrator or a moderator has changed a room name (" + e.oldName + " to "
            + e.name + ")");
    })
    .listen('Kick', (e) => {
        Livewire.emit("sideBarRender");
        window.alert("You have been kicked from a room (Room: " + e.roomName + ")");
    })
    .listen('Close', (e) => {
        Livewire.emit("sideBarRender");
        window.alert("An administrator has deleted their room (Room: " + e.roomName + ")");
    });