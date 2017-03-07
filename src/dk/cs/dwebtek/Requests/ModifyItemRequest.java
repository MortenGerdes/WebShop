package dk.cs.dwebtek.Requests;

import org.jdom2.Document;

/**
 * Created by morten on 2/17/17.
 */
public class ModifyItemRequest implements PostRequest<Document>
{

   private Document body;
    public ModifyItemRequest(Document body)
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
        return "/modifyItem";
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
