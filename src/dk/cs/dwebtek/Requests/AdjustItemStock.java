package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by Sofus on 23-02-2017.
 */
public class AdjustItemStock implements PostRequest<Document>
{
    private Document body;

    public AdjustItemStock(Document body)
    {
        this.body = body;
    }

    @Override
    public boolean hasResponse()
    {
        return false;
    }

    @Override
    public String getPath()
    {
        return "/AdjustItemStock";
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
