const RESPONSE_STATUS_FORBIDDEN = 403;
const EMPTY_FIELD_ERROR = "Please enter a room code";

window.onload = function () {
    if (sessionStorage.getItem("kicked") == "true") {
        sessionStorage.setItem("kicked", null)
        alert("You have been kicked")
    }
    if (sessionStorage.getItem("user") == null ||
        sessionStorage.getItem("user") == "") {
        window.location.assign(
            "http://192.168.1.249:8080/VideoSync/server/login")
    }
}

function postRoomUser() {
    if ($('#roomCode').val() != "") {
        $.ajax({
            type: 'POST',
            url: window.location.href + "?roomCode=" + $('#roomCode').val(),
            contentType: "application/json",
            data: sessionStorage.getItem("user"),
            success: function () {
                window.location.assign("http://192.168.1.249:8080/VideoSync/"
                +   "server/room/" + $('#roomCode').val())
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status == RESPONSE_STATUS_FORBIDDEN) {
                    alert(jqXHR.responseText);
                } else {
                    alert('Error: ' + jqXHR.responseText);
                }
            }
        })
    } else {
        alert(EMPTY_FIELD_ERROR);
    }
}