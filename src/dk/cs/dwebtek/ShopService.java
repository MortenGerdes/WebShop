package dk.cs.dwebtek;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("shop")
public class ShopService
{
    /**
     * Our Servlet session. We will need this for the shopping basket
     */
    HttpSession session;

    public ShopService(@Context HttpServletRequest servletRequest)
    {
        session = servletRequest.getSession();
    }

    /**
     * Make the price increase per request (for the sake of example)
     */
    private static int priceChange = 0;

    @GET
    @Path("items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems()
    {
        System.out.println("test1");
        return CloudServiceSingleton.getInstance().itemsFromXMLToJava();
    }

    @GET
    @Path("customer")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getLoggedInCustomer()
    {
        if(session.getAttribute("loggedIn") != null)
        {
            return CloudServiceSingleton.getInstance().getCustomerByName((String)session.getAttribute("loggedIn"));
        }
        return null;
    }

    @GET
    @Path("customerSales")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerSale> getCustomerSales(){
        CloudService cs = new CloudService();

        return cs.salesFromXMLToJava(Integer.parseInt(getLoggedInCustomer().getId()));
    }

    @POST
    @Path("login")
    public Response login(@FormParam("username") String user, @FormParam("password") String pass) throws URISyntaxException
    {
        if(session.getAttribute("loggedIn") == null)
        {
            URI target = new URI("http://localhost:8081/login.html");
            if (Week3Runner.login(new String[]{"", user, pass}) == true)
            {
                target = new URI("http://localhost:8081/index.html");
                session.setAttribute("loggedIn", user);
            } else
            {
                target = new URI("http://localhost:8081/login.html");
            }
            return Response.seeOther(target).build();
        }
        else
        {
            return Response.seeOther(new URI("http://localhost:8081/index.html")).build();
        }
    }
}
