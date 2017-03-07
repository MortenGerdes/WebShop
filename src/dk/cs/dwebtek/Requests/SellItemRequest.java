package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morte_000 on 05-03-2017.
 */
public class SellItemRequest implements PostRequest<Document>
{
    private Document body;

    public SellItemRequest(Document body)
    {
        this.body = body;
    }

    @Override
    public boolean hasResponse()
    {
        return true;
    }

    @Override
    public String getPath()
    {
        return "/sellItems";
    }

    @Override
    public Document parseResponse(String message)
    {
        return Utils.stringToDoc(message);
    }

    @Override
    public Document getPostBody()
    {
        return body;
    }
}
