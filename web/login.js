/**
 * Created by SofusPeter on 08-03-2017.
 */


function login() {
    var userName = document.getElementById("username").value;
    var pass = document.getElementById("pass").value;
    console.log("Username: " + userName + " Password: " + pass);
    var cusInfo = {"userName": userName, "passWord": pass};
    console.log(cusInfo);

    sendRequest("POST", "rest/shop/login", JSON.stringify(cusInfo), function (response) {
        if (response == "OK") {
            alert("logged in");
            logedIn();
        }
        else if (response == "ACTIVE") {
            alert("Already logged in!");
            logedIn();
        }
        else {
            alert("Wrong username or password (Or server is busy)");
        }
    })

}

function logedIn() {
    var logInField = document.getElementById("logInButton").innerHTML = "";
    var thePic = document.createElement("img");
    thePic.setAttribute("src", "https://media-cdn.tripadvisor.com/media/photo-s/01/90/78/c5/sunset-over-dinner-nice.jpg");
    thePic.setAttribute("alt", "A picture of the product");
    logInField.appendChild(thePic);
}

function toCreateUser() {
    window.location.replace("http://localhost:8081/createAccount.html");

}

////

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