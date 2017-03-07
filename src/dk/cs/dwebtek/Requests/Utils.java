package dk.cs.dwebtek.Requests;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;

/**
 * Created by morten on 2/17/17.
 */
public class Utils
{
    public static String streamToString(InputStream is)
    {
        try
        {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1)
            {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Document stringToDoc(String message)
    {
        SAXBuilder builder = new SAXBuilder();
        InputStream stream = null;
        Document doc = null;
        try
        {
            stream = new ByteArrayInputStream(message.getBytes("UTF-8"));
            doc = builder.build(stream);
            return doc;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (JDOMException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatItemDescriptionToHTMLTags(String text)
    {
        text = text.replaceAll("<w:bold>", "<strong>");
        text = text.replaceAll("</w:bold>", "</strong>");

        text = text.replaceAll("<w:italics>", "<em>");
        text = text.replaceAll("</w:itelics>", "</em>");

        text = text.replaceAll("<w:list>", "<ui>");
        text = text.replaceAll("</w:list>", "</ui>");

        return text;
    }
}
