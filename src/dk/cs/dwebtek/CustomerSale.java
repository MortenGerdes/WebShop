package dk.cs.dwebtek;

/**
 * Created by morte_000 on 08-03-2017.
 */
public class CustomerSale
{
    private long saleTime;
    private int saleAmount;
    private int shopID;
    private int itemID;
    private int customerID;
    private int saleItemPrice;

    public long getSaleTime()
    {
        return saleTime;
    }

    public void setSaleTime(long saleTime)
    {
        this.saleTime = saleTime;
    }

    public int getSaleAmount()
    {
        return saleAmount;
    }

    public void setSaleAmount(int saleAmount)
    {
        this.saleAmount = saleAmount;
    }

    public int getShopID()
    {
        return shopID;
    }

    public void setShopID(int shopID)
    {
        this.shopID = shopID;
    }

    public int getItemID()
    {
        return itemID;
    }

    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    public int getSaleItemPrice()
    {
        return saleItemPrice;
    }

    public void setSaleItemPrice(int saleItemPrice)
    {
        this.saleItemPrice = saleItemPrice;
    }
}
