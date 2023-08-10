const RESPONSE_STATUS_NOT_FOUND = 404;
const NO_FILE_ERROR = 
const EMPTY_MESSAGE_ERROR = "Please enter a message to send"

const ADD_USER = "addUser"
const DELETE_USER = "deleteUser"
const KICK_USER = "kickUser"
const UPLOAD = "upload"
const PLAY = "play";
const PAUSE = "pause";
const REMOVE = "remove";
const MESSAGE = "message";
const CLOSE = "close";

const ROOM_CODE = window.location.href.substring(
    window.location.href.lastIndexOf('/') + 1);

let webSocket;
let users;
let admin;
let videoPlaying = "false";

window.onload = function() {
    if (sessionStorage.getItem("user") == null) {
        window.location.assign(
            "http://192.168.1.249:8080/VideoSync/server/login")
    } else {
        getRoomDetails();
    }
}
window.onbeforeunload = function () {
    deleteRoomUser();
}

function getRoomDetails() {
    $.ajax({
        type: 'GET',
        url: window.location.href + "?user=" + sessionStorage.getItem("user"),
        dataType: 'json',
        success: async function (data) {
            document.getElementById('video').load();
            users = data.users;
            document.getElementById('roomCode').innerHTML += data.code;
            document.getElementById('source').src = data.videoURL;
            admin = users[JSON.parse(sessionStorage.getItem("user")).username]
            if (admin != true) {
                document.getElementById('playButton').remove();
                document.getElementById('pauseButton').remove();
                document.getElementById('removeButton').remove();
                document.getElementById('slider').remove();
                document.getElementById('fileForm').remove();
            }
            setWebSocket();
            if (admin) {
                setSliderValue();
            }
            await new Promise(r => setTimeout(r, 100));
            if (document.getElementById('video').readyState != 4) {
                document.getElementById('videoButtons')
                    .setAttribute('hidden', "");
            }
            document.getElementById('video').volume = 0;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Error: " + jqXHR.responseText);
            if (jqXHR.status == RESPONSE_STATUS_NOT_FOUND) {
                window.location.assign(
                    "http://192.168.1.249:8080/VideoSync/server/login")
            }
        }
    });
}

function postVideoFile() {
    if (document.getElementById('file').value != "") {
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: window.location.href,
            processData: false,
            contentType: false,
            cache: false,
            data: new FormData(jQuery('#fileForm')[0]),
            success: function (data) {
                alert("Upload Successful")
                document.getElementById("source").setAttribute('src', data);
                sendRoomUpdate(UPLOAD, data);
                document.getElementById("video").load();
                document.getElementById("video").currentTime = 0;
                document.getElementById('videoButtons').removeAttribute('hidden');
                document.getElementById('pauseButton').setAttribute('hidden', "");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Error: ' + jqXHR.responseText)
            }
        });
    } else {
        alert(NO_FILE_ERROR);
    }
    document.getElementById("source").removeAttribute('src');
    sendRoomUpdate(REMOVE, "")
}

function postVideoMessage() {
    let message = document.getElementById('message').value;
    if (message != "") {
        message = JSON.parse(sessionStorage.getItem("user")).username
            + ": " + document.getElementById('message').value;
        sendRoomUpdate(MESSAGE, message);
        document.getElementById('message').value = "";
        document.getElementById('output').innerHTML += message + "<br>";
    } else {
        alert(EMPTY_MESSAGE_ERROR)
    }
}

