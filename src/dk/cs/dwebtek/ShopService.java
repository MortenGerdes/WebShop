package dk.cs.dwebtek;

import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

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
                target = new URI("http://localhost:8081/failedLogin.html");
            }
            return Response.seeOther(target).build();
        }
        else
        {
            return Response.seeOther(new URI("http://localhost:8081/index.html")).build();
        }
    }

    public boolean isLoggedIn()
    {
        if(session.getAttribute("loggedIn") != null)
        {
            return true;
        }
        return false;
    }

    @POST
    @Path("addbasket")
    public void saveToBasket(@QueryParam("itemID") int id)
    {
        saveToBasket(CloudServiceSingleton.getInstance().getItemByID(id));
    }

    public void saveToBasket(Item item)
    {
        List<Item> items = getCustomerBasket();
        if(!items.contains(item))
        {
            items.add(item);
            session.setAttribute((((String) session.getAttribute("loggedIn")) + "basket"), items);
        }
    }

    @GET
    @Path("getbasket")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getCustomerBasket()
    {
        if(!isLoggedIn())
        {
            return null;
        }
        if(session.getAttribute(((String)session.getAttribute("loggedIn")) + "basket") == null)
        {
            session.setAttribute((((String)session.getAttribute("loggedIn")) + "basket"), new ArrayList<Item>());
        }
        return (List<Item>)session.getAttribute(((String)session.getAttribute("loggedIn")) + "basket");
    }

    @POST
    @Path("newCustomer")
    @Consumes(MediaType.WILDCARD)
    public String newCustomer(String x) throws URISyntaxException {
        JSONObject cusInfo = new JSONObject(x);
        String userName = cusInfo.getString("userName");
        String pass = cusInfo.getString("passWord");

        if (CloudServiceSingleton.getInstance().createCustomer(userName, pass).isSuccess()) {
            return "OK";
        }
        return "FAILED";
    }
}
