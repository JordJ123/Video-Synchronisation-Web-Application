const EMPTY_ERROR = "Please enter a value for each box"
const PASSWORD_ERROR = "Passwords do not match";

function getLoginPage() {
    window.location.assign("http://192.168.1.249:8080/VideoSync/server/login");
}

function postNewUser() {
    if ($("#username").val() != "" && $("#password").val() != "" &&
        $("#repeatPassword").val() != "") {
        if ($("#password").val() == $("#repeatPassword").val()) {
            $.ajax({
                type: "POST",
                url: window.location.href,
                contentType: "application/x-www-form-urlencoded",
                data: $("#userForm").serializeArray(),
                success: function() {
                    getLoginPage();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('Error: ' + jqXHR.responseText)
                }
            });
        } else {
            alert(PASSWORD_ERROR);
        }
    } else {
        alert(EMPTY_ERROR);
    }
}
