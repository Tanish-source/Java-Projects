package com.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.*;
import com.DS.*;
import com.utils.JDBCUtils;

public class Customer {
    static Scanner scanner = new Scanner(System.in);
    static int defaultQuantity;
    static int tempCustomerId;
    static CartLinkedList cart = new CartLinkedList();
    static CustomCircularLL menuItemsList = new CustomCircularLL();

    Customer customer = new Customer();

    public static void customerMenu() {
        System.out.println("1. Registration");
        System.out.println("2. login");
        int choice = scanner.nextInt();
        if (choice == 1) {
            newCustomer();
        } else if (choice == 2) {
            existingCustomer();
        }
    }

    public static void newCustomer() {
        String name = validateName();
        String email = validateEmail();
        String password = validatePassword();
        String phone = validatePhone();
        System.out.println("Enter your address: ");
        String address = scanner.next();

        String query = "INSERT INTO Customer (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.setString(5, password);
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                tempCustomerId = generatedKeys.getInt(1);
                System.out.println("Welcome, " + name + "! Your customer ID is " + tempCustomerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void existingCustomer() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println("Enter Email: ");
            String email = scanner.next();
            System.out.println("Enter Password: ");
            String password = scanner.next();

            String query = "SELECT customer_id FROM Customer WHERE email = ? AND password = ?";
            try {
                Connection c = JDBCUtils.getConnection();
                PreparedStatement pst = c.prepareStatement(query);
                pst.setString(1, email);
                pst.setString(2, password);
                ResultSet resultSet = pst.executeQuery();
                if (resultSet.next()) {
                    tempCustomerId = resultSet.getInt(1);
                    System.out.println("Welcome back! Your customer ID is " + tempCustomerId);
                    customerOptions();
                    return;
                } else {
                    attempts++;
                    System.out.println("Invalid Email or Password!");
                    if (attempts == 3) {
                        System.out.println("You have exceeded the maximum number of attempts.");
                        System.out.println("Would you like to reset your password? (yes/no)");
                        String reset = scanner.next();
                        if (reset.equalsIgnoreCase("yes")) {
                            forgotPassword();
                            return;
                        } else {
                            System.exit(0);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void forgotPassword() {
        String email = validateEmail();
        String newPassword = validatePassword();
        System.out.println("Enter conform Password (Length must be 8 and contain at least 1 special character): ");
        String conformPassword = scanner.next();
        if (!newPassword.equals(conformPassword)) {
            while (true) {
                System.out.println("Enter conform Password (new password and conform password must be same): ");
                String password = scanner.next();
                if (newPassword == conformPassword) {
                    break;
                } else {
                    System.out.println(
                            "Invalid password. new password and conform password must be same.");
                }
            }
        }

        String query = "UPDATE Customer SET password = ? WHERE email = ?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(query);
            pst.setString(1, newPassword);
            pst.setString(2, email);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password reset successful. You can now log in with your new password.");
                existingCustomer();
            } else {
                System.out.println("Error resetting password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void customerOptions() {
        int choice;
        do {
            System.out.println("1. Show All Restaurant.");
            System.out.println("2. Show All MenuItem.");
            System.out.println("3. Show Most Rated Restaurant");
            System.out.println("4. Show Most Rated MenuItem");
            System.out.println("5. Add to Cart");
            System.out.println("6. Update cart");
            System.out.println("7. Remove from Cart");
            System.out.println("8. Place Order");
            System.out.println("9. Cancel Order");
            System.out.println("10. Check Order History");
            System.out.println("11. Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    showAllRestaurants();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    showAllMenuItems();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    showMostRatedRestaurant();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    showMostRatedMenuItems();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    addToCart();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 6:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    UpdateCart();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 7:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    removeFromCart();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 8:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    placeOrder();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 9:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    cancelOrder();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 10:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    checkOrderHistory();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    break;
                case 11:
                    System.exit(0);
                    break;
            }
        } while (choice != 8);
    }

    public static void showAllRestaurants() {
        try {
            Connection c = JDBCUtils.getConnection();
            String restaurantQuery = "SELECT restaurant_id, name, address, phone_no, rating FROM Restaurant";
            try {
                Statement statement = c.createStatement();
                ResultSet restaurantResultSet = statement.executeQuery(restaurantQuery);
                System.out.printf("%-15s %-30s %-50s %-20s %-10s%n", "Restaurant ID", "Name", "Address", "Phone No",
                        "Rating");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------");

                while (restaurantResultSet.next()) {
                    int restaurantId = restaurantResultSet.getInt("restaurant_id");
                    String name = restaurantResultSet.getString("name");
                    String address = restaurantResultSet.getString("address");
                    String phoneNo = restaurantResultSet.getString("phone_no");
                    int rating = restaurantResultSet.getInt("rating");

                    System.out.printf("%-15d %-30s %-50s %-20s %-10d%n", restaurantId, name, address, phoneNo, rating);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("\nDo you want to see the menu items of any restaurant? (yes/no)");
            String response = scanner.next();

            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Enter Restaurant ID: ");
                int selectedRestaurantId = scanner.nextInt();

                String menuItemQuery = "SELECT item_id, item_name, price, rating, quantity FROM MenuItem WHERE restaurant_id = ?";
                try (PreparedStatement pst = c.prepareStatement(menuItemQuery)) {
                    pst.setInt(1, selectedRestaurantId);
                    ResultSet menuItemResultSet = pst.executeQuery();

                    System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", "Item ID", "Item Name", "Price", "Rating",
                            "Quantity");
                    System.out.println("-------------------------------------------------------------");

                    while (menuItemResultSet.next()) {
                        int itemId = menuItemResultSet.getInt("item_id");
                        String itemName = menuItemResultSet.getString("item_name");
                        double price = menuItemResultSet.getDouble("price");
                        int rating = menuItemResultSet.getInt("rating");
                        int quantity = menuItemResultSet.getInt("quantity");

                        System.out.printf("%-10d %-30s %-10.2f %-10d %-10d%n", itemId, itemName, price, rating,
                                quantity);
                    }
                }

                System.out.println("\nDo you want to add any menu item to the cart? Enter Item ID or 0 to skip: ");
                int selectedItemId = scanner.nextInt();

                if (selectedItemId != 0) {
                    System.out.println("Enter Quantity: ");
                    int quantity = scanner.nextInt();

                    String itemDetailsQuery = "SELECT item_name, price FROM MenuItem WHERE item_id = ?";
                    try {
                        PreparedStatement pst = c.prepareStatement(itemDetailsQuery);
                        pst.setInt(1, selectedItemId);
                        ResultSet resultSet = pst.executeQuery();

                        if (resultSet.next()) {
                            String itemName = resultSet.getString("item_name");
                            double price = resultSet.getDouble("price");
                            defaultQuantity = resultSet.getInt("quantity");

                            if (defaultQuantity == 0) {
                                ShareResources shareResources = new ShareResources(c, selectedItemId);
                                Thread producer = new Producer(shareResources);
                                Thread consumer = new Consumer(shareResources);

                                producer.start();
                                consumer.start();

                                try {
                                    producer.join();
                                    consumer.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (quantity == 0 || quantity < 0 || quantity > defaultQuantity) {
                                System.out.println("Invalid quantity.");
                            } else if (quantity <= defaultQuantity && quantity > 0) {

                                String restaurantNameQuery = "SELECT name FROM Restaurant WHERE restaurant_id = ?";
                                try {
                                    PreparedStatement rpst = c.prepareStatement(restaurantNameQuery);
                                    rpst.setInt(1, selectedRestaurantId);
                                    ResultSet restaurantResultSet = rpst.executeQuery();
                                    if (restaurantResultSet.next()) {
                                        String restaurantName = restaurantResultSet.getString("name");

                                        CartItem existingItem = null;
                                        for (int i = 0; i < cart.size(); i++) {
                                            CartItem item = cart.get(i);
                                            if (item.getItemId() == selectedItemId) {
                                                existingItem = item;
                                                break;
                                            }
                                        }

                                        if (existingItem != null) {
                                            existingItem.setQuantity(existingItem.getQuantity() + quantity);
                                            System.out.println("Updated quantity of " + itemName + " in the cart.");
                                        } else {
                                            CartItem newItem = new CartItem(selectedItemId, selectedRestaurantId,
                                                    itemName, "", price,
                                                    quantity);

                                            cart.add(newItem);
                                            System.out.println("Item added to cart: " + itemName + " (Quantity: "
                                                    + quantity + ")");
                                        }
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Failed to update MenuItem quantity.");
                            }

                        } else {
                            System.out.println("Invalid Item ID.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAllMenuItems() {
        try {
            Connection c = JDBCUtils.getConnection();
            String menuItemQuery = "SELECT item_id, restaurant_id, item_name, price, rating, quantity FROM MenuItem";
            try {
                Statement st = c.createStatement();
                ResultSet menuItemResultSet = st.executeQuery(menuItemQuery);
                System.out.printf("%-10s %-15s %-30s %-10s %-10s %-10s%n", "Item ID", "Restaurant ID", "Item Name",
                        "Price", "Rating", "Quantity");
                System.out.println(
                        "-----------------------------------------------------------------------------------------");

                while (menuItemResultSet.next()) {
                    int itemId = menuItemResultSet.getInt("item_id");
                    int restaurantId = menuItemResultSet.getInt("restaurant_id");
                    String itemName = menuItemResultSet.getString("item_name");
                    double price = menuItemResultSet.getDouble("price");
                    int rating = menuItemResultSet.getInt("rating");
                    int quantity = menuItemResultSet.getInt("quantity");

                    System.out.printf("%-10d %-15d %-30s %-10.2f %-10d %-10d%n", itemId, restaurantId, itemName, price,
                            rating, quantity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("\nDo you want to add any menu item to the cart? Enter Item ID or 0 to skip: ");
            int selectedItemId = scanner.nextInt();

            if (selectedItemId != 0) {
                System.out.println("Enter Quantity: ");
                int quantity = scanner.nextInt();

                String itemDetailsQuery = "SELECT item_name, price, quantity, restaurant_id FROM MenuItem WHERE item_id = ?";
                try {
                    PreparedStatement pst = c.prepareStatement(itemDetailsQuery);
                    pst.setInt(1, selectedItemId);
                    ResultSet resultSet = pst.executeQuery();

                    if (resultSet.next()) {
                        String itemName = resultSet.getString("item_name");
                        double price = resultSet.getDouble("price");
                        int defaultQuantity = resultSet.getInt("quantity");
                        int restaurantId = resultSet.getInt("restaurant_id");

                        if (quantity <= 0 || quantity > defaultQuantity) {
                            System.out.println("Invalid quantity.");
                        } else {
                            String restaurantNameQuery = "SELECT name FROM Restaurant WHERE restaurant_id = ?";
                            try (PreparedStatement rpst = c.prepareStatement(restaurantNameQuery)) {
                                rpst.setInt(1, restaurantId);
                                ResultSet restaurantResultSet = rpst.executeQuery();
                                if (restaurantResultSet.next()) {
                                    String restaurantName = restaurantResultSet.getString("name");

                                    CartItem existingItem = null;
                                    for (int i = 0; i < cart.size(); i++) {
                                        CartItem item = cart.get(i);
                                        if (item.getItemId() == selectedItemId) {
                                            existingItem = item;
                                            break;
                                        }
                                    }

                                    if (existingItem != null) {
                                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                                        System.out.println("Updated quantity of " + itemName + " in the cart.");
                                    } else {
                                        CartItem newItem = new CartItem(selectedItemId, restaurantId, itemName,
                                                restaurantName, price, quantity);
                                        cart.add(newItem);
                                        System.out.println(
                                                "Item added to cart: " + itemName + " (Quantity: " + quantity + ")");
                                    }
                                } else {
                                    System.out.println("Restaurant not found for the selected item.");
                                }
                            }
                        }
                    } else {
                        System.out.println("Invalid Item ID.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToCart() {
        String categoryQuery = "SELECT * FROM Category";
        try {
            Connection c = JDBCUtils.getConnection();
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery(categoryQuery);

            System.out.println("\n=========================================");
            System.out.println("              CATEGORIES                 ");
            System.out.println("=========================================");
            System.out.printf("%-15s %-30s%n", "Category ID", "Category Name");
            System.out.println("-----------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-15d %-30s%n", resultSet.getInt("category_id"),
                        resultSet.getString("category_name"));
            }
            System.out.println("=========================================\n");

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Select Category ID: ");
        int categoryId = scanner.nextInt();

        String restaurantQuery = "SELECT restaurant_id, name FROM Restaurant WHERE category_id = ?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(restaurantQuery);
            pst.setInt(1, categoryId);
            ResultSet resultSet = pst.executeQuery();

            System.out.println("\n=========================================");
            System.out.println("               RESTAURANTS               ");
            System.out.println("=========================================");
            System.out.printf("%-15s %-30s%n", "Restaurant ID", "Restaurant Name");
            System.out.println("-----------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-15d %-30s%n", resultSet.getInt("restaurant_id"), resultSet.getString("name"));
            }
            System.out.println("=========================================\n");

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Select Restaurant ID: ");
        int restaurantId = scanner.nextInt();

        String menuItemQuery = "SELECT item_id, item_name, price, rating, quantity FROM MenuItem WHERE category_id = ? AND restaurant_id = ?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(menuItemQuery);
            pst.setInt(1, categoryId);
            pst.setInt(2, restaurantId);
            ResultSet resultSet = pst.executeQuery();

            System.out.println(
                    "\n=======================================================================================");
            System.out
                    .println("                                      MENU ITEMS                                       ");
            System.out
                    .println("=======================================================================================");
            System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", "Item ID", "Item Name", "Price", "Rating", "Quantity");
            System.out
                    .println("---------------------------------------------------------------------------------------");

            menuItemsList.clear();
            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                int rating = resultSet.getInt("rating");
                int quantity = resultSet.getInt("quantity");

                menuItemsList.add(new Menu(itemId, itemName, price, rating, quantity));

                System.out.printf("%-10d %-30s %-10.2f %-10d %-10d%n", itemId, itemName, price, rating, quantity);
            }
            System.out.println(
                    "=======================================================================================\n");

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        boolean continueFiltering = true;
        while (continueFiltering) {
            System.out.println("Would you like to filter these items? (yes/no)");
            String filterResponse = scanner.next();

            if (filterResponse.equalsIgnoreCase("yes")) {
                System.out.println("Filter by:");
                System.out.println("1. By price");
                System.out.println("2. By rating");
                int filterOption = scanner.nextInt();

                switch (filterOption) {
                    case 1:
                        System.out.println("1. High to Low");
                        System.out.println("2. Low to High");
                        int priceOption = scanner.nextInt();
                        if (priceOption == 1) {
                            menuItemsList.sortByPriceDesc();
                        } else if (priceOption == 2) {
                            menuItemsList.sortByPriceAsc();
                        }
                        break;

                    case 2:
                        System.out.println("1. High to Low");
                        System.out.println("2. Low to High");
                        int ratingOption = scanner.nextInt();
                        if (ratingOption == 1) {
                            menuItemsList.sortByRatingDesc();
                        } else if (ratingOption == 2) {
                            menuItemsList.sortByRatingAsc();
                        }
                        break;

                    default:
                        System.out.println("Invalid option.");
                        return;
                }

                System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", "Item ID", "Item Name", "Price", "Rating",
                        "Quantity");
                System.out.println("-------------------------------------------------------------");
                menuItemsList.print();

            } else {
                continueFiltering = false;
            }
        }

        System.out.println("Select Item ID: ");
        int itemId = scanner.nextInt();

        System.out.println("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String itemDetailsQuery = "SELECT item_name, price, quantity FROM MenuItem WHERE item_id = ?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(itemDetailsQuery);
            pst.setInt(1, itemId);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                defaultQuantity = resultSet.getInt("quantity");

                if (defaultQuantity == 0) {
                    ShareResources shareResources = new ShareResources(c, itemId);
                    Thread producer = new Producer(shareResources);
                    Thread consumer = new Consumer(shareResources);

                    producer.start();
                    consumer.start();

                    try {
                        producer.join();
                        consumer.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (quantity == 0 || quantity < 0 || quantity > defaultQuantity) {
                    System.out.println("Invalid quantity.");
                } else if (quantity <= defaultQuantity && quantity > 0) {

                    String restaurantNameQuery = "SELECT name FROM Restaurant WHERE restaurant_id = ?";
                    try {
                        PreparedStatement rpst = c.prepareStatement(restaurantNameQuery);
                        rpst.setInt(1, restaurantId);
                        ResultSet restaurantResultSet = rpst.executeQuery();
                        if (restaurantResultSet.next()) {
                            String restaurantName = restaurantResultSet.getString("name");

                            CartItem existingItem = null;
                            for (int i = 0; i < cart.size(); i++) {
                                CartItem item = cart.get(i);
                                if (item.getItemId() == itemId) {
                                    existingItem = item;
                                    break;
                                }
                            }

                            if (existingItem != null) {
                                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                                System.out.println("Updated quantity of " + itemName + " in the cart.");
                            } else {
                                cart.add(new CartItem(itemId, restaurantId, itemName, restaurantName, price, quantity));
                                System.out.println("Item added to cart.");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to update MenuItem quantity.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdateCart() {
        System.out.println("Current Cart Items:");
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        } else {
            cart.printCartItems();

            System.out.print("Enter the item ID of the cart item to update: ");
            int itemId = scanner.nextInt();
            scanner.nextLine();

            CartItem itemToUpdate = cart.find(itemId);
            if (itemToUpdate == null) {
                System.out.println("Item not found in the cart.");
                return;
            } else {
                int oldQuantity = itemToUpdate.getQuantity();
                System.out.println("Enter new quantity:");
                int newQuantity = scanner.nextInt();
                if (newQuantity < 0) {
                    System.out.println("Invalid quantity.");
                    return;
                } else if (newQuantity == 0) {
                    cart.remove(itemToUpdate);
                    System.out.println("Item removed from the cart.");
                } else {
                    try {
                        Connection c = JDBCUtils.getConnection();
                        if (newQuantity > oldQuantity) {
                            int update1 = newQuantity - oldQuantity;
                            String updateQuery = "UPDATE MenuItem SET quantity = quantity - ? WHERE item_id = ?";
                            PreparedStatement pst = c.prepareStatement(updateQuery);
                            pst.setInt(1, update1);
                            pst.setInt(2, itemId);
                            pst.executeUpdate();

                            itemToUpdate.setQuantity(newQuantity);
                            System.out.println("Cart updated successfully. Quantity increased.");
                        } else if (newQuantity < oldQuantity) {
                            int update2 = oldQuantity - newQuantity;
                            String updateQuery = "UPDATE MenuItem SET quantity = quantity + ? WHERE item_id = ?";
                            PreparedStatement pst = c.prepareStatement(updateQuery);
                            pst.setInt(1, update2);
                            pst.setInt(2, itemId);
                            pst.executeUpdate();

                            itemToUpdate.setQuantity(newQuantity);
                            System.out.println("Cart updated successfully. Quantity decreased.");
                        } else {
                            itemToUpdate.setQuantity(newQuantity);
                            System.out.println("Cart updated successfully.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Error updating the cart.");
                    }
                }
            }
        }
    }

    public static void removeFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("cart item : ");
        System.out.println("==========================================================");
        cart.printCartItems();
        System.out.println("==========================================================");
        System.out.println("Enter the Item ID you want to remove:");
        int itemId = scanner.nextInt();

        CartItem itemToRemove = cart.find(itemId);
        if (itemToRemove == null) {
            System.out.println("Item not found in the cart.");
            return;
        }

        cart.remove(itemToRemove);
        System.out.println("Item removed from cart.");
    }

    public static void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double totalAmount = 0;
        System.out.println("Order Summary:");
        cart.printCartItems();

        CartLinkedList.Node current = cart.head;
        while (current != null) {
            CartItem item = current.data;
            double itemTotal = item.getPrice() * item.getQuantity();
            totalAmount += itemTotal;
            System.out.println(
                    item.getItemName() + " - " + item.getQuantity() + " x " + item.getPrice() + " = " + itemTotal);
            current = current.next;
        }
        System.out.println("Total Amount: " + totalAmount);

        System.out.println("Do you want to place the order? (yes/no)");
        String choice = scanner.next();
        if (!choice.equalsIgnoreCase("yes")) {
            return;
        }

        System.out.println("Select Payment Method: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Google Pay");
        System.out.println("4. Cash on Delivery");
        int paymentMethodChoice = scanner.nextInt();
        String paymentMethod = "";
        String cardNumber = "";
        String UPI = "";

        switch (paymentMethodChoice) {
            case 1:
                paymentMethod = "Credit Card";
                cardNumber = validateCardNumber();
                break;
            case 2:
                paymentMethod = "Debit Card";
                cardNumber = validateCardNumber();
                break;
            case 3:
                paymentMethod = "Google Pay";
                UPI = validateUPIPIN();
                break;
            case 4:
                paymentMethod = "Cash on Delivery";
                break;
            default:
                System.out.println("Invalid payment method. Order not placed.");
                return;
        }

        String orderQuery = "INSERT INTO Orders (customer_id, item_name, quantity, total_price, order_date) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        String paymentQuery = "INSERT INTO Payment (payment_type, customer_id, item_name, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
        String updateMenuItemQuery = "UPDATE MenuItem SET quantity = quantity - ? WHERE item_id = ?";
        String salesQuery = "INSERT INTO Sales (customer_id, item_id, restaurant_id, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
        String reportQuery = "INSERT INTO Report (customer_id, restaurant_id, item_id, report_details) VALUES (?, ?, ?, ?)";

        Connection c = null;

        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);

            PreparedStatement orderStatement = c.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement paymentStatement = c.prepareStatement(paymentQuery);
            PreparedStatement updateMenuItemStatement = c.prepareStatement(updateMenuItemQuery);
            PreparedStatement salesStatement = c.prepareStatement(salesQuery);
            PreparedStatement reportStatement = c.prepareStatement(reportQuery);

            Set<Integer> restaurantIds = new HashSet<>();
            Set<Integer> itemIds = new HashSet<>();
            current = cart.head;
            while (current != null) {
                CartItem item = current.data;

                orderStatement.setInt(1, tempCustomerId);
                orderStatement.setString(2, item.getItemName());
                orderStatement.setInt(3, item.getQuantity());
                orderStatement.setDouble(4, item.getPrice() * item.getQuantity());
                orderStatement.addBatch();

                paymentStatement.setString(1, paymentMethod);
                paymentStatement.setInt(2, tempCustomerId);
                paymentStatement.setString(3, item.getItemName());
                paymentStatement.setInt(4, item.getQuantity());
                paymentStatement.setDouble(5, item.getPrice() * item.getQuantity());
                paymentStatement.addBatch();

                updateMenuItemStatement.setInt(1, item.getQuantity());
                updateMenuItemStatement.setInt(2, item.getItemId());
                updateMenuItemStatement.addBatch();

                salesStatement.setInt(1, tempCustomerId);
                salesStatement.setInt(2, item.getItemId());
                salesStatement.setInt(3, item.getRestaurantId());
                salesStatement.setInt(4, item.getQuantity());
                salesStatement.setDouble(5, item.getPrice() * item.getQuantity());
                salesStatement.addBatch();

                restaurantIds.add(item.getRestaurantId());
                itemIds.add(item.getItemId());

                current = current.next;
            }

            orderStatement.executeBatch();
            paymentStatement.executeBatch();
            updateMenuItemStatement.executeBatch();
            salesStatement.executeBatch();

            c.commit();

            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                System.out.println("Order placed and payment processed successfully.");
                System.out.println("Order ID: " + orderId);

                generateAndPrintBill(orderId, totalAmount, paymentMethod);

                System.out.println("Would you like to provide feedback? (yes/no)");
                String feedbackChoice = scanner.next();
                if (feedbackChoice.equalsIgnoreCase("yes")) {
                    System.out.println("Please rate your experience (1 to 5 stars): ");
                    int rating = scanner.nextInt();

                    for (int itemId : itemIds) {
                        int restaurantId = getRestaurantIdForItemId(itemId);

                        String feedbackSelectQuery = "SELECT rating FROM Feedback WHERE restaurant_id = ? AND item_id = ?";
                        String feedbackUpdateQuery = "UPDATE Feedback SET rating = ? WHERE restaurant_id = ? AND item_id = ?";
                        String feedbackInsertQuery = "INSERT INTO Feedback (customer_id, restaurant_id, item_id, rating) VALUES (?, ?, ?, ?)";

                        PreparedStatement feedbackSelectStmt = c.prepareStatement(feedbackSelectQuery);
                        feedbackSelectStmt.setInt(1, restaurantId);
                        feedbackSelectStmt.setInt(2, itemId);
                        ResultSet feedbackResultSet = feedbackSelectStmt.executeQuery();

                        if (feedbackResultSet.next()) {
                            double existingRating = feedbackResultSet.getDouble("rating");
                            double newRating = (existingRating + rating) / 2;

                            PreparedStatement feedbackUpdateStmt = c.prepareStatement(feedbackUpdateQuery);
                            feedbackUpdateStmt.setDouble(1, newRating);
                            feedbackUpdateStmt.setInt(2, restaurantId);
                            feedbackUpdateStmt.setInt(3, itemId);
                            feedbackUpdateStmt.executeUpdate();
                        } else {
                            PreparedStatement feedbackInsertStmt = c.prepareStatement(feedbackInsertQuery);
                            feedbackInsertStmt.setInt(1, tempCustomerId);
                            feedbackInsertStmt.setInt(2, restaurantId);
                            feedbackInsertStmt.setInt(3, itemId);
                            feedbackInsertStmt.setInt(4, rating);
                            feedbackInsertStmt.executeUpdate();
                        }

                        String menuItemSelectQuery = "SELECT rating FROM MenuItem WHERE item_id = ?";
                        String menuItemUpdateQuery = "UPDATE MenuItem SET rating = ? WHERE item_id = ?";

                        PreparedStatement menuItemSelectStmt = c.prepareStatement(menuItemSelectQuery);
                        menuItemSelectStmt.setInt(1, itemId);
                        ResultSet menuItemResultSet = menuItemSelectStmt.executeQuery();

                        if (menuItemResultSet.next()) {
                            double existingMenuItemRating = menuItemResultSet.getDouble("rating");
                            double newMenuItemRating = (existingMenuItemRating + rating) / 2;

                            PreparedStatement menuItemUpdateStmt = c.prepareStatement(menuItemUpdateQuery);
                            menuItemUpdateStmt.setDouble(1, newMenuItemRating);
                            menuItemUpdateStmt.setInt(2, itemId);
                            menuItemUpdateStmt.executeUpdate();
                        } else {
                            PreparedStatement menuItemUpdateStmt = c.prepareStatement(menuItemUpdateQuery);
                            menuItemUpdateStmt.setDouble(1, rating);
                            menuItemUpdateStmt.setInt(2, itemId);
                            menuItemUpdateStmt.executeUpdate();
                        }

                        System.out.println("Thank you for your feedback for item ID " + itemId + "!");
                    }

                    for (int restaurantId : restaurantIds) {
                        String restaurantSelectQuery = "SELECT rating FROM Restaurant WHERE restaurant_id = ?";
                        String restaurantUpdateQuery = "UPDATE Restaurant SET rating = ? WHERE restaurant_id = ?";

                        PreparedStatement restaurantSelectStmt = c.prepareStatement(restaurantSelectQuery);
                        restaurantSelectStmt.setInt(1, restaurantId);
                        ResultSet restaurantResultSet = restaurantSelectStmt.executeQuery();

                        if (restaurantResultSet.next()) {
                            double existingRestaurantRating = restaurantResultSet.getDouble("rating");
                            double newRestaurantRating = (existingRestaurantRating + rating) / 2;

                            PreparedStatement restaurantUpdateStmt = c.prepareStatement(restaurantUpdateQuery);
                            restaurantUpdateStmt.setDouble(1, newRestaurantRating);
                            restaurantUpdateStmt.setInt(2, restaurantId);
                            restaurantUpdateStmt.executeUpdate();
                        } else {
                            PreparedStatement restaurantUpdateStmt = c.prepareStatement(restaurantUpdateQuery);
                            restaurantUpdateStmt.setDouble(1, rating);
                            restaurantUpdateStmt.setInt(2, restaurantId);
                            restaurantUpdateStmt.executeUpdate();
                        }
                    }

                    if (rating <= 3) {
                        System.out.println("Would you like to provide a report for this order? (yes/no)");
                        String reportChoice = scanner.next();
                        if (reportChoice.equalsIgnoreCase("yes")) {
                            System.out.println("Please provide details for the report:");
                            String reportDetails = scanner.next();

                            for (int itemId : itemIds) {
                                for (int restaurantId : restaurantIds) {
                                    reportStatement.setInt(1, tempCustomerId);
                                    reportStatement.setInt(2, restaurantId);
                                    reportStatement.setInt(3, itemId);
                                    reportStatement.setString(4, reportDetails);
                                    reportStatement.executeUpdate();
                                }
                            }
                            System.out.println("Report submitted successfully.");
                        }
                    }
                }

            }
        } catch (SQLException e) {
            try {
                if (c != null) {
                    c.rollback();
                }
            } catch (Exception e1) {
                e.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.setAutoCommit(true);
                    c.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        cart.clear();

    }

    public static void generateAndPrintBill(int orderId, double totalAmount, String paymentMethod) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = sdf.format(new Date());

        System.out.println("\n======= BILL =======");
        System.out.println("Date & Time: " + currentDateTime);
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + tempCustomerId);
        System.out.println("---------------------------------------------------------------");

        System.out.printf("%-30s %-10s %-10s %-10s%n", "Item Name", "Quantity", "Price", "Total");
        System.out.println("---------------------------------------------------------------");

        CartLinkedList.Node current = cart.head;
        double billTotal = 0.0;
        while (current != null) {
            CartItem item = current.data;
            double itemTotal = item.getPrice() * item.getQuantity();
            billTotal += itemTotal;
            System.out.printf("%-30s %-10d $%-10.2f $%-10.2f%n",
                    item.getItemName(),
                    item.getQuantity(),
                    item.getPrice(),
                    itemTotal);
            current = current.next;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-30s $%-10.2f%n", "Subtotal", billTotal);
        System.out.printf("%-30s $%-10.2f%n", "Total Amount", totalAmount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("====================\n");
    }

    public static int getRestaurantIdForItemId(int itemId) {
        String query = "SELECT restaurant_id FROM MenuItem WHERE item_id = ?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement st = c.prepareStatement(query);
            st.setInt(1, itemId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("restaurant_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void cancelOrder() {
        System.out.println("Enter Order ID to Cancel: ");
        int orderId = scanner.nextInt();

        String fetchQuery = "SELECT item_name, quantity FROM Orders WHERE order_id = ?";
        Map<String, Integer> itemQuantities = new HashMap<>();

        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement fetchStatement = c.prepareStatement(fetchQuery);
            fetchStatement.setInt(1, orderId);
            ResultSet resultSet = fetchStatement.executeQuery();

            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                int quantity = resultSet.getInt("quantity");
                itemQuantities.put(itemName, quantity);
            }

            String deleteQuery = "DELETE FROM Orders WHERE order_id = ?";
            try {
                PreparedStatement deleteStatement = c.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, orderId);
                deleteStatement.executeUpdate();
                System.out.println("Order cancelled successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String updateQuery = "UPDATE MenuItem SET quantity = quantity + ? WHERE item_name = ?";
            try {
                PreparedStatement updateStatement = c.prepareStatement(updateQuery);
                for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
                    updateStatement.setInt(1, entry.getValue());
                    updateStatement.setString(2, entry.getKey());
                    updateStatement.executeUpdate();
                }
                System.out.println("Menu items updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkOrderHistory() {
        String query = "{CALL GetOrderHistory(?)}";
        try {
            Connection c = JDBCUtils.getConnection();
            CallableStatement cst = c.prepareCall(query);
            cst.setInt(1, tempCustomerId);
            ResultSet resultSet = cst.executeQuery();

            // Print table header
            System.out.println("\n======= Order History =======");
            System.out.printf("%-10s %-20s %-8s %-10s %-20s\n", "Order ID", "Item Name", "Quantity", "Total Price",
                    "Order Date");
            System.out.println("---------------------------------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-10d %-20s %-8d %-10.2f %-20s\n",
                        resultSet.getInt("order_id"),
                        resultSet.getString("item_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("total_price"),
                        resultSet.getTimestamp("order_date"));
            }
            System.out.println("===============================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showMostRatedRestaurant() {
        String query = "SELECT restaurant_id, name, address, phone_no, rating FROM Restaurant";
        Connection c = null;
        List<Restaurant> restaurantList = new ArrayList<>();

        try {
            c = JDBCUtils.getConnection();
            PreparedStatement st = c.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                int restaurantId = resultSet.getInt("restaurant_id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phoneNo = resultSet.getString("phone_no");
                int rating = resultSet.getInt("rating");

                Restaurant restaurant = new Restaurant(restaurantId, name, address, phoneNo, rating);
                restaurantList.add(restaurant);
            }

            Collections.sort(restaurantList, (r1, r2) -> Integer.compare(r2.getRating(), r1.getRating()));

            System.out.println("Top 10 Most Rated Restaurants (Sorted):");
            int count = 0;
            for (Restaurant restaurant : restaurantList) {
                if (count < 10) {
                    System.out.println(restaurant);
                    count++;
                } else {
                    break;
                }
            }

            System.out.println("\nDo you want to see the menu items of any restaurant? (yes/no)");
            String response = scanner.next();

            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Enter Restaurant ID: ");
                int selectedRestaurantId = scanner.nextInt();

                String menuItemQuery = "SELECT item_id, item_name, price, rating, quantity FROM MenuItem WHERE restaurant_id = ?";
                try {
                    PreparedStatement pst = c.prepareStatement(menuItemQuery);
                    pst.setInt(1, selectedRestaurantId);
                    ResultSet menuItemResultSet = pst.executeQuery();

                    System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", "Item ID", "Item Name", "Price", "Rating",
                            "Quantity");
                    System.out.println("-------------------------------------------------------------");

                    while (menuItemResultSet.next()) {
                        int itemId = menuItemResultSet.getInt("item_id");
                        String itemName = menuItemResultSet.getString("item_name");
                        double price = menuItemResultSet.getDouble("price");
                        int rating = menuItemResultSet.getInt("rating");
                        int quantity = menuItemResultSet.getInt("quantity");

                        System.out.printf("%-10d %-30s %-10.2f %-10d %-10d%n", itemId, itemName, price, rating,
                                quantity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("\nDo you want to add any menu item to the cart? Enter Item ID or 0 to skip: ");
                int selectedItemId = scanner.nextInt();

                if (selectedItemId != 0) {
                    System.out.println("Enter Quantity: ");
                    int quantity = scanner.nextInt();

                    String itemDetailsQuery = "SELECT item_name, price, quantity FROM MenuItem WHERE item_id = ?";
                    try {
                        PreparedStatement pst = c.prepareStatement(itemDetailsQuery);
                        pst.setInt(1, selectedItemId);
                        ResultSet itemResultSet = pst.executeQuery();

                        if (itemResultSet.next()) {
                            String itemName = itemResultSet.getString("item_name");
                            double price = itemResultSet.getDouble("price");
                            int defaultQuantity = itemResultSet.getInt("quantity");

                            if (quantity <= 0 || quantity > defaultQuantity) {
                                System.out.println("Invalid quantity.");
                            } else {
                                String restaurantNameQuery = "SELECT name FROM Restaurant WHERE restaurant_id = ?";
                                try {
                                    PreparedStatement rpst = c.prepareStatement(restaurantNameQuery);
                                    rpst.setInt(1, selectedRestaurantId);
                                    ResultSet restaurantResultSet = rpst.executeQuery();
                                    if (restaurantResultSet.next()) {
                                        String restaurantName = restaurantResultSet.getString("name");

                                        CartItem existingItem = null;
                                        for (int i = 0; i < cart.size(); i++) {
                                            CartItem item = cart.get(i);
                                            if (item.getItemId() == selectedItemId) {
                                                existingItem = item;
                                                break;
                                            }
                                        }

                                        if (existingItem != null) {
                                            existingItem.setQuantity(existingItem.getQuantity() + quantity);
                                            System.out.println("Updated quantity of " + itemName + " in the cart.");
                                        } else {
                                            CartItem newItem = new CartItem(selectedItemId, selectedRestaurantId,
                                                    itemName, restaurantName, price, quantity);
                                            cart.add(newItem);
                                            System.out.println("Item added to cart: " + itemName + " (Quantity: "
                                                    + quantity + ")");
                                        }
                                    } else {
                                        System.out.println("Restaurant not found for the selected item.");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("Invalid Item ID.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void showMostRatedMenuItems() {
        String query = "SELECT item_id, item_name, quantity, price, rating FROM MenuItem";
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        try {
            Connection c = JDBCUtils.getConnection();
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                int rating = resultSet.getInt("rating");

                MenuItem menuItem = new MenuItem(itemId, itemName, quantity, price, rating);
                menuItems.add(menuItem);
            }

            Collections.sort(menuItems, new Comparator<MenuItem>() {
                @Override
                public int compare(MenuItem o1, MenuItem o2) {
                    return Integer.compare(o2.getRating(), o1.getRating());
                }
            });

            System.out.println("Most Rated Menu Items:");
            System.out.printf("%-10s %-30s %-10s %-6s%n", "Item ID", "Item Name", "Price", "Rating");
            System.out.println("-----------------------------------------------------------");

            int count = 0;
            for (MenuItem item : menuItems) {
                if (count >= 50)
                    break;
                System.out.printf("%-10d %-30s %-10.2f %-6d%n", item.getItemId(), item.getItemName(), item.getPrice(),
                        item.getRating());
                count++;
            }

        } catch (Exception e) {
            System.err.println("SQL error: " + e.getMessage());
        }
    }

    public static String validateUPIPIN() {
        while (true) {
            System.out.println("Enter UPI pin (6 digits, numeric only): ");
            String pin = scanner.next();
            if (isValidUPIPIN(pin)) {
                return pin;
            } else {
                System.out.println(
                        "Invalid pin number. Card number must be between 13 and 19 digits long and contain only digits.");
            }
        }
    }

    public static boolean isValidUPIPIN(String upiPin) {
        if (upiPin.length() != 6) {
            return false;
        }

        for (int i = 0; i < upiPin.length(); i++) {
            char ch = upiPin.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }

    public static String validateCardNumber() {
        while (true) {
            System.out.println("Enter Card Number (13 to 19 digits, numeric only): ");
            String cardNumber = scanner.next();
            if (isValidCardNumber(cardNumber)) {
                return cardNumber;
            } else {
                System.out.println(
                        "Invalid card number. Card number must be between 13 and 19 digits long and contain only digits.");
            }
        }
    }

    public static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }

        for (int i = 0; i < cardNumber.length(); i++) {
            char ch = cardNumber.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }

    public static String validateName() {
        while (true) {
            System.out.println("Enter Name (Only alphabets): ");
            String name = scanner.next();
            if (isAlpha(name)) {
                return name;
            } else {
                System.out.println("Invalid name. Name should contain only alphabets.");
            }
        }
    }

    public static String validateEmail() {
        while (true) {
            System.out.println("Enter Email (Minimum length 15 and ends with '@gmail.com'): ");
            String email = scanner.next();
            if (email.length() >= 15 && email.endsWith("@gmail.com")) {
                return email;
            } else {
                System.out
                        .println("Invalid email. Email must be at least 15 characters long and end with '@gmail.com'.");
            }
        }
    }

    public static String validatePassword() {
        while (true) {
            System.out.println("Enter Password (Length must be 8 and contain at least 1 special character): ");
            String password = scanner.next();
            if (password.length() == 8 && containsSpecialCharacter(password)) {
                return password;
            } else {
                System.out.println(
                        "Invalid password. Password must be exactly 8 characters long and contain at least 1 special character.");
            }
        }
    }

    public static String validatePhone() {
        while (true) {
            System.out.println("Enter Phone (Length must be 10 and contain only digits): ");
            String phone = scanner.next();
            if (phone.length() == 10 && isNumeric(phone)) {
                return phone;
            } else {
                System.out.println("Invalid phone number. Phone number must be exactly 10 digits long.");
            }
        }
    }

    public static boolean isAlpha(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsSpecialCharacter(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }
}

class ShareResources {
    boolean updateCompleted = false;
    Connection c;
    int itemId;

    public ShareResources(Connection c, int itemId) {
        this.c = c;
        this.itemId = itemId;
    }

    synchronized void updateQuantity() {
        while (updateCompleted) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        try {
            String updateQuery = "UPDATE MenuItem SET quantity = ? WHERE item_id = ?";
            PreparedStatement pst = c.prepareStatement(updateQuery);
            pst.setInt(1, 100);
            pst.setInt(2, itemId);
            pst.executeUpdate();
            System.out.println("Updated quantity to 100 for item ID: " + itemId);
            Customer.defaultQuantity = 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateCompleted = true;
        notify();
    }

    synchronized void confirmUpdate() {
        while (!updateCompleted) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Update confirmed for item ID: " + itemId);
        updateCompleted = false;
        notify();
    }
}

class Producer extends Thread {
    ShareResources shareResources;

    public Producer(ShareResources shareResources) {
        this.shareResources = shareResources;
    }

    public void run() {
        shareResources.updateQuantity();
    }
}

class Consumer extends Thread {
    ShareResources shareResources;

    public Consumer(ShareResources shareResources) {
        this.shareResources = shareResources;
    }

    public void run() {
        shareResources.confirmUpdate();
    }
}
