package main;

import dao.OrderProcessor;
import entity.model.Product;
import entity.model.User;
import exception.OrderNotFoundException;
import exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderProcessor orderProcessor = new OrderProcessor();
        List<Product> productList = new ArrayList<>();
       // User user = null;
        while (true) {
            // Display the menu
            System.out.println("\n=== Order Management System ===");
            System.out.println("1. Create User");
            System.out.println("2. Create Product");
            System.out.println("3. Create Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. Get All Products");
            System.out.println("6. Get Orders by User");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1: // Create User
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (Admin/User): ");
                    String role = scanner.nextLine();

                    User user = new User(userId, username, password, role);
                    orderProcessor.createUser(user);
                    break;
                    
                    case 2: // Create Product
                    List<User> users = orderProcessor.getAllUsers(); // Get the list of users
                    if (users.isEmpty()) { // Check if there are no users
                        System.out.println("No users available. Please create a user first.");
                        break;
                    }
                    
                    System.out.println("Available Users:");
                    for (User u : users) {
                        System.out.println("User ID: " + u.getUserId() + ", Username: " + u.getUsername());
                    }
                    
                    System.out.print("Enter User ID (Admin) to create the product: ");
                    int productCreatorUserId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                
                    // Check if the user exists
                    /*if (!orderProcessor.isUserPresent(productCreatorUserId)) {
                        System.out.println("User not found. Please provide a valid Admin User ID.");
                        break;
                    }*/
                
                    // Proceed to create the product
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity in Stock: ");
                    int quantityInStock = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter Product Type (Electronics/Clothing): ");
                    String type = scanner.nextLine();
                
                    Product product = new Product(productId, productName, description, price, quantityInStock, type);
                    orderProcessor.createProduct(new User(productCreatorUserId), product); // Create the product with the Admin user
                    break;
                
                /*case 2: // Create Product
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity in Stock: ");
                    int quantityInStock = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter Product Type (Electronics/Clothing): ");
                    String type = scanner.nextLine();

                    Product product = new Product(productId, productName, description, price, quantityInStock, type);
                    productList.add(product);

                   
                    
                    orderProcessor.createProduct(user, product); // Assuming an admin user is creating the product
                    break;
                    */

                case 3: // Create Order
                    System.out.print("Enter User ID: ");
                    int orderUserId = scanner.nextInt();
                    System.out.print("Enter Product IDs (comma-separated): ");
                    scanner.nextLine(); // Consume newline left-over
                    String[] productIds = scanner.nextLine().split(",");
                    List<Product> orderedProducts = new ArrayList<>();

                    for (String pid : productIds) {
                        int id = Integer.parseInt(pid.trim());
                        for (Product p : productList) {
                            if (p.getProductId() == id) {
                                orderedProducts.add(p);
                            }
                        }
                    }

                    try {
                        orderProcessor.createOrder(new User(orderUserId), orderedProducts);
                        System.out.println("Order created successfully!");
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4: // Cancel Order
                    System.out.print("Enter User ID: ");
                    int cancelUserId = scanner.nextInt();
                    System.out.print("Enter Order ID: ");
                    int orderId = scanner.nextInt();

                    try {
                        orderProcessor.cancelOrder(cancelUserId, orderId);
                        System.out.println("Order canceled successfully!");
                    } catch (UserNotFoundException | OrderNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5: // Get All Products
                    System.out.println("Products List:");
                    List<Product> products = orderProcessor.getAllProducts();
                    for (Product p : products) {
                        System.out.println(p);
                    }
                    break;

                case 6: // Get Orders by User
                    System.out.print("Enter User ID: ");
                    int userIdForOrders = scanner.nextInt();
                    User userForOrders = new User(userIdForOrders);

                    List<Product> userOrders = orderProcessor.getOrderByUser(userForOrders);
                    System.out.println("Orders for User ID " + userIdForOrders + ":");
                    for (Product o : userOrders) {
                        System.out.println(o);
                    }
                    break;

                case 7: // Exit
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 7.");
                    break;
            }
        }
    }
}
