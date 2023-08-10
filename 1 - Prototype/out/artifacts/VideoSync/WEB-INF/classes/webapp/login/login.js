const CREATE_USER_URL = "http://192.168.1.249:8080/VideoSync/server/createUser";
const EMPTY_FIELD_ERROR = "Please enter a username and password";

window.onload = function () {
    sessionStorage.setItem("user", "");
}

function getCreateUser() {
    window.location.assign(CREATE_USER_URL);
}

function postActiveUser() {
    if ($('#username').val() != "" && $('#password').val() != "") {
        $.ajax({
            type: 'POST',
            url: "http://192.168.1.249:8080/VideoSync/server/login",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            data: $("#userForm").serializeArray(),
            success: async function (data) {
                sessionStorage.setItem("user", JSON.stringify(data));
                window.location.assign(
                    "http://192.168.1.249:8080/VideoSync/server/home");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Error: ' + jqXHR.responseText);
            }
        })
    } else {
        alert(EMPTY_FIELD_ERROR);
    }
}