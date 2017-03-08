/**
 * Created by morten on 3/7/17.
 */
//Run this function when we have loaded the HTML document
window.onload = function () {
    var message = document.getElementById("welcomeMessage");
    //This code is called when the body element has been loaded and the application starts

    //Request items from the server. The server expects no request body, so we set it to null
    sendRequest("GET", "rest/shop/items", null, function (itemsText) {
        //This code is called when the server has sent its data
        var items = JSON.parse(itemsText);
        addItemsToTable(items);
    });

    //Register an event listener for button clicks
    var updateButton = document.getElementById("update");
    addEventListener(updateButton, "click", function () {
        //Same as above, get the items from the server
        sendRequest("GET", "rest/shop/items", null, function (itemsText) {
            //This code is called when the server has sent its data
            var items = JSON.parse(itemsText);
            addItemsToTable(items);
        });
    });
};

function addItemsToTable(items) {
    //Get the table body we we can add items to it
    var productDiv = document.getElementById("area");
    //Remove all contents of the table body (if any exist)

    //Loop through the items from the server
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        //Create a new line for this item
        var special = document.createElement("div");
        special.setAttribute("class", "specificproductouter");

        var itemPicDiv = document.createElement("div");
        itemPicDiv.setAttribute("class", "productpic");
        special.appendChild(itemPicDiv);

        var thePic = document.createElement("img");
        thePic.setAttribute("src", item.itemURL);
        thePic.setAttribute("alt", "A picture of the product");
        itemPicDiv.appendChild(thePic);
        var itemTitle = document.createElement("div");
        itemTitle.setAttribute("class", "producttitle");
        itemTitle.textContent = item.itemName;
        special.appendChild(itemTitle);

        var itemDes = document.createElement("div");
        itemDes.setAttribute("class", "productinfo");
        itemDes.textContent = item.itemDescription;
        special.appendChild(itemDes);

        var itemPur = document.createElement("div");
        itemPur.setAttribute("class", "productpurchase");
        var itemPricing = document.createElement("div");
        itemPricing.setAttribute("class", "pricetext");
        itemPricing.textContent = item.itemPrice + "$";
        itemPur.appendChild(itemPricing);
        var theForm = document.createElement("form");
        theForm.setAttribute("action", "rest/shop/addbasket?itemID=" + item.itemID);
        theForm.setAttribute("method", "post");
        itemPur.appendChild(theForm);
        var addToCart = document.createElement("input");
        addToCart.setAttribute("name", "addtocart");
        addToCart.setAttribute("type", "submit");
        addToCart.setAttribute("value", "Add to cart");
        addToCart.setAttribute("onclick", "whenAddedToBasket()")
        theForm.appendChild(addToCart);

        special.appendChild(itemPur);
        productDiv.appendChild(special);
    }
}

function addItemToSalesTable(items){

    var productDiv = document.getElementById("area");

}

function whenAddedToBasket() {
    alert("Item added to basket");

}


/////////////////////////////////////////////////////
// Code from slides
/////////////////////////////////////////////////////

/**
 * A function that can add event listeners in any browser
 */
function addEventListener(myNode, eventType, myHandlerFunc) {
    if (myNode.addEventListener)
        myNode.addEventListener(eventType, myHandlerFunc, false);
    else
        myNode.attachEvent("on" + eventType,
            function (event) {
                myHandlerFunc.call(myNode, event);
            });
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