function postKickedUser(username) {
    $.ajax({
        type: 'POST',
        url: window.location.href,
        contentType: "text/plain",
        data: username,
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
    sendRoomUpdate(KICK_USER, username)
}

function putVideoStatus(status, timestamp) {
    var date = new Date();
    sendRoomUpdate(status, date.getHours() + ":" + date.getMinutes()
        + ":" + date.getSeconds() + "/" + timestamp)
    if (status == PLAY) {
        videoPlaying = "true";
        document.getElementById('video').play();
        if (admin) {
            document.getElementById('playButton').setAttribute('hidden', "");
            document.getElementById('pauseButton').removeAttribute('hidden');
        }
    } else {
        videoPlaying = "false";
        document.getElementById('video').pause();
        if (admin) {
            document.getElementById('playButton').removeAttribute('hidden');
            document.getElementById('pauseButton').setAttribute('hidden', "");
        }
    }
}

function deleteRoomUser() {
    $.ajax({
        type: 'DELETE',
        url: window.location.href + "/"
            + sessionStorage.getItem("user"),
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

function deleteRoomVideo() {
    document.getElementById("source").setAttribute('src', "");
    document.getElementById("video").load();
    document.getElementById('videoButtons').setAttribute('hidden', "")
    sendRoomUpdate(REMOVE, "")
    $.ajax({
        type: 'DELETE',
        url: window.location.href,
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

function setWebSocket() {
    webSocket = new WebSocket("ws://192.168.1.249:8080/VideoSync/webSocket/" +
        "room/" + ROOM_CODE + "/"
        + JSON.parse(sessionStorage.getItem("user")).username + "/" + admin);
    webSocket.onmessage = function (event) {
        let roomUpdate = JSON.parse(event.data);
        if (roomUpdate.updateType == ADD_USER) {
            if (admin) {
                if (videoPlaying == "true") {
                    putVideoStatus("play",
                        document.getElementById("video").currentTime);
                } else {
                    putVideoStatus("pause",
                        document.getElementById("video").currentTime);
                }
                document.getElementById("usersList").innerHTML +=
                    "<div class='user' id='user" + roomUpdate.information + "'>"
                        + roomUpdate.information +
                        " <button onclick='postKickedUser(" + '"' +
                            roomUpdate.information + '"' + ")'>Kick</button>" +
                    "</div>"
            }
        } else if (roomUpdate.updateType == DELETE_USER) {
            delete users[roomUpdate.information];
            document.getElementById('user' + roomUpdate.information).remove();
        } else if (roomUpdate.updateType == KICK_USER) {
            if (JSON.parse(sessionStorage.getItem("user")).username == roomUpdate.information) {
                sessionStorage.setItem("kicked", "true");
                window.location.assign(
                    "http://192.168.1.249:8080/VideoSync/server/login/")
            }
        } else if (roomUpdate.updateType == UPLOAD) {
            let source = document.getElementById("source");
            source.setAttribute('src', roomUpdate.information);
            document.getElementById('video').load();
            document.getElementById('videoButtons').removeAttribute('hidden');
        } else if (roomUpdate.updateType == PLAY) {
            setCurrentTime(roomUpdate.information);
            document.getElementById("video").play();
        } else if (roomUpdate.updateType == PAUSE) {
            setCurrentTime(roomUpdate.information);
            document.getElementById("video").pause();
        } else if (roomUpdate.updateType == REMOVE) {
            document.getElementById("source").setAttribute('src', "");
            document.getElementById("video").load();
            document.getElementById('videoButtons').setAttribute('hidden', "")
        } else if (roomUpdate.updateType == CLOSE) {
            window.location.assign(
                "http://192.168.1.249:8080/VideoSync/server/login/")
        } else if (roomUpdate.updateType == MESSAGE) {
            document.getElementById('output').innerHTML
                += roomUpdate.information + "<br>"
        } else {
            alert(event.data);
        }
    }
}

function setCurrentTime(currentTime) {
    var base = currentTime.substring(currentTime.indexOf("/") + 1);
    var hours = currentTime.substring(0, currentTime.indexOf(":"));
    var minutes = currentTime.substring(currentTime.indexOf(":") + 1,
        currentTime.indexOf(":", currentTime.indexOf(":") + 1));
    var seconds = currentTime.substring(
        currentTime.indexOf(":", currentTime.indexOf(":") + 1) + 1,
        currentTime.indexOf("/"));
    var earlierTime = (hours * 3600) + (minutes * 60) + seconds;
    var date = new Date();
    var laterTime = (date.getHours() * 3600) + (date.getMinutes() * 60)
        + seconds;
    if (earlierTime > laterTime) {
        laterTime = laterTime + 86400
    }
    let time = base + (laterTime - earlierTime)
    document.getElementById("video").currentTime = time.valueOf();
}

function setVideoVolume(volumeChange) {
    var currentVolume = document.getElementById('video').volume;
    if (document.getElementById('video').muted == true) {
        document.getElementById('video').muted
            = !document.getElementById('video').muted;
    }
    currentVolume += volumeChange;
    if (currentVolume >= 0 && currentVolume <= 1) {
        document.getElementById("video").volume = currentVolume;
    } else {
        document.getElementById("video").volume = (currentVolume < 0) ? 0 : 1;
    }
}

function setSliderInput() {
    let video = document.getElementById('video');
    let percentage = document.getElementById('slider').value / 100;
    video.currentTime = video.duration * percentage;
    if (videoPlaying == "true") {
        putVideoStatus("play", document.getElementById("video").currentTime);
    } else {
        putVideoStatus("pause", document.getElementById("video").currentTime);
    }
}

async function setSliderValue() {
    while (true) {
        await new Promise(r => setTimeout(r, 100));
        if (videoPlaying == "true") {
            let slider = document.getElementById("slider");
            let video = document.getElementById("video");
            slider.value = Math.ceil((video.currentTime / video.duration) * 100)
        }
    }
}

function sendRoomUpdate(updateType, information) {
    let object = {updateType:updateType, information:information}
    webSocket.send(JSON.stringify(object))
}

function onUsersButton() {
    document.getElementById("usersBox").style.display = "block";
}
function onSpanButton() {
    document.getElementById("usersBox").style.display = "none";
}
window.onclick = function(event) {
    if (event.target == document.getElementById("usersBox")) {
        document.getElementById("usersBox").style.display = "none";
    }
}