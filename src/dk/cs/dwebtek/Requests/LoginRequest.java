package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morten on 3/4/17.
 */
public class LoginRequest implements PostRequest<Document>
{
    private Document body;
    public LoginRequest(Document body)
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
        return "/login";
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
