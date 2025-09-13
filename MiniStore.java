package UserHistories;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;

public class MiniStore {

    // data structure
    static ArrayList<String> names = new ArrayList<String>();
    static double[] prices = new double[100];
    static HashMap<String, Integer> stock = new HashMap<String, Integer>();
    static int totalProducts = 0;
    static double totalSales = 0;

    public static void main(String[] args) {

        // Welcome message
        JOptionPane.showMessageDialog(null, "Welcome to the Mini-Store");

        int option = 0;

        /* I set a while loop here because I want the menu to keep showing
           until the user explicitly decides to exit by choosing option 6
           and also in the task has the requirement to create this loop*/
        while (option != 6) {

            /* I built this menu string because I wanted an easy way to show
               all available actions to the user in one dialog */
            String menu = "MAIN MENU\n" +
                    "1. Add product\n" +
                    "2. List inventory\n" +
                    "3. Buy product\n" +
                    "4. Show statistics\n" +
                    "5. Search product by name\n" +
                    "6. Exit\n" +

                    "Select an option:";

            String input = JOptionPane.showInputDialog(menu);

            if (input == null) {
                option = 6;
            } else {
                try {
                    option = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    /* I added this catch because sometimes users type letters instead of numbers,
                       so this prevents the program from crashing and resets the option */
                    JOptionPane.showMessageDialog(null, "Enter a valid number");
                    option = 0;

                }
            }

            // Process the selected option
            if (option == 1) {
                addProduct();
            } else if (option == 2) {
                listInventory();
            } else if (option == 3) {
                buyProduct();
            } else if (option == 4) {
                showStatistics();
            } else if (option == 5) {
                searchProduct();
            } else if (option == 6) {
                showFinalTicket();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option");
            }
        }
    }

