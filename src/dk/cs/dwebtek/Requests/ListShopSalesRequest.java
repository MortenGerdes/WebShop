package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by SofusPeter on 05-03-2017.
 */

public class ListShopSalesRequest implements Request<Document> {

    private String shopKey;

    public ListShopSalesRequest(String shopKey) {

        this.shopKey = shopKey;
    }

    @Override
    public boolean hasResponse() {
        return true;
    }

    @Override
    public String getPath() {
        return "/listShopSales?shopKey=" + shopKey;
    }

    @Override
    public Document parseResponse(String message) {
            return Utils.stringToDoc(message);
    }
}
