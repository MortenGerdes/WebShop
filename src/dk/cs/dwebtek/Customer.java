package dk.cs.dwebtek;

import java.util.List;

/**
 * Created by morte_000 on 08-03-2017.
 */
public class Customer
{
    private String name;
    private String id;
    private List<CustomerSale> sales;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<CustomerSale> getSales()
    {
        return sales;
    }

    public void setSales(List<CustomerSale> sales)
    {
        this.sales = sales;
    }
}
