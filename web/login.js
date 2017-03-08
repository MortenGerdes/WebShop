/**
 * Created by SofusPeter on 08-03-2017.
 */


function login() {
    var userName = document.getElementById("username").value;,
    var pass = document.getElementById("pass").value;
    var cusInfo ={"userName": userName, "passWord": pass};
    console.log(cusInfo);

    sendRequest("POST", "rest/shop/login", JSON.stringify(cusInfo),function (response) {
        if (response == "OK")
            console.log("logged in");
        if (response == "ACTIVE")
            console.log("Already logged in!");
        else{
            console.log("Fail! :(");
        }
    } )

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