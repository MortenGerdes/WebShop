/**
 * Created by jackc_000 on 08-03-2017.
 */

window.onload = function () {

    sendRequest("GET", "rest/shop/customer", null, function (customer) {
        var customerInfo = JSON.parse(customer);
        addCustomerName(customerInfo);
    });

    setTimeout(initCusSales, 1000);
}

function initCusSales()
{

    sendRequest("GET", "rest/shop/customerSales", null, function (customerSales) {
        //This code is called when the server has sent its data
        var items = JSON.parse(customerSales);
        addItemsToSalesTable(items);
    });

}

function addCustomerName(customerInfo){
    var header = document.getElementById("headerID");
    header.innerHTML = customerInfo.name + " ID: " + customerInfo.id;

}

function addItemsToSalesTable(items) {
    //Remove all contents of the table body (if any exist)
    var table = document.getElementById("salesTable")
    //Loop through the items from the server
    for (var i = 0; i < items.length; i++) {

        var item = items[i];

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


