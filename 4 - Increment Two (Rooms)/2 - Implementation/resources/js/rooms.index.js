window.Echo.private("index." + document.getElementById("userId").textContent)
    .listen('Kick', (e) => {
        Livewire.emit("sideBarRender");
        window.alert("You have been kicked from a room (Room: " + e.roomName + ")");
    })
    .listen('Close', (e) => {
        Livewire.emit("sideBarRender");
        window.alert("An administrator has deleted their room (Room: " + e.roomName + ")");
    });