const RESPONSE_STATUS_CONFLICT = 409;

function loadRoom() {
    $.ajax({
        type: 'POST',
        url: "http://192.168.1.85:8080/VideoSync/data/rooms/"
            + document.getElementById('roomCode').value,
        contentType: 'text/plain',
        data: document.getElementById('username').value,
        success: function () {
            sessionStorage.setItem("hasAccess", "true");
            window.location.assign(
                "http://192.168.1.85:8080/VideoSync/data/rooms/"
                + $('#roomCode').val()
                + "?user=" + $('#username').val())
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == RESPONSE_STATUS_CONFLICT) {
                alert("Username is already in use for this room")
            } else {
                alert('error: ' + errorThrown)
            }
        }
    })
}