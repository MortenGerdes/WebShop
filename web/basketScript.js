/**
 * Created by jackc_000 on 08-03-2017.
 */
/**
 * Created by jackc_000 on 08-03-2017.
 */
//global variable used for something
var totalItems = 0;
var allPrices;

window.onload = function () {

    sendRequest("GET", "rest/shop/getbasket", null, function (basket) {
        var basketList = JSON.parse(basket);

        if (basketList.length == 0) {
            var noItemsMessage = document.getElementById("headerID");
            noItemsMessage.textContent = "You have no items in your basket";
        }
        else {
            addItemsToBasket(basketList);
            storeItemAmount(basketList);
            storeAllPrices(basketList);
            setTotalTotal();
        }
    });

}

function addCustomerName(customerInfo) {
    var header = document.getElementById("headerID");
    header.innerHTML = customerInfo.name + " ID: " + customerInfo.id;

}

function storeItemAmount(basketList) {
    totalItems = basketList.length;
}


function addItemsToBasket(basketList) {
    //Remove all contents of the table body (if any exist)
    var table = document.getElementById("salesTable");

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
        itemDes.innerHTML = item.itemDescription;
        newRow.appendChild(itemDes);

        var maxAmount = document.createElement("td");
        maxAmount.setAttribute("id", "maxAmount" + i);
        maxAmount.innerHTML = item.itemStock;
        newRow.appendChild(maxAmount);

        var amount = document.createElement("td");
        amount.setAttribute("class", "amountRow");
        amount.textContent = "";


        var decrementBut = document.createElement("input");
        decrementBut.setAttribute("type", "button");
        decrementBut.setAttribute("value", "<-");
        decrementBut.setAttribute("id", "button" + "-" + i);
        decrementBut.setAttribute("onclick", "decrement(" + "\'" + "button" + "-" + i + "\'" + ")");
        amount.appendChild(decrementBut);

        var amountInput = document.createElement("input");
        amountInput.setAttribute("value", "1");
        amountInput.setAttribute("maxlength", "4");
        amountInput.setAttribute("size", "1");
        amountInput.setAttribute("id", "amountInt" + "" + i);
        amount.appendChild(amountInput);

        var incrementBut = document.createElement("input");
        incrementBut.setAttribute("type", "button");
        incrementBut.setAttribute("value", "->");
        incrementBut.setAttribute("id", "button" + "" + i);
        incrementBut.setAttribute("onclick", "increment(" + "\'" + "button" + i + "\'" + ")");
        amount.appendChild(incrementBut);

        newRow.appendChild(amount);

        var price = document.createElement("td");
        price.setAttribute("class", "priceRow");
        price.textContent = item.itemPrice;
        newRow.appendChild(price);


        var total = document.createElement("td");
        total.setAttribute("class", "totalRow");
        total.setAttribute("id", "total" + i);
        total.textContent = item.itemPrice;
        newRow.appendChild(total);

        table.appendChild(newRow);
    }

    var buyNowRow = document.getElementById("buyNowRow");
    var buyNowBut = document.createElement("input");
    buyNowBut.setAttribute("type", "button");
    buyNowBut.setAttribute("value", "Buy Now");
    buyNowBut.setAttribute("onclick", "buyConfirmed()");
    buyNowRow.appendChild(buyNowBut);
}

function buyConfirmed() {

    var delay = 300;
    sendRequest("GET", "rest/shop/getbasket", null, function (basket) {
        var basketList = JSON.parse(basket);
        for (var i = 0; i < basketList.length; i++) {
            var item = basketList[i];
            var textField = document.getElementById("amountInt" + i);
            var number = parseInt(textField.value);
            item["wantedToSell"] = number;

            console.log("Timeout: " + delay);
            console.log(item);
            buyItem(item);
        }
    });

    alert("Your purchase has been confirmed");

    setTimeout(function () {
        sendRequest("GET", "rest/shop/clearBasket", null, function () {
        });
    }, delay);

    setTimeout(function () {
        window.location.replace("/index.html");
    }, delay+100)

}

function clearBasket() {
    sendRequest("GET", "rest/shop/clearBasket", null, function (){
    });
    alert("Basket Cleared!");
    window.location.replace("/index.html")
}

function buyItem(item) {
    sendRequest("POST", "rest/shop/sellItem", JSON.stringify(item), function (rawr) {
        console.log("Bought shet");
    });
}

function setTotalTotal() {
    var totalPrice = 0;
    for (var i = 0; i < allPrices.length; i++) {

        var total = document.getElementById("total" + i);


        if (total.innerHTML == null) {
            totalPrice += 0;
        }
        else {
            totalPrice += parseInt(total.innerHTML);
        }
    }

    var totalTotal = document.getElementById("totalTotal");
    totalTotal.innerHTML = totalPrice;
}

function storeAllPrices(basketList) {
    var temp = [];

    for (var i = 0; i < basketList.length; i++) {
        temp[i] = (basketList[i].itemPrice);
    }
    allPrices = temp;
}

function totalMult(index, amount) {

    var indexInt = parseInt(index);
    var total = document.getElementById("total" + indexInt);

    var newTotal = allPrices[index] * amount;

    total.textContent = "" + newTotal;

}


function increment(id) {


    if (totalItems < 10) {
        var textField = document.getElementById("amountInt" + id.toString().substr(6, 7));
        var number = parseInt(textField.value);
        if (document.getElementById("maxAmount" + id.toString().substr(6, 7)).innerHTML <= number) {
            return;
        }
        var numberInt = parseInt(number) + 1;

        textField.value = "" + numberInt;

        //calls total multiplier function
        totalMult(id.toString().substr(6, 7), numberInt);
        setTotalTotal();

    }
    else if (totalItems >= 10 && totalItems < 100) {
        var textField = document.getElementById("amountInt" + id.substr(6, 8));
        var number = parseInt(textField.value);
        if (document.getElementById("maxAmount" + id.substr(6, 8)).innerHTML <= number) {
            return;
        }
        var numberInt = parseInt(number) + 1;

        textField.value = "" + numberInt;
        totalMult(id.toString().substr(6, 8), numberInt);
        setTotalTotal();
    }
    else {
        console.debug("LOL1");
    }


}

function decrement(id) {


    if (totalItems < 10) {


        var textField = document.getElementById("amountInt" + id.substr(7, 8));
        if (textField.value > 0) {
            var number = textField.value;
            var numberInt = parseInt(number) - 1;

            textField.value = "" + numberInt;

            totalMult(id.toString().substr(7, 8), numberInt);
            setTotalTotal();
        }
    }
    else if (totalItems >= 10 && totalItems < 100) {

        var textField = document.getElementById("amountInt" + id.substr(7, 9));
        if (textField.value > 0) {
            var number = textField.value;
            var numberInt = parseInt(number) - 1;

            textField.value = "" + numberInt;

            totalMult(id.toString().substr(7, 9), numberInt);
            setTotalTotal();
        }
    }
    else {
        console.debug("LOL2")
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


