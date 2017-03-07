package dk.cs.dwebtek;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by morten on 2/26/17.
 */
public class OAuthServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String returnCode = req.getParameter("code");
        String returnUser = req.getParameter("username");
        String postData = "grant_type=authorization_code"           // say what kind of grant we are looking for
                + "&code=" + returnCode                             // send the code we just obtained
                + "&client_id=webtek"                               // can be any string, as long as it is not empty
                + "&client_secret=webtek"                           // can be any string, as long as it is not empty
                + "&redirect_uri=http://localhost:8081/oauth-callback";  // must be the same uri we used as redirect_uri in the link above
        URL token = new URL("https://services.brics.dk/java/dovs-auth/token");
        HttpURLConnection conn = (HttpURLConnection) token.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-length", ""+postData.length());
        conn.setDoOutput(true);

        DataOutputStream dataOS = new DataOutputStream(conn.getOutputStream()); // Fetching the data from the connection
        dataOS.writeBytes(postData);
        dataOS.flush();
        dataOS.close();

        if(conn.getResponseCode() == 200) // Authorized
        {
            req.getSession().setAttribute("Authed", true);

            resp.sendRedirect("http://localhost:8081/admin/index.xhtml");
            System.out.println("Logged in!");
        }
        else
        {
            resp.sendRedirect("http://localhost:8081/login.xhtml");
            System.out.println("Authentication failed!");
        }
    }
}
