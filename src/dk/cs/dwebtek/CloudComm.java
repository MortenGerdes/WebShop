package dk.cs.dwebtek;

import dk.cs.dwebtek.Requests.PostRequest;
import dk.cs.dwebtek.Requests.Request;
import dk.cs.dwebtek.Requests.Utils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mortenkrogh-jespersen on 07/02/2017.
 */
public class CloudComm
{

    public static <E> OperationResult<E> performRequest(Request req)
    {
        try
        {
            boolean isPost = req instanceof PostRequest;

            String inputLine = null;
            Object response = null;
            Document doc = null;
            URL url = new URL("http://webtek.cs.au.dk/cloud" + req.getPath());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (isPost)
            {
                PostRequest pr = (PostRequest) req;
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                if (pr.getPostBody() instanceof Document)
                {
                    doc = (Document) pr.getPostBody();
                    if (validate(doc).isSuccess())
                    {
                        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat().setIndent("    "));
                        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream()))
                        {
                            wr.writeBytes(out.outputString(doc));
                        }
                    } else
                    {
                        return OperationResult.Fail(validate((Document) pr.getPostBody()).getMessage());
                    }
                }
            } else
            {
                conn.setRequestMethod("GET");
            }
            if (conn.getResponseCode() == 200)
            {
                if (req.hasResponse())
                {
                    inputLine = Utils.streamToString(conn.getInputStream());
                    response = req.parseResponse(inputLine);
                    if (response instanceof Document) // Since we want to validate documents
                    {
                        doc = (Document) response;
                        if (validate(doc).isSuccess())
                        {
                            return OperationResult.Success((E) doc);
                        } else
                        {
                            return OperationResult.Fail("Failed validation");
                        }
                    }
                }

            } else
            {
                return OperationResult.Fail("Not correct response: " + conn.getResponseCode());
            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return OperationResult.Success();
    }


    private static OperationResult<Object> validate(Document doc)
    {
        CloudComm cm = new CloudComm();
        URL url = cm.getClass().getClassLoader().getResource("cloud.xsd");
        XMLReaderJDOMFactory factory;

        try
        {
            factory = new XMLReaderXSDFactory(url);
        } catch (JDOMException e)
        {
            return OperationResult.Fail("Could not find schema");
        }

        String xml = new XMLOutputter().outputString(doc);
        SAXBuilder builder = new SAXBuilder(factory);
        try
        {
            builder.build(new StringReader(xml));
        } catch (JDOMException e)
        {
            return OperationResult.Fail("Xml is not valid: " + e.getMessage());
        } catch (IOException e)
        {
            return OperationResult.Fail("YIKES: " + e.getMessage());
        }

        return OperationResult.Success(true);
    }

}
