/**
 * Created by jackc_000 on 08-03-2017.
 */

window.onload = function () {
    sendRequest("GET", "rest/shop/items", null, function (itemsText) {
        //This code is called when the server has sent its data
        var items = JSON.parse(itemsText);
        addItemsToTable(items);
    });
}
