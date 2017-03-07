package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morten on 3/3/17.
 */
public class ListCustomersRequest implements Request<Document>
{
    @Override
    public boolean hasResponse()
    {
        return true;
    }

    @Override
    public String getPath()
    {
        return "/listCustomers";
    }

    @Override
    public Document parseResponse(String message)
    {
        return Utils.stringToDoc(message);
    }
}
