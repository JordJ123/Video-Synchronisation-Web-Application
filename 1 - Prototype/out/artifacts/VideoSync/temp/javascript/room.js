const ROOM_CODE = window.location.href.substring(
    window.location.href.lastIndexOf('/') + 1,
    window.location.href.lastIndexOf('?'));
const USERNAME = window.location.href.substring(
    window.location.href.lastIndexOf('?user=') + 6);

window.onbeforeunload = function () {
    if (sessionStorage.getItem("hasAccess") == "true") {
        deleteRoomUser();
    }
}

function postVideoFile() {
    var form = 
    var data = new FormData(form);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "http://192.168.1.85:8080/VideoSync/data/rooms/" + ROOM_CODE,
        data: data,
        // processData: false,
        dataType: "text",
        // cache: false,
        // timeout: 600000,
        success: function (data) {
            var source = document.createElement('source');
            source.setAttribute('src', data);
            var video = document.getElementById("video");
            video.appendChild(source);
        },
        error: function (errorThrown) {
            alert('error: ' + errorThrown)
        }
    });
};

function getRoomDetails() {
    var hasAccess = "false";
    if (sessionStorage.getItem("hasAccess") == "true") {
        hasAccess = "true";
    }
    document.getElementById("body").innerHTML = hasAccess;
    $.ajax({
        type: 'GET',
        url: "http://192.168.1.85:8080/VideoSync/data/rooms/" + ROOM_CODE
            + "?hasAccess=" + hasAccess,
        dataType: 'json',
        success: function (data) {
            var users = "";
            for (var key in data.users) {
                if (data.users.hasOwnProperty(key)) {
                    if (key == USERNAME) {
                        users += "<b>" + key + ", </b>";
                    } else {
                        users += key + ", ";
                    }
                }
            }
            users = users.substring(0, users.length - 6);
            document.getElementById("body").innerHTML =
                "<p>Room Code: " + data.code + "</p>"
                + "<p>Users: " + users + "</p>"
                + "<video controls>"
                    + "<source src='" + data.videoURL + "'/>"
                + "</video>";
            if (data.users[USERNAME] == true) {
                document.getElementById("body").innerHTML +=
                    "<form method='POST' encType='multipart/form-data'>"
                        + "<input type='file' name='file'/><br/>"
                        + "<input type='button' value='Upload' " +
                                "onclick='return postVideoFile()'/>"
                    + "</form>";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 403) {
                window.location.assign(
                    "http://192.168.1.85:8080/VideoSync/data/rooms/")
            } else {
                alert("Error: " + errorThrown);
            }
        }
    })
}

function deleteRoomUser() {
    $.ajax({
        type: 'DELETE',
        url: window.location.href,
        success: function () {
            document.getElementById("roomCode").innerHTML = "works";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('error: ' + errorThrown)
        }
    })
}