    // Method to add a product
    public static void addProduct() {

        /* I ask for product name here because it's the main identifier in our inventory */
        String name = JOptionPane.showInputDialog("enter the product name:");

        if (name == null || name.equals("")) {
            JOptionPane.showMessageDialog(null, "name cannot be empty");
            return;
        }

        /* I loop through existing names to avoid duplicates,
           because the system should not allow two products with the same name */
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(name)) {
                JOptionPane.showMessageDialog(null, "product already exists");
                return;
            }
        }

        // Ask for price
        String priceText = JOptionPane.showInputDialog("Enter the product price:");

        if (priceText == null || priceText.equals("")) {
            JOptionPane.showMessageDialog(null, "Price cannot be empty");
            return;
        }

        double price = 0;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            /* I added this exception because users could type text instead of numbers */
            JOptionPane.showMessageDialog(null, "Enter a valid price");
            return;
        }

        if (price <= 0) {
            JOptionPane.showMessageDialog(null, "Price must be greater than 0");
            return;
        }

        // Ask for stock
        String stockText = JOptionPane.showInputDialog("Enter the stock quantity:");
        if (stockText == null || stockText.equals("")) {
            JOptionPane.showMessageDialog(null, "Stock cannot be empty");
            return;
        }

        int stockQuantity = 0;
        try {
            stockQuantity = Integer.parseInt(stockText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid quantity");
            return;
        }
        if (stockQuantity < 0) {
            JOptionPane.showMessageDialog(null, "Stock cannot be negative");
            return;
        }

        /* At this point everything is validated, so I store the data into our structures:
           - names goes into ArrayList
           - prices stored in the array by index
           - stock saved in HashMap using the name as key */
        names.add(name);
        prices[totalProducts] = price;
        stock.put(name, stockQuantity);
        totalProducts++;

        JOptionPane.showMessageDialog(null,
                "Product added:\n" +
                        "Name: " + name + "\n" +
                        "Price: $" + price + "\n" +
                        "Stock: " + stockQuantity);
    }

    // Method to list inventory
    public static void listInventory() {

        /* If there are no products, I directly return with a message
           because showing an empty inventory makes no sense */
        if (names.size() == 0) {
            JOptionPane.showMessageDialog(null, "No products in inventory");
            return;
        }

        String inventory = "´Product inventory \n\n";

        /* I loop through names with their index because I need to connect
           each name with its price and stock */
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            double price = prices[i];
            int stockQuantity = stock.get(name);

            inventory = inventory + "Product: " + name + "\n";
            inventory = inventory + "Price: $" + price + "\n";
            inventory = inventory + "Stock: " + stockQuantity + "\n\n";
        }

        JOptionPane.showMessageDialog(null, inventory);
    }

    // Method to buy a product
    public static void buyProduct() {
        if (names.size() == 0) {
            JOptionPane.showMessageDialog(null, "No products available");
            return;
        }// first I need to verify if the store have some product to buy, if the store don't have products
        // I return the user to the principal menu

        /* I build this string of products to show the customer what’s available
           and to prevent them from buying items that don’t exist */
        String products = "available products:\n\n";
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            int stockQuantity = stock.get(name);
            if (stockQuantity > 0) {
                products = products + names.get(i) + " - $" + prices[i] + " (Stock: " + stockQuantity + ")\n";
            }
        }// Here I created relations between each variable to, so I Connect everything with the index of the arraylist


        String productName = JOptionPane.showInputDialog(products + "\nEnter the name of the product to buy:");

        if (productName == null || productName.equals("")) {
            return;
        }

        /* I search for the product index here because I need to match the name
           with its price stored in the array */
        int index = -1;
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(productName)) {
                index = i;
                break;
            }
        } /* This is an interesting part, because I searched at the web the "-1", and we need to know something,
        we're working with indexes and arrays, so the products will be in an array, but, if I put a product
         that isn't at the arrayList of the products, the product I searched will be "-1"
*/

        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Product not found");
            return;
        }

        int availableStock = stock.get(productName);

        if (availableStock <= 0) {
            JOptionPane.showMessageDialog(null, "Product out of stock");
            return;
        }

        // Ask for quantity
        String quantityText = JOptionPane.showInputDialog(
                "Product: " + productName + "\n" +
                        "Price: $" + prices[index] + "\n" +
                        "Available stock: " + availableStock + "\n\n" +
                        "Enter the quantity to buy:");

        if (quantityText == null || quantityText.equals("")) {
            return;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid quantity");
            return;
        }

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null, "Quantity must be greater than 0");
            return;
        }

        if (quantity > availableStock) {
            JOptionPane.showMessageDialog(null, "Not enough stock available");
            return;
        }

        // Calculate total
        double unitPrice = prices[index];
        double totalPurchase = unitPrice * quantity;

        /* I used showConfirmDialog here because I want the user to confirm
           before modifying stock and increasing sales */
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Confirm purchase:\n\n" +
                        "Product: " + productName + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Unit price: $" + unitPrice + "\n" +
                        "Total: $" + totalPurchase + "\n\n" +
                        "Confirm purchase?");

        if (confirmation == JOptionPane.YES_OPTION) {
            stock.put(productName, availableStock - quantity);
            totalSales = totalSales + totalPurchase;

            JOptionPane.showMessageDialog(null,
                    "Purchase completed!\n" +
                            "Total paid: $" + totalPurchase + "\n" +
                            "Remaining stock: " + (availableStock - quantity));
        }
    }

    // Method to show statistics
    public static void showStatistics() {

        if (names.size() == 0) {
            JOptionPane.showMessageDialog(null, "No products to show statistics");
            return;
        }

        /* I initialize min and max with the first product because
           I need a base reference for comparison */
        double minPrice = prices[0];
        double maxPrice = prices[0];
        String cheapestProduct = names.get(0);
        String expensiveProduct = names.get(0);

        /* I iterate over all products to find the one with smallest and largest price */
        for (int i = 1; i < names.size(); i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
                cheapestProduct = names.get(i);
            }
            if (prices[i] > maxPrice) {
                maxPrice = prices[i];
                expensiveProduct = names.get(i);
            }
        }

        String statistics = "STATISTICS\n\n" +
                "Cheapest product: " + cheapestProduct + " - $" + minPrice + "\n" +
                "Most expensive product: " + expensiveProduct + " - $" + maxPrice + "\n\n" +
                "Total products: " + names.size() + "\n" +
                "Accumulated sales: $" + totalSales;

        JOptionPane.showMessageDialog(null, statistics);
    }

    // Method to search for a product
    public static void searchProduct() {

        if (names.size() == 0) {
            JOptionPane.showMessageDialog(null, "No products in inventory");
            return;
        }

        /* I ask for a search string and I compare using toLowerCase
           so the search is case-insensitive */
        String search = JOptionPane.showInputDialog("Enter the product name to search:");

        if (search == null || search.equals("")) {
            return;
        }

        String results = "SEARCH RESULTS:\n\n";
        boolean found = false;

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            if (name.toLowerCase().contains(search.toLowerCase())) {
                found = true;
                results = results + "Product: " + name + "\n";
                results = results + "Price: $" + prices[i] + "\n";
                results = results + "Stock: " + stock.get(name) + "\n\n";
            }
        }

        if (!found) {
            results = results + "No products found";
        }

        JOptionPane.showMessageDialog(null, results);
    }

    // Method to show final ticket
    public static void showFinalTicket() {

        /* I built this final ticket to summarize the session,
           showing inventory size and total sales before exit */
        String ticket = "FINAL TICKET\n\n" +
                "Products in inventory: " + names.size() + "\n" +
                "Total sales: $" + totalSales + "\n\n" +
                "Thank you for using Mini-Store!";

        JOptionPane.showMessageDialog(null, ticket);
    }
}
