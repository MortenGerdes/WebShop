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
    header.innerHTML = "Customer Name: " + customerInfo.name + " <br>ID: " + customerInfo.id;
}

function addItemsToSalesTable(items) {

    //Remove all contents of the table body (if any exist)
    var table = document.getElementById("salesTable")
    //Loop through the items from the server
    for (var i = 0; i < items.length; i++) {

        var item = items[i];

        var newRow = document.createElement("tr");

        var itemURL = document.createElement("td");
        itemURL.textContent = item.itemURL;
        newRow.appendChild(itemURL);

        var itemName = document.createElement("td");
        itemName.textContent = item.itemName;
        newRow.appendChild(itemName);

        var itemID = document.createElement("td");
        itemID.textContent = item.itemID;
        newRow.appendChild(itemID);

        var price = document.createElement("td");
        price.textContent = item.saleItemPrice;
        newRow.appendChild(price);

        var amount = document.createElement("td");
        amount.textContent = item.saleAmount;
        newRow.appendChild(amount);

        var shopID = document.createElement("td");
        shopID.textContent = item.shopID;
        newRow.appendChild(shopID);

        var date = document.createElement("td");
        var theDate = new Date(item.saleTime)
        date.textContent = theDate.customFormat("#YYYY#/#MM#/#DD# #hh#:#mm#:#ss#");
        newRow.appendChild(date);

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

Date.prototype.customFormat = function(formatString){
    var YYYY,YY,MMMM,MMM,MM,M,DDDD,DDD,DD,D,hhhh,hhh,hh,h,mm,m,ss,s,ampm,AMPM,dMod,th;
    var dateObject = this;
    YY = ((YYYY=dateObject.getFullYear())+"").slice(-2);
    MM = (M=dateObject.getMonth()+1)<10?('0'+M):M;
    MMM = (MMMM=["January","February","March","April","May","June","July","August","September","October","November","December"][M-1]).substring(0,3);
    DD = (D=dateObject.getDate())<10?('0'+D):D;
    DDD = (DDDD=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][dateObject.getDay()]).substring(0,3);
    th=(D>=10&&D<=20)?'th':((dMod=D%10)==1)?'st':(dMod==2)?'nd':(dMod==3)?'rd':'th';
    formatString = formatString.replace("#YYYY#",YYYY).replace("#YY#",YY).replace("#MMMM#",MMMM).replace("#MMM#",MMM).replace("#MM#",MM).replace("#M#",M).replace("#DDDD#",DDDD).replace("#DDD#",DDD).replace("#DD#",DD).replace("#D#",D).replace("#th#",th);

    h=(hhh=dateObject.getHours());
    if (h==0) h=24;
    if (h>12) h-=12;
    hh = h<10?('0'+h):h;
    hhhh = hhh<10?('0'+hhh):hhh;
    AMPM=(ampm=hhh<12?'am':'pm').toUpperCase();
    mm=(m=dateObject.getMinutes())<10?('0'+m):m;
    ss=(s=dateObject.getSeconds())<10?('0'+s):s;
    return formatString.replace("#hhhh#",hhhh).replace("#hhh#",hhh).replace("#hh#",hh).replace("#h#",h).replace("#mm#",mm).replace("#m#",m).replace("#ss#",ss).replace("#s#",s).replace("#ampm#",ampm).replace("#AMPM#",AMPM);
}


