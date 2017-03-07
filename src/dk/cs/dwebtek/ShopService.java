package dk.cs.dwebtek;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public List<Item> getItems() {
        System.out.println("test1");
        return CloudServiceSingleton.getInstance().itemsFromXMLToJava();

        /*
        System.out.println("Hej");
        //You should get the items from the cloud server.
        //In the template we just construct some simple data as an array of objects
        // Here we have the JSON automated automatically by having models.
        ArrayList<ItemTest> items = new ArrayList<>();
        items.add(new ItemTest(1, "Stetson hat", 200 + priceChange));
        items.add(new ItemTest(2, "Rifle", 500 + priceChange));
        return items;
        */
    }


 /*   @GET
    @Path("items/manual")
    public String getItemsConstructedManually() {
        //You should get the items from the cloud server.
        //In the template we just construct some simple data as an array of objects
        //Here we output construct the JSON manually
        JSONArray array = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 1);
        jsonObject1.put("name", "Stetson hat");
        jsonObject1.put("price", 200 + priceChange);
        array.put(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", 2);
        jsonObject2.put("name", "Rifle");
        jsonObject2.put("price", 500 + priceChange);
        array.put(jsonObject2);

        priceChange++;

        //You can create a MessageBodyWriter so you don't have to call toString() every time
        return array.toString();
    }*/


}
