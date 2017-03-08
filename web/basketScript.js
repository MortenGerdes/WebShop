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
    console.debug("TEST");
    //Remove all contents of the table body (if any exist)
    var table = document.getElementById("salesTable")
    //Loop through the items from the server
    for (var i = 0; i < basketList.length; i++) {

        var item = basketList[i];

        var newRow = document.createElement("tr");

        var shopID = document.createElement("tr");
        shopID.textContent = item.shopID;

        var name = document.createElement("td");
        name.textContent = item.itemName;
        newRow.appendChild(name);

        var itemDes = document.createElement("td");
        itemDes.textContent = item.itemDescription;
        newRow.appendChild(itemDes);

        var amount = document.createElement("td");
        amount.textContent = "";


        var amountInput = document.createElement("input");
        amountInput.setAttribute("maxlength", "4");
        amountInput.setAttribute("size", "1");
        amountInput.setAttribute("id", "amountInt");
        amount.appendChild(amountInput);
        newRow.appendChild(amount);

        var price = document.createElement("td");
        price.textContent = item.itemPrice;
        newRow.appendChild(price);


        var total = document.createElement("td");
        total.textContent = item.itemPrice * parseInt(document.getElementById("amountInt"));
        newRow.appendChild(total);

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


