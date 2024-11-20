import java.util.*;

class MobileProduct {
    String name;
    String brand;
    String specifications;
    double price;
    int quantity;
    String model;

    public MobileProduct(String name, String brand, String model, double price, int antity, String specifications) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.specifications = specifications;
    }
}

class MobileShopManagementSystem {
    private static final int MAX_PRODUCTS = 100;
    private static MobileProduct[] products = new MobileProduct[MAX_PRODUCTS];
    private static int totalProducts = 0;

    private static boolean isLoggedIn = false;
    private static final String USERNAME = "ishan";
    private static final String PASSWORD = "2006";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        login();
        System.out.println(
                "===========================================================================================================================================");
        System.out.println();
        System.out.println("                                       WELCOME TO THE ELECTRONIC SHOP MANAGEMENT SYSTEM ");
        System.out.println();
        System.out.println(
                "===========================================================================================================================================");
        do {
            System.out.println("1. Add Product In Invenory");
            System.out.println("2. Remove Product In Inventory");
            System.out.println("3. Search by Brand name");
            System.out.println("4. Search by Model name");
            System.out.println("5. Search by Specifications");
            System.out.println("6. search By Price");
            System.out.println("7. Update Item Quantity");
            System.out.println("8. Display Inventory");
            System.out.println("9. bill");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    searchByBrand();
                    break;
                case 4:
                    searchByModel();
                    break;
                case 5:
                    searchBySpecifications();
                    break;
                case 6:
                    searchByPrice();
                    break;
                case 7:
                    updateItemQuantity();
                    break;
                case 8:
                    displayInventory();
                    break;
                case 9:
                    generateBill();
                    break;
                case 10:
                    System.out.println("==== Thank You , Visite Again ====");
                    break;
                default:
                    System.out.println("Invalid choice. Please Enter No between 1 to 10.");
            }

        } while (choice != 10);
    }

    private static void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        System.out.print("Enter name: ");
        String name = scanner.next();

        System.out.print("Enter brand: ");
        String brand = scanner.next();

        scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        // sc.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Enter specifications: ");
        String specs = scanner.nextLine();

        products[totalProducts] = new MobileProduct(name, brand, model, price, quantity,
                specs);
        totalProducts++;

        System.out.println("Product added successfully!");
        System.out.println();
        System.out.println();
    }

    private static void displayInventory() {
        // Create a copy of the products array for sorting
        MobileProduct[] sortedProducts = Arrays.copyOf(products, totalProducts);

        // Sort the products based on brand names
        Arrays.sort(sortedProducts, (product1, product2) -> product1.brand.compareTo(
                product2.brand));

        System.out.println("Inventory:");
        System.out.printf("%-15s%-20s%-15s%-10s%-20s\n", "Brand", "Model", "Price",
                "Quantity", "Specifications");

        for (MobileProduct product : sortedProducts) {
            System.out.printf("%-15s%-20s%-15.2f%-10d%-20s\n", product.brand, product.model, product.price,
                    product.quantity, product.specifications);
        }

        System.out.println();
        System.out.println();
    }

    private static void searchByBrand() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name to search: ");
        String searchname = scanner.next();

        scanner.nextLine();

        System.out.print("Enter brand to search: ");
        String searchBrand = scanner.nextLine();

        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            if (products[i].brand.equalsIgnoreCase(searchBrand) && products[i].name.equalsIgnoreCase(searchname)) {
                System.out.println();
                System.out.printf("name: %s, Brand: %s, Model: %s, Price: %.2f, Quantity: %d, Specifications: %s\n",
                        products[i].name, products[i].brand,
                        products[i].model, products[i].price, products[i].quantity, products[i].specifications);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found for the given brand.");
        }

        System.out.println();
        System.out.println();
    }

    private static void searchByPrice() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter minimum price to search: ");
        double minPrice = scanner.nextDouble();

        System.out.print("Enter maximum price to search: ");
        double maxPrice = scanner.nextDouble();

        scanner.nextLine();

        System.out.print("Enter product name to filter (or press Enter to skip): ");
        String filtername = scanner.next().trim();

        scanner.nextLine();

        System.out.print("Enter brand name to filter (or press Enter to skip): ");
        String filterBrand = scanner.nextLine().trim();

        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            // Check if the product's price is within the specified range and matches the
            // brand filter
            if (products[i].price >= minPrice && products[i].price <= maxPrice
                    && (filterBrand.isEmpty() || products[i].brand.equals(filterBrand))
                    && (filtername.isEmpty() || products[i].name.equals(filtername))) {
                System.out.println("============For your Range Below item Can U find===========");
                System.out.printf("name: %s, Brand: %s, Model: %s, Price: %.2f, Quantity: %d, Specifications: %s\n",
                        products[i].name, products[i].brand, products[i].model, products[i].price, products[i].quantity,
                        products[i].specifications);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products found within the given price range and brand.");
        }

        System.out.println();
        System.out.println();
    }

    private static void generateBill() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name for billing: ");
        String billingname = scanner.next();

        System.out.print("Enter brand for billing: ");
        String billingBrand = scanner.next();

        scanner.nextLine();

        System.out.print("Enter model for billing: ");
        String billingModel = scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            if (products[i].brand.equalsIgnoreCase(billingBrand) && products[i].model.equalsIgnoreCase(billingModel)
                    && products[i].name.equalsIgnoreCase(
                            billingname)) {
                System.out.print("Enter quantity to purchase: ");
                int quantityToPurchase = scanner.nextInt();

                if (quantityToPurchase > 0 && quantityToPurchase <= products[i].quantity) {
                    double totalCost = products[i].price * quantityToPurchase;

                    // Print the bill header
                    System.out.println(
                            "\n========== Your Bill ==========================================================================================");
                    System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Brand", "Model",
                            "Price", "Quantity", "Total Cost");
                    System.out.println(
                            "==================================================================");

                    // Print the purchased item details
                    System.out.printf("%-20s%-20s%-20.2f%-20d%-20.2f\n", products[i].brand, products[i].model,
                            products[i].price, quantityToPurchase, totalCost);

                    // Update inventory after purchase
                    products[i].quantity -= quantityToPurchase;

                    // Print the total cost and closing remarks
                    System.out.println(
                            "==================================================================");
                    System.out.printf("%-80s%.2f\n", "Total Cost:", totalCost);
                    System.out.println(
                            "==================Thank You for Your Purchase! ====================================================================");

                    found = true;
                } else {
                    System.out.println("Invalid quantity. Please enter a valid quantity.");
                }
            }
        }

        if (!found) {
            System.out.println("Product not found for billing.");
        }

        System.out.println();
        System.out.println();
    }

    private static void searchBySpecifications() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name to search: ");
        String searchname = scanner.next();

        scanner.nextLine();

        System.out.print("Enter specifications to search: ");
        String searchSpecs = scanner.nextLine();

        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            if (products[i].specifications.equalsIgnoreCase(searchSpecs)
                    && products[i].name.equalsIgnoreCase(searchname)) {
                System.out.printf("name: %s, Brand: %s, Model: %s, Price: %.2f, Quantity: %d\n", products[i].name,
                        products[i].brand, products[i].specifications, products[i].price, products[i].quantity);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found for the given specifications.");
        }

        System.out.println();
        System.out.println();
    }

    private static void updateItemQuantity() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name to update quantity: ");
        String updatename = scanner.next();

        scanner.nextLine();

        System.out.print("Enter brand to update quantity: ");
        String updateBrand = scanner.next();

        scanner.nextLine();

        System.out.print("Enter model to update quantity: ");
        String updateModel = scanner.nextLine();

        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            if (products[i].brand.equals(updateBrand) && products[i].model.equals(updateModel)
                    && products[i].name.equals(updatename)) {
                System.out.print("Enter new quantity: ");
                int newQuantity = scanner.nextInt();

                // Update the quantity
                products[i].quantity += newQuantity;

                System.out.println("Quantity updated successfully.");
                System.out.println("Updated Quantity for " + updateBrand + " " + updateModel
                        + ": " + products[i].quantity);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Product not found for quantity update.");
        }

        System.out.println();
        System.out.println();
    }

    private static void removeItem() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name to remove item: ");
        String removename = scanner.next();

        scanner.nextLine();

        System.out.print("Enter brand to remove item: ");
        String removeBrand = scanner.next();

        scanner.nextLine();

        System.out.print("Enter model to remove item: ");
        String removeModel = scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            if (products[i].brand.equals(removeBrand) && products[i].model.equals(removeModel)
                    && products[i].name.equals(removename)) {
                // Shift remaining elements to fill the gap
                for (int j = i; j < totalProducts - 1; j++) {
                    products[j] = products[j + 1];
                }
                totalProducts--;

                System.out.println("Item removed successfully for " + removeBrand + " " +
                        removeModel);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Product not found for removal.");
        }

        System.out.println();
        System.out.println();
    }

    private static void searchByModel() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name to search: ");
        String searchname = scanner.next();

        scanner.nextLine();

        System.out.print("Enter model to search: ");
        String searchModel = scanner.nextLine();

        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < totalProducts; i++) {
            if (products[i].model.equals(searchModel) && products[i].name.equals(searchname)) {
                System.out.printf("name: %s, Brand: %s, Model: %s, Price: %.2f, Quantity: %d, Specifications: %s\n",
                        products[i].name, products[i].brand, products[i].model, products[i].price, products[i].quantity,
                        products[i].specifications);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found for the given model.");
        }

        System.out.println();
        System.out.println();
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;

        do {
            System.out.println();
            System.out.println();
            System.out.print("Enter username: ");
            String username = scanner.next();

            System.out.print("Enter password: ");
            String password = scanner.next();

            if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                System.out.println("Login successful!");
                isLoggedIn = true;
                break;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                attempts++;
            }
        } while (attempts < 3);

        if (!isLoggedIn) {
            System.out.println("Too many login attempts. Exiting the program.");
            System.exit(0);
        }
    }
}