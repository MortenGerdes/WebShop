package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morten on 3/3/17.
 */
public class DeleteItemRequest implements PostRequest<Document>
{
    private Document body;

    public DeleteItemRequest(Document body)
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
        return "/deleteItem";
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
