package dk.cs.dwebtek;

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
public class ShopService {
    /**
     * Our Servlet session. We will need this for the shopping basket
     */
    HttpSession session;

    public ShopService(@Context HttpServletRequest servletRequest) {
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

    @POST
    @Path("itemswithpost")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.WILDCARD)
    public List<Item> getItems(String x)
    {
        JSONObject cusInfo = new JSONObject(x);
        String id = cusInfo.getString("id");
        return CloudServiceSingleton.getInstance().itemsFromXMLToJava(""+id);
    }


    @GET
    @Path("customer")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getLoggedInCustomer() {
        if (session.getAttribute("loggedIn") != null) {
            return CloudServiceSingleton.getInstance().getCustomerByName((String) session.getAttribute("loggedIn"));
        }
        return null;
    }

    @GET
    @Path("customerSales")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerSale> getCustomerSales() {
        CloudService cs = new CloudService();

        return cs.salesFromXMLToJava(Integer.parseInt(getLoggedInCustomer().getId()));
    }

    @GET
    @Path("shopList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Shop> getShopList(){
        CloudService cs = new CloudService();

        return cs.shopsFromXMLToJava();
    }



    @POST
    @Path("login")
    @Consumes(MediaType.WILDCARD)
    public String login(String input) throws URISyntaxException {
        JSONObject cusInfo = new JSONObject(input);
        String user = cusInfo.getString("userName");
        String pass = cusInfo.getString("passWord");

        if (session.getAttribute("loggedIn") == null) {
            if (Week3Runner.login(new String[]{"", user, pass}) == true) {
                session.setAttribute("loggedIn", user);
                return "OK";
            } else {
                return "FAIL";
            }
        } else {
            return "ACTIVE";
        }
    }

    @POST
    @Path("addbasket")
    public void saveToBasket(@QueryParam("itemID") int id)
    {
        if(isLoggedIn())
        {
            saveToBasket(CloudServiceSingleton.getInstance().getItemByID(id));
        }

    }

    public void saveToBasket(Item item) {
        List<Item> items = getCustomerBasket();
        if (!CloudServiceSingleton.getInstance().containsId(items, item.getItemID())) {
            items.add(item);
            session.setAttribute((((String) session.getAttribute("loggedIn")) + "basket"), items);
        }
    }

    @GET
    @Path("getbasket")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getCustomerBasket() {
        if (!isLoggedIn()) {
            return null;
        }
        if (session.getAttribute(((String) session.getAttribute("loggedIn")) + "basket") == null) {
            session.setAttribute((((String) session.getAttribute("loggedIn")) + "basket"), new ArrayList<Item>());
        }
        return (List<Item>) session.getAttribute(((String) session.getAttribute("loggedIn")) + "basket");
    }

    @GET
    @Path("verifypurchase")
    public void verifyPurchase()
    {

    }

    @GET
    @Path("clearBasket")
    public void clearBasket(){
        session.setAttribute((((String) session.getAttribute("loggedIn")) + "basket"), new ArrayList<Item>());;
        System.out.println("Cleared basket");
    }

    @POST
    @Path("sellItem")
    @Consumes(MediaType.WILDCARD)
    public void sellItemsInBasket(String x)
    {
        JSONObject cusInfo = new JSONObject(x);
        int itemID = cusInfo.getInt("itemID");
        int saleAmount = cusInfo.getInt("wantedToSell");
        CloudServiceSingleton.getInstance().sellItem(itemID, Integer.parseInt(getLoggedInCustomer().getId()), saleAmount);
    }

    @POST
    @Path("newCustomer")
    @Consumes(MediaType.WILDCARD)
    public String newCustomer(String input) throws URISyntaxException {
        JSONObject cusInfo = new JSONObject(input);
        String userName = cusInfo.getString("userName");
        String pass = cusInfo.getString("passWord");

        if (CloudServiceSingleton.getInstance().createCustomer(userName, pass).isSuccess()) {
            return "OK";
        }
        return "FAILED";
    }

    @POST
    @Path("searchitem")
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> filterSearch(String x) {
        JSONObject obj = new JSONObject(x);
        String searchQuery = obj.getString("query");
        String shopID = obj.getString("shopID");
        List<Item> oldItems = CloudServiceSingleton.getInstance().itemsFromXMLToJava(shopID);
        List<Item> newItems = new ArrayList<>();
        for (Item oldItem : oldItems) {
            if (oldItem.getItemName().toLowerCase().contains(searchQuery.toLowerCase())) {
                newItems.add(oldItem);
            }
        }
        return newItems;
    }

    @GET
    @Path("isLoggedIn")
    public boolean isLoggedIn() {
        if (session.getAttribute("loggedIn") != null) {
            return true;
        }
        return false;
    }

    @GET
    @Path("logOut")
    public boolean logOut() {
        System.out.println("logger ud");
        session.setAttribute("loggedIn", null);
        return true;
    }

    public boolean verifyBuyAblility(Item item, int toBuy)
    {
        return item.getItemStock() >= toBuy;
    }
}
