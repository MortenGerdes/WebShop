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
//abe
function logedIn() {
    var logInField = document.getElementById("logInButton");
    var logInFieldValue = logInField.innerHTML = "";
    var reDirect = document.createElement("a");
    reDirect.setAttribute("href", "http://localhost:8081/basket.html");
    var thePic = document.createElement("img");
    thePic.setAttribute("src", "https://cdn4.iconfinder.com/data/icons/greicons-2/1052/CARRITO-512.png");
    thePic.setAttribute("alt", "A picture of a basket");
    thePic.setAttribute("style", "width: 75px; height: 75px;");
    reDirect.appendChild(thePic);
    logInField.appendChild(reDirect);
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