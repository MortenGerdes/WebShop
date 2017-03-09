package dk.cs.dwebtek;

import dk.cs.dwebtek.Requests.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.output.XMLOutputter;

import javax.print.Doc;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CloudService
{
    public static final Namespace NS = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
    public static final String SHOP_KEY = "FD74363042702C59A8BD3A21";

    private HttpURLConnection conn = null;

    /**
     * Builds a modifyItem xml-element
     *
     * @param itemID
     * @param itemName
     * @param itemPrice
     * @param itemURL
     * @param itemDescription
     * @return A OperationResult with the XML as a string in the result, if the XML is well-formed.
     */
    public OperationResult<Document> createBodyForModifyItem(int itemID, String itemName, int itemPrice, String itemURL, String itemDescription)
    {
        // itemDescription is a string, possible with XML content. We have to transform it.
        OperationResult<Element> itemDescRes = convertItemDescription(itemDescription);
        if (!itemDescRes.isSuccess())
        {
            return OperationResult.Fail("Failed to add XML to itemdes");
        }
        // HINT: You can get the XML as a string from a Document by XMLOutputter
        Element modifyItem = new Element("modifyItem", NS);
        modifyItem.addContent(new Element("shopKey", NS).setText(SHOP_KEY));
        modifyItem.addContent(new Element("itemID", NS).setText("" + itemID));
        modifyItem.addContent(new Element("itemName", NS).setText(itemName));
        modifyItem.addContent(new Element("itemPrice", NS).setText("" + itemPrice));
        modifyItem.addContent(new Element("itemURL", NS).setText(itemURL));
        modifyItem.addContent(itemDescRes.getResult()); //TODO: Check if this is a fact
        Document doc = new Document();
        doc.setContent(modifyItem);
        System.out.println(validate(doc).getMessage());
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }

    public OperationResult<Document> createBodyForItem(String name)
    {
        Element createItem = new Element("createItem", NS);
        createItem.addContent(new Element("shopKey", NS).setText(SHOP_KEY));
        createItem.addContent(new Element("itemName", NS).setText(name));

        Document doc = new Document();
        doc.setRootElement(createItem);
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }

    public OperationResult<Document> createBodyForItemStock(int itemID, int adjustment)
    {
        Element adjust = new Element("adjustItemStock", NS);
        adjust.addContent(new Element("shopKey", NS).setText(SHOP_KEY));
        adjust.addContent(new Element("itemID", NS).setText("" + itemID));
        adjust.addContent(new Element("adjustment", NS).setText("" + adjustment));

        Document doc = new Document();
        doc.setRootElement(adjust);
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }

    public OperationResult<Document> createBodyForDelete(int itemID)
    {
        Element toDelete = new Element("deleteItem", NS);
        toDelete.addContent(new Element("shopKey", NS).setText(SHOP_KEY));
        toDelete.addContent(new Element("itemID", NS).setText("" + itemID));

        Document doc = new Document();
        doc.setRootElement(toDelete);
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }

    public OperationResult<Document> createBodyForCustomer(String cName, String cPass)
    {
        Element customer = new Element("createCustomer", NS);
        customer.addContent(new Element("shopKey", NS).setText(SHOP_KEY));
        customer.addContent(new Element("customerName", NS).setText(cName));
        customer.addContent(new Element("customerPass", NS).setText(cPass));

        Document doc = new Document();
        doc.setRootElement(customer);
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }

    public OperationResult<Document> createBodyForLogin(String cName, String cPass)
    {
        Element login = new Element("login", NS);
        login.addContent(new Element("customerName", NS).setText(cName));
        login.addContent(new Element("customerPass", NS).setText(cPass));

        Document doc = new Document();
        doc.setRootElement(login);
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }

    public OperationResult<Document> createBodyForSellItem(int itemID, int customerID, int saleAmount)
    {
        Element customer = new Element("sellItems", NS);
        customer.addContent(new Element("shopKey", NS).setText(SHOP_KEY));
        customer.addContent(new Element("itemID", NS).setText("" + itemID));
        customer.addContent(new Element("customerID", NS).setText("" + customerID));
        customer.addContent(new Element("saleAmount", NS).setText("" + saleAmount));

        Document doc = new Document();
        doc.setRootElement(customer);
        if (validate(doc).isSuccess())
        {
            return OperationResult.Success(doc);
        } else
        {
            return OperationResult.Fail(validate(doc).getMessage());
        }
    }


    /**
     * Converts a string to an element.
     *
     * @param content the body of the document-element
     * @return A OperationResult where the result is an element if the xml is well-formed
     */
    private OperationResult<Element> convertItemDescription(String content)
    {
        // HINT: surround the content with "<document>" and "</document>" before parsing

        try
        {
            String formatted = "<itemDescription><document>" + content + "</document></itemDescription>";
            SAXBuilder builder = new SAXBuilder();
            InputStream stream = new ByteArrayInputStream(formatted.getBytes("UTF-8"));
            Document doc = builder.build(stream);
            Element elem = doc.getRootElement().clone();
            setNamespace(elem);
            elem.detach();
            //doc.setRootElement(new Element("itemDescription", NS));
            return OperationResult.Success(elem);
        } catch (UnsupportedEncodingException e)
        {
        } catch (JDOMException e)
        {
            System.out.println("Failed to handle ItemDescription's XML in CovertItemDes Method");
        } catch (IOException e)
        {
        }
        System.out.println("Stuff happened that shouldn't in Cloudservice");
        return OperationResult.Fail("Exception occured during ItemDescription generation");
    }

    /**
     * Sets the namespace on the element - what about the children??
     *
     * @param child the xml-element to have set the namespace
     */
    private void setNamespace(Element child)
    {
        if (child.getChildren() != null)
        {
            for (Element elem : child.getChildren())
            {
                setNamespace(elem);
            }
        }
        System.out.println("Setting NS on element: " + child.getName());
        child.setNamespace(NS);
    }

    public String handleItemDes(Element child, String message)
    {
        if (child.getChildren() != null)
        {
            for (Element elemChild : child.getChildren())
            {
                message += new XMLOutputter().outputString(elemChild);
                handleItemDes(elemChild, message);
            }
        }
        message = Utils.formatItemDescriptionToHTMLTags(message);
        return message;
    }

    public List<Shop> shopsFromXMLToJava(){
        Namespace ns = CloudServiceSingleton.getInstance().NS;
        List<Shop> javaItems = new ArrayList<>();
        OperationResult<Document> xmlShops = CloudServiceSingleton.getInstance().listShops();

        for(Element shop : xmlShops.getResult().getRootElement().getChildren("shop", ns)){
            Shop javaShop = new Shop();

            javaShop.setShopID(shop.getChildText("shopID", ns));
            javaShop.setShopName(shop.getChildText("shopName", ns));
            javaShop.setShopURL(shop.getChildText("shopURL", ns));

            javaItems.add(javaShop);
        }

        return javaItems;
    }

    public List<Item> itemsFromXMLToJava(){
        return itemsFromXMLToJava("303");
    }

    public List<Item> itemsFromXMLToJava(String shopID)
    {
        Namespace ns = CloudServiceSingleton.getInstance().NS;
        List<Item> javaItems = new ArrayList<>();
        List<Item> deletedJavaItems = new ArrayList<>();
        OperationResult<Document> xmlItems = CloudServiceSingleton.getInstance().listItems("303");
        OperationResult<Document> deletedXMLItems = CloudServiceSingleton.getInstance().listDeletedItems("303");
        for (Element child : deletedXMLItems.getResult().getRootElement().getChildren())
        {
            Item javaItem = new Item();
            javaItem.setItemID(Integer.parseInt(child.getText()));
            deletedJavaItems.add(javaItem);
        }
        for (Element item : xmlItems.getResult().getRootElement().getChildren("item", ns))
        {
            if (containsId(deletedJavaItems, Integer.parseInt(item.getChildText("itemID", ns))))
            {
                continue;
            }
            Item javaItem = new Item();
            javaItem.setItemID(Integer.parseInt(item.getChildText("itemID", ns)));
            javaItem.setItemName(item.getChildText("itemName", ns));
            javaItem.setItemURL(item.getChildText("itemURL", ns));
            javaItem.setItemPrice(Integer.parseInt(item.getChildText("itemPrice", ns)));
            javaItem.setItemStock(Integer.parseInt(item.getChildText("itemStock", ns)));
            javaItem.setItemDescription(CloudServiceSingleton.getInstance().handleItemDes(item.getChild("itemDescription", ns)));
            javaItems.add(javaItem);
        }
        return javaItems;
    }

    public List<CustomerSale> salesFromXMLToJava(int customerID)
    {
        List<CustomerSale> sale = new ArrayList<>();
        List<Item> shopItems = itemsFromXMLToJava();
        OperationResult<Document> customerSales = listCustomerSales(customerID);

        for (Element item : customerSales.getResult().getRootElement().getChildren("sale", NS))
        {
            CustomerSale cs = new CustomerSale();
            cs.setCustomerID(Integer.parseInt(item.getChildText("customerID", NS)));
            cs.setItemID(Integer.parseInt(item.getChildText("itemID", NS)));
            cs.setSaleTime(Long.parseLong(item.getChildText("saleTime", NS)));
            cs.setSaleAmount(Integer.parseInt(item.getChildText("saleAmount", NS)));
            cs.setShopID(Integer.parseInt(item.getChildText("shopID", NS)));
            cs.setSaleItemPrice(Integer.parseInt(item.getChildText("saleItemPrice", NS)));

            for (Item theItem : shopItems)
            {
                if (theItem.getItemID() == cs.getItemID())
                {
                    cs.setItemName(theItem.getItemName());
                    cs.setItemURL(theItem.getItemURL());
                }
            }
            sale.add(cs);
        }
        return sale;
    }

    public Customer getCustomerByName(String name)
    {
        Customer cs = new Customer();
        OperationResult<Document> items = listCustomers();
        if (items.isSuccess())
        {
            Document doc = items.getResult();

            for (Element child : doc.getRootElement().getChildren())
            {
                String pastId = "";
                for (Element child2 : child.getChildren())
                {
                    if (child2.getText().equals(name) && child2.getName() == "customerName")
                    {
                        cs.setName(child2.getText());
                        cs.setId(pastId);
                        cs.setSales(salesFromXMLToJava(Integer.parseInt(pastId)));
                    }
                    if (child2.getName() == "customerID")
                    {
                        pastId = child2.getText();
                    }
                }
            }
        }
        return cs;
    }

    public boolean containsId(List<Item> list, int id)
    {
        for (Item item : list)
        {
            if (item.getItemID() == id)
            {
                return true;
            }
        }
        return false;
    }

    public Item getItemByID(int id)
    {
        List<Item> items = itemsFromXMLToJava();

        for (Item item : items)
        {
            if (item.getItemID() == id)
            {
                return item;
            }
        }
        return null;
    }

    public String handleItemDes(Element child)
    {
        return handleItemDes(child, "");
    }


    /**
     * Validates the document according to the schema cloud.xsd
     *
     * @param doc
     * @return OperationResult with information about success or failure
     */
    private OperationResult<Object> validate(Document doc)
    {
        String xml;
        SAXBuilder builder;
        XMLReaderJDOMFactory factory;
        URL url = getClass().getClassLoader().getResource("cloud.xsd");

        try
        {
            factory = new XMLReaderXSDFactory(url);
        } catch (JDOMException e)
        {
            return OperationResult.Fail("Could not find schema");
        }

        xml = new XMLOutputter().outputString(doc);
        builder = new SAXBuilder(factory);
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

    public OperationResult<Document> createItem(String name)
    {
        return CloudComm.performRequest(new CreateItemPostRequest(name, createBodyForItem(name).getResult()));
    }

    public OperationResult<Document> listItems(String shopID) // TODO: Check if it's one long string we get, or seperated strings (array)
    {
        return CloudComm.performRequest(new ListItemsRequest(shopID));
    }

    public OperationResult<Document> listDeletedItems(String shopID)
    {
        return CloudComm.performRequest(new ListDeleteItems(shopID));
    }

    public OperationResult<Document> listCustomers()
    {
        return CloudComm.performRequest(new ListCustomersRequest());
    }

    public OperationResult<Document> modifyItem(int itemID, String itemName, int itemPrice, String itemUrl, String itemDescriptionXml)
    {
        return CloudComm.performRequest(new ModifyItemRequest(createBodyForModifyItem(itemID, itemName, itemPrice, itemUrl, itemDescriptionXml).getResult()));
    }

    public OperationResult<Document> listShops(){
        return CloudComm.performRequest(new ListShopsRequest());
    }

    public OperationResult<Document> adjustItemStock(int itemID, int adjustment)
    {
        return CloudComm.performRequest(new AdjustItemStock(createBodyForItemStock(itemID, adjustment).getResult()));
    }

    public OperationResult<Document> deleteItem(int itemID)
    {
        return CloudComm.performRequest(new DeleteItemRequest(createBodyForDelete(itemID).getResult()));
    }

    public OperationResult<Document> createCustomer(String cName, String cPass)
    {
        return CloudComm.performRequest(new CreateCustomerRequest(createBodyForCustomer(cName, cPass).getResult()));
    }

    public OperationResult<Document> loginRequest(String cName, String cPass)
    {
        return CloudComm.performRequest(new LoginRequest(createBodyForLogin(cName, cPass).getResult()));
    }

    public OperationResult<Document> sellItem(int itemID, int customerID, int saleAmount)
    {
        return CloudComm.performRequest(new SellItemRequest(createBodyForSellItem(itemID, customerID, saleAmount).getResult()));
    }

    public OperationResult<Document> listCustomerSales(int customerID)
    {
        return CloudComm.performRequest(new ListCustomerSalesRequest(customerID));
    }

    public OperationResult<Document> listShopSales(String shopKey)
    {
        return CloudComm.performRequest(new ListShopSalesRequest(shopKey));
    }
}
