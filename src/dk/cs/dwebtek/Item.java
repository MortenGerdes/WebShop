package dk.cs.dwebtek;


/**
 * Created by morten on 2/23/17.
 */
public class Item
{
    private int itemID;
    private String itemName;
    private String itemURL;
    private int itemPrice;
    private int itemStock;
    private String itemDescription;

    public int getItemID()
    {
        return itemID;
    }

    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemURL()
    {
        return itemURL;
    }

    public void setItemURL(String itemURL)
    {
        this.itemURL = itemURL;
    }

    public int getItemPrice()
    {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice)
    {
        this.itemPrice = itemPrice;
    }

    public int getItemStock()
    {
        return itemStock;
    }

    public void setItemStock(int itemStock)
    {
        this.itemStock = itemStock;
    }

    public String getItemDescription()
    {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription)
    {
        this.itemDescription = itemDescription;
    }

    public String toString()
    {
        return "ItemID = " + itemID + ". ItemName = " + itemName + ". ItemURL = " + itemURL + ". ItemPrice = " + itemPrice +
                ". ItemStock = " + itemStock + ". ItemDescription = " + itemDescription + ".";
    }
}
