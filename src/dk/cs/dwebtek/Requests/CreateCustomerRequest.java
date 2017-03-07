package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morten on 3/3/17.
 */
public class CreateCustomerRequest implements PostRequest<Document>
{
    private Document body;

    public CreateCustomerRequest(Document body)
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
        return "/createCustomer";
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
