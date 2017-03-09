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
            loggedIn();
        }
        else if (response == "ACTIVE") {
            alert("Already logged in!");
            loggedIn();
        }
        else {
            alert("Wrong username or password (Or server is busy)");
        }
    })
}
//abe
function loggedIn() {
    var logInField = document.getElementById("logInButton");
    var logInFieldValue = logInField.innerHTML = "";
    var reDirect = document.createElement("a");
    reDirect.setAttribute("href", "http://localhost:8081/basket.html");
    var thePic = document.createElement("img");
    thePic.setAttribute("src", "https://cdn4.iconfinder.com/data/icons/greicons-2/1052/CARRITO-512.png");
    thePic.setAttribute("alt", "A picture of a basket");
    thePic.setAttribute("style", "width: 75px; height: 75px;");
    reDirect.appendChild(thePic);
    var reDirect2 = document.createElement("a");
    reDirect2.setAttribute("href", "http://localhost:8081/profile.html");
    var thePic2 = document.createElement("img");
    thePic2.setAttribute("src", "http://www.freeiconspng.com/uploads/profile-icon-9.png");
    thePic2.setAttribute("alt", "A picture of a person");
    thePic2.setAttribute("style", "width: 75px; height: 75px;");
    reDirect2.appendChild(thePic2);
    logInField.appendChild(reDirect);
    logInField.appendChild(reDirect2);

    var logOut = document.createElement("input");
    logOut.setAttribute("type", "submit");
    logOut.setAttribute("value", "Log Ud");
    logOut.setAttribute("onclick", "logOut()");
    logOut.setAttribute("style", "display:block; width:150px;");
    console.log(logOut);
    logInField.appendChild(logOut);
}

function logOut() {
    sendRequest("GET", "rest/shop/logOut", null, function (response) {
        alert("logged out!");
        window.location.replace("http://localhost:8081/index.html");
    })

    logInField.appendChild(reDirect2);
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