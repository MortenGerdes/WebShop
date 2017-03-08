/**
 * Created by SofusPeter on 08-03-2017.
 */

// document.getElementById("create").addEventListener("click", function () {
//     console.log("lol");
// });

//addEventListener(create, "onClick", createCustomer() );


function createCustomer() {
    var userName = document.getElementById("username").value;
    var pass = document.getElementById("password").value;
    var rePass = document.getElementById("rePassword").value;
    var cusInfo = {"userName": userName, "passWord": pass};
    console.log(cusInfo);
    if (pass != rePass) {
        alert("Passwordet var ikke ens i de to passwordfelter!")
    }
    else {
        sendRequest("POST", "rest/shop/newCustomer", JSON.stringify(cusInfo), function (response) {
            console.log(response);
            if (response == "OK") {
                alert("Bruger oprettet!");
                window.location.replace("http://localhost:8081/login.html");
            }
            if (response == "FAILED") {
                var message = "Bruger ikke oprettet! Måske er der allerede nogen der har brugt dit brugernavn, eller serveren har travlt. Kun bogstaver fra A-Z er ikke tilladt";
                alert(message);
            }
        });
    }
}

var http;
if (!XMLHttpRequest)
    http = new ActiveXObject("Microsoft.XMLHTTP");
else
    http = new XMLHttpRequest();

function sendRequest(httpMethod, url, body, responseHandler) {
    http.open(httpMethod, url);
    if (httpMethod == "POST") {
        http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    }
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            responseHandler(http.responseText);
        }
    };
    http.send(body);
}