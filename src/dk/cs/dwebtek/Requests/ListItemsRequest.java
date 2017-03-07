package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morte_000 on 16-02-2017.
 */
public class ListItemsRequest implements Request<Document>
{
    private String shopID;

    public ListItemsRequest(String shopID)
    {
        this.shopID = shopID;
    }

    public ListItemsRequest(int shopID)
    {
        this.shopID = "" + shopID;
    }

    @Override
    public boolean hasResponse()
    {
        return true;
    }

    @Override
    public String getPath()
    {
        return "/listItems?shopID=" + shopID;
    }

    @Override
    public Document parseResponse(String message)
    {
        return Utils.stringToDoc(message);
    }
}
