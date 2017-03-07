package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by mortenkrogh-jespersen on 07/02/2017.
 */
public class CreateItemPostRequest implements PostRequest<Document>
{

    private String name;
    private Document body;

    public CreateItemPostRequest(String name, Document body)
    {
        this.name = name;
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
        return "/createItem";
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
