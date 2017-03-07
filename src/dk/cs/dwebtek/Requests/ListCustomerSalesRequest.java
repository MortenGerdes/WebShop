package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by jackc_000 on 05-03-2017.
 */
public class ListCustomerSalesRequest implements Request<Document>{

    private String customerID;

    public ListCustomerSalesRequest(String customerID) {
        this.customerID = customerID;
    }

    public ListCustomerSalesRequest(int customerID) {
        this.customerID = "" + customerID;
    }
        @Override
    public boolean hasResponse() {
        return true;
    }

    @Override
    public String getPath() {
        return "/listCustomerSales?customerID=" + customerID;
    }

    @Override
    public Document parseResponse(String message) {
        return Utils.stringToDoc(message);
    }
}
