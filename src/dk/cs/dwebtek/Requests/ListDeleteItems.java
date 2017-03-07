package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morten on 3/3/17.
 */
public class ListDeleteItems implements Request<Document>
{
    private String shopID;

    public ListDeleteItems(String shopID)
    {
        this.shopID = shopID;
    }

    @Override
    public boolean hasResponse()
    {
        return true;
    }

    @Override
    public String getPath()
    {
        return "/listDeletedItemIDs?shopID=" + shopID;
    }

    @Override
    public Document parseResponse(String message)
    {
        return Utils.stringToDoc(message);
    }
}
