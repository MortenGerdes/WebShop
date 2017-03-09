package dk.cs.dwebtek.Requests;

/**
 * Created by jackc on 09/03/2017.
 */
public class ListShopsRequest implements Request {

    private String shopID;
    private String shopName;
    private String shopURL;


    @Override
    public boolean hasResponse() {
        return true;

    }

    @Override
    public String getPath() {
        return "/listShops";
    }

    @Override
    public Object parseResponse(String message) {
        return Utils.stringToDoc(message);
    }
}
