package com.admin;

import java.sql.*;
import java.util.*;

import com.utils.JDBCUtils;

public class Admin {
    static Scanner scanner = new Scanner(System.in);

    public static void adminMenu() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        if (authenticateAdmin(username, password)) {
            System.out.println("Welcome, Admin!");
            int choice;
            do {
                System.out.println("1. Add Restaurants");
                System.out.println("2. Remove Restaurants");
                System.out.println("3. Add Menu Items");
                System.out.println("4. Remove Menu Items");
                System.out.println("5. Change Price of Food");
                System.out.println("6. Generate Sales Report");
                System.out.println("7. Generate Most Ordered Items Report");
                System.out.println("8. Exit");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addRestaurant();// procedure
                        break;
                    case 2:
                        removeRestaurant();// trigger
                        break;
                    case 3:
                        addMenuItem();// procedure//trigger
                        break;
                    case 4:
                        removeMenuItem();// trigger
                        break;
                    case 5:
                        changePrice();// trigger
                        break;
                    case 6:
                        generateSalesReport();
                        break;
                    case 7:
                        generateMostOrderedItemsReport();
                        break;
                }
            } while (choice != 8);
        } else {
            System.out.println("Invalid Username or Password!");
        }
    }

    public static boolean authenticateAdmin(String username, String password) {
        String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet resultSet = pst.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addRestaurant() {
        try {
            Connection c = JDBCUtils.getConnection();

            String getCategoryQuery = "SELECT category_id, category_name FROM Category";
            PreparedStatement getCategoryStmt = c.prepareStatement(getCategoryQuery);
            ResultSet categoryResultSet = getCategoryStmt.executeQuery();

            System.out.printf("%-15s%-20s%n", "Category ID", "Category Name");
            System.out.println("-----------------------------------");

            while (categoryResultSet.next()) {
                int categoryId = categoryResultSet.getInt("category_id");
                String categoryName = categoryResultSet.getString("category_name");
                System.out.printf("%-15d%-20s%n", categoryId, categoryName);
            }

            System.out.println("Enter the Category ID for the Restaurant: ");
            int selectedCategoryId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter Restaurant Name: ");
            String name = scanner.nextLine();
            System.out.println("Enter Restaurant Address: ");
            String address = scanner.nextLine();
            System.out.println("Enter Restaurant Phone No: ");
            String phone = scanner.nextLine();

            String query = "{CALL AddRestaurant(?, ?, ?, ?, ?)}";
            CallableStatement cst = c.prepareCall(query);
            cst.setInt(1, selectedCategoryId);
            cst.setString(2, name);
            cst.setString(3, address);
            cst.setString(4, phone);

            cst.executeUpdate();

            int restaurantId = cst.getInt(5);
            System.out.println("Restaurant added with ID: " + restaurantId);

            System.out.println("Enter Menu Items (name and price). Type 'done' when finished.");
            while (true) {
                System.out.println("Enter Item Name: ");
                String itemName = scanner.nextLine();
                if (itemName.equalsIgnoreCase("done"))
                    break;
                System.out.println("Enter Item Price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();
                addMenuItem(restaurantId, selectedCategoryId, itemName, price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    public static void addMenuItem(int restaurantId, int categoryId, String itemName, double price) {
        String query = "{CALL AddMenuItem(?, ?, ?, ?)}";
        try {
            Connection c = JDBCUtils.getConnection();
            CallableStatement cst = c.prepareCall(query);
            cst.setInt(1, restaurantId);
            cst.setInt(2, categoryId);
            cst.setString(3, itemName);
            cst.setDouble(4, price);

            int response = cst.executeUpdate();
            if (response > 0) {
                System.out.println("Menu item added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeRestaurant() {
        System.out.println("Enter Restaurant ID to Remove: ");
        int restaurantId = scanner.nextInt();
        scanner.nextLine();

        String deleteSalesQuery = "DELETE FROM Sales WHERE item_id IN (SELECT item_id FROM MenuItem WHERE restaurant_id = ?)";
        String deleteMenuItemsQuery = "DELETE FROM MenuItem WHERE restaurant_id = ?";
        String deleteRestaurantQuery = "DELETE FROM Restaurant WHERE restaurant_id = ?";

        try {
            Connection c = JDBCUtils.getConnection();

            PreparedStatement pst1 = c.prepareStatement(deleteSalesQuery);
            pst1.setInt(1, restaurantId);
            pst1.executeUpdate();

            PreparedStatement pst2 = c.prepareStatement(deleteMenuItemsQuery);
            pst2.setInt(1, restaurantId);
            pst2.executeUpdate();

            PreparedStatement pst3 = c.prepareStatement(deleteRestaurantQuery);
            pst3.setInt(1, restaurantId);
            pst3.executeUpdate();

            System.out.println("Restaurant and its menu items removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMenuItem() {
        try {
            Connection c = JDBCUtils.getConnection();

            String getCategoryQuery = "SELECT category_id, category_name FROM Category";
            PreparedStatement getCategoryStmt = c.prepareStatement(getCategoryQuery);
            ResultSet categoryResultSet = getCategoryStmt.executeQuery();

            System.out.println("Available Categories:");
            while (categoryResultSet.next()) {
                int categoryId = categoryResultSet.getInt("category_id");
                String categoryName = categoryResultSet.getString("category_name");
                System.out.println(categoryId + ": " + categoryName);
            }

            System.out.println("Enter the Category ID for the Menu Item: ");
            int categoryId = scanner.nextInt();
            scanner.nextLine();

            String getRestaurantQuery = "SELECT restaurant_id, name FROM Restaurant";
            PreparedStatement getRestaurantStmt = c.prepareStatement(getRestaurantQuery);
            ResultSet restaurantResultSet = getRestaurantStmt.executeQuery();

            System.out.println("Available Restaurants:");
            while (restaurantResultSet.next()) {
                int restaurantId = restaurantResultSet.getInt("restaurant_id");
                String restaurantName = restaurantResultSet.getString("name");
                System.out.println(restaurantId + ": " + restaurantName);
            }

            System.out.println("Enter Restaurant ID: ");
            int restaurantId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter Item Name: ");
            String itemName = scanner.nextLine();

            System.out.println("Enter Item Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            addMenuItem(restaurantId, categoryId, itemName, price);
            System.out.println("Menu item added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeMenuItem() {
        System.out.println("Enter Item ID to Remove: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();

        Connection c = null;
        PreparedStatement deleteFeedbackPst = null;
        PreparedStatement deleteMenuItemPst = null;

        try {
            c = JDBCUtils.getConnection();

            String deleteFeedbackQuery = "DELETE FROM feedback WHERE item_id = ?";
            deleteFeedbackPst = c.prepareStatement(deleteFeedbackQuery);
            deleteFeedbackPst.setInt(1, itemId);
            deleteFeedbackPst.executeUpdate();

            String deleteMenuItemQuery = "DELETE FROM MenuItem WHERE item_id = ?";
            deleteMenuItemPst = c.prepareStatement(deleteMenuItemQuery);
            deleteMenuItemPst.setInt(1, itemId);
            deleteMenuItemPst.executeUpdate();

            System.out.println("Menu item removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changePrice() {
        try {
            Connection c = JDBCUtils.getConnection();

            String getMenuItemsQuery = "SELECT item_id, item_name, price FROM MenuItem";
            PreparedStatement getMenuItemsStmt = c.prepareStatement(getMenuItemsQuery);
            ResultSet menuItemsResultSet = getMenuItemsStmt.executeQuery();

            System.out.println("Available Menu Items:");
            while (menuItemsResultSet.next()) {
                int itemId = menuItemsResultSet.getInt("item_id");
                String itemName = menuItemsResultSet.getString("item_name");
                double price = menuItemsResultSet.getDouble("price");
                System.out.println("Item ID: " + itemId + ", Name: " + itemName + ", Price: " + price);
            }

            System.out.println("Enter Item ID to update: ");
            int itemId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter New Price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            String updatePriceQuery = "{CALL UpdateMenuItemPrice(?, ?)}";
            CallableStatement cst = c.prepareCall(updatePriceQuery);
            cst.setInt(1, itemId);
            cst.setDouble(2, newPrice);

            int rowsAffected = cst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Price updated successfully.");
            } else {
                System.out.println("Item ID not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateMostOrderedItemsReport() {
        String query = "SELECT item_id, SUM(quantity) AS total_quantity " +
                "FROM Sales " +
                "GROUP BY item_id " +
                "ORDER BY total_quantity DESC";

        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();

            System.out.println("\n======= Most Ordered Items Report =======");
            System.out.printf("%-10s %-20s\n", "Item ID", "Total Quantity Sold");
            System.out.println("------------------------------------------");

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                int totalQuantity = resultSet.getInt("total_quantity");

                System.out.printf("%-10d %-20d\n", itemId, totalQuantity);
            }
            System.out.println("==========================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateSalesReport() {
        String query = "SELECT restaurant_id, SUM(total_price) AS total_sales, SUM(quantity) AS total_quantity " +
                "FROM Sales " +
                "GROUP BY restaurant_id";

        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement pst = c.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();

            System.out.println("\n======= Sales Report =======");
            System.out.printf("%-15s %-15s %-20s\n", "Restaurant ID", "Total Sales", "Total Quantity Sold");
            System.out.println("---------------------------------------------------------");

            while (resultSet.next()) {
                int restaurantId = resultSet.getInt("restaurant_id");
                double totalSales = resultSet.getDouble("total_sales");
                int totalQuantity = resultSet.getInt("total_quantity");

                System.out.printf("%-15d %-15.2f %-20d\n",
                        restaurantId, totalSales, totalQuantity);
            }
            System.out.println("=============================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}