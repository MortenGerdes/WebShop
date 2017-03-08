/**
 * Created by jackc_000 on 08-03-2017.
 */
/**
 * Created by jackc_000 on 08-03-2017.
 */

window.onload = function () {

    sendRequest("GET", "rest/shop/getbasket", null, function (basket) {
        var basketList = JSON.parse(basket);
        addItemsToBasket(basketList);
    });


}

function addCustomerName(customerInfo){
    var header = document.getElementById("headerID");
    header.innerHTML = customerInfo.name + " ID: " + customerInfo.id;

}

function addItemsToBasket(basketList) {

    //Remove all contents of the table body (if any exist)
    var table = document.getElementById("salesTable")
    //Loop through the items from the server
    for (var i = 0; i < basketList.length; i++) {

        var item = basketList[i];

        var newRow = document.createElement("tr");

        var shopID = document.createElement("td");
        shopID.textContent = item.shopID;
        newRow.appendChild(shopID);

        var itemID = document.createElement("td");
        itemID.textContent = item.itemID;
        newRow.appendChild(itemID);

        var price = document.createElement("td");
        price.textContent = item.saleItemPrice;
        newRow.appendChild(price);

        var amount = document.createElement("td");
        amount.textContent = item.saleAmount;
        newRow.appendChild(amount);

        table.appendChild(newRow);
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
        http.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    }
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            responseHandler(http.responseText);
        }
    };
    http.send(body);
}


