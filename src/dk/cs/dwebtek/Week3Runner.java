package dk.cs.dwebtek;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 * Created by mortenkrogh-jespersen on 06/02/2017.
 */
public class Week3Runner {
    private static CloudService service = new CloudService();

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Not enough arguments...");
            return;
        }

        switch (args[0]) {
            case "createItem":
                createItem(args[1]);
                break;
            case "modifyItem":
                modifyItem(args);
                break;
            case "listItems":
                listItems(args[1]);
                break;
            case "listDeletedItems":
                listDeletedItems(args[1]);
                break;
            case "listCustomers":
                listCustomers();
                break;
            case "adjustStock":
                adjustItem(args);
                break;
            case "deleteItem":
                deleteItem(args[1]);
                break;
            case "createCustomer":
                createCustomer(args);
                break;
            case "login":
                login(args);
                break;
            case "sellItem":
                sellItem(args);
                break;
            case "listShopSales":
                listShopSales(args[1]);
                break;
            case "listCustomerSales":
                listCustomerSales(args);
                break;

            default:
                System.out.println("Command not valid");
        }
    }

    public static void createItem(String name) {
        OperationResult<Document> result = service.createItem(name);
        if (result.isSuccess()) {
            System.out.printf("Item created. itemID: " + result.getResult().getRootElement().getText() + "\n");
        } else {
            System.out.println(result.getMessage());
        }
    }

    private static void modifyItem(String[] args) {
        try {
            int itemID = Integer.parseInt(args[1]);
            String itemName = args[2];
            int itemPrice = Integer.parseInt(args[3]);
            String itemUrl = args[4];
            String itemDescriptionXml = args[5];
            OperationResult res = service.modifyItem(itemID, itemName, itemPrice, itemUrl, itemDescriptionXml);
            if (res.isSuccess()) {
                System.out.printf("Success\n");
            } else {
                System.out.println(res.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number inserted. Make sure the ID writting is actually in the list of items and that price a positive number");
        }

    }

    private static void listItems(String shopID) {
        // Print out the items here:
        OperationResult<Document> items = service.listItems(shopID);
        if (items.isSuccess()) {
            Document doc = items.getResult();

            for (Element child : doc.getRootElement().getChildren()) {
                System.out.println();
                for (Element child2 : child.getChildren()) {
                    System.out.print(child2.getName() + " = " + child2.getText() + ", ");
                    if (child2.getName() == "itemDescription") {
                        String text = new XMLOutputter().outputString(child2);
                        System.out.print(text.replaceAll("<.*?>", ""));
                    }
                }
            }
            System.out.println();
        }
    }

    private static void listDeletedItems(String shopID) {
        // Print out the items here:
        OperationResult<Document> items = service.listDeletedItems(shopID);
        if (items.isSuccess()) {
            Document doc = items.getResult();

            for (Element child : doc.getRootElement().getChildren()) {
                System.out.println("Deleted itemID: " + child.getText());
            }
            System.out.println();
        }
    }

    private static void listCustomers() //
    {
        // Print out the items here:
        OperationResult<Document> items = service.listCustomers();
        if (items.isSuccess()) {
            Document doc = items.getResult();

            for (Element child : doc.getRootElement().getChildren()) {
                for (Element child2 : child.getChildren()) {
                    System.out.print(child2.getName() + " = " + child2.getText() + ", ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void listCustomerSales(String[] theArgs)
    {
        if (theArgs.length < 2){
            System.out.println("TOO FEW ARGS");
            return;
        }

        OperationResult<Document> customer = service.listCustomerSales(Integer.parseInt(theArgs[1]));

        if(customer.isSuccess()){
            Document document = customer.getResult();
            System.out.println("Customer " + theArgs[1] + " has bought these items: ");
            System.out.println("");

            for(Element child : document.getRootElement().getChildren()){
                for(Element child2 : child.getChildren()){
                    System.out.println(child2.getName() + " = " + child2.getText() + ", ");
                }
                System.out.println();
            }
        }
    }

    private static void listShopSales(String shopKey) {
        // Print out all succesfull sales:
        // Test with imput "listShopSales FD74363042702C59A8BD3A21"
        OperationResult<Document> items = service.listShopSales(shopKey);

        if (items.isSuccess()) {
            Document doc = items.getResult();

            for (Element child : doc.getRootElement().getChildren()) {
                for (Element child2 : child.getChildren()) {
                    System.out.print(child2.getName() + " = " + child2.getText() + ", ");
                }
                System.out.println();
            }
        }
    }

    private static void adjustItem(String[] theArgs) {
        if (theArgs.length < 2) {
            System.out.println("TOO FEW ARGS");
            return;
        }
        OperationResult<Document> rawrXD = service.adjustItemStock(Integer.parseInt(theArgs[1]), Integer.parseInt(theArgs[2]));
        if (rawrXD.isSuccess()) {
            System.out.println("Adjusted item: " + theArgs[1] + " by adding: " + theArgs[2] + " to the total amount!");
        } else {
            System.out.println("Failed to adjust stock on item: " + theArgs[1] + ". Something went wrong. Ask Morten");
            System.out.println(rawrXD.getMessage());
        }
    }

    private static void deleteItem(String itemID) {
        OperationResult<Document> rawrXD = service.deleteItem(Integer.parseInt(itemID));
        if (rawrXD.isSuccess()) {
            System.out.println("Succesfully deleted item with itemID: " + itemID);
        } else {
            System.out.println("Failed to delete Item");
        }
        listDeletedItems("303");
    }

    private static void createCustomer(String[] args) {
        OperationResult<Document> rawrXD = service.createCustomer(args[1], args[2]);
        if (rawrXD.isSuccess()) {
            System.out.println("Customer created");
        } else {
            System.out.println("Failed to create Customer");
        }
    }

    public static boolean login(String[] args) {
        OperationResult<Document> rawrXD = service.loginRequest(args[1], args[2]);
        if (rawrXD.isSuccess()) {
            System.out.println("Logged in succesfully on Customer: "
                    + rawrXD.getResult().getRootElement().getChild("customerName", CloudServiceSingleton.getInstance().NS).getText()
                    + " with customer ID: " + rawrXD.getResult().getRootElement().getChild("customerID", CloudServiceSingleton.getInstance().NS).getText());
            return true;
        } else {
            System.out.println("Failed to login on " + args[1] + "\nMost likely due to wrong Username/password!");
            return false;
        }
    }

    private static void sellItem(String[] args)
    //sellItem 52092 (eksempel paa et itemID) 1120 (jacks konto)
    {
        OperationResult<Document> rawrXD = service.sellItem(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        if (rawrXD.isSuccess()) {
            System.out.println("Succesfully contacted cloud and returned: "
                    + (new XMLOutputter().outputString(rawrXD.getResult())));
        } else {
            System.out.println(rawrXD.getMessage());
            System.out.println("Failed to contact server!");
        }

    }


}
