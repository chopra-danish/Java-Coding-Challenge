package dao;

import entity.model.Product;
import entity.model.User;
import exception.OrderNotFoundException;
import exception.UserNotFoundException;
//import util.DBConnUtil;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessor implements IOrderManagementRepository {

    


public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String query = "SELECT * FROM users";

    try (Connection connection = DBUtil.getDBConn();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role")
            );
            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}








    @Override
    public void createOrder(User user, List<Product> products) throws UserNotFoundException {
        if (!isUserPresent(user.getUserId())) {
            throw new UserNotFoundException("User not found in the database.");
        }

        String query = "INSERT INTO orders (userId, productId) VALUES (?, ?)";

        try (Connection connection = DBUtil.getDBConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Product product : products) {
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setInt(2, product.getProductId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException {
        if (!isUserPresent(userId)) {
            throw new UserNotFoundException("User not found.");
        }

        String query = "DELETE FROM orders WHERE userId = ? AND order_id = ?";

        try (Connection connection = DBUtil.getDBConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, orderId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new OrderNotFoundException("Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createProduct(User user, Product product) {
        // Assuming an admin user is creating the product
        /*if (!"Admin".equalsIgnoreCase(user.getRole())) {
            throw new SecurityException("Only Admin users can create products.");
        }*/

        String query = "INSERT INTO products (productId, product_name, description, price, quantity_in_stock, type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getDBConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getQuantityInStock());
            preparedStatement.setString(6, product.getType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(User user) {
        String query = "INSERT INTO users (userId, username, password, role) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtil.getDBConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection connection = DBUtil.getDBConn(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("product_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getString("type")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getOrderByUser(User user) {
        List<Product> orderedProducts = new ArrayList<>();
        String query = "SELECT products.* FROM orders JOIN products ON orders.productId = products.productId WHERE orders.userId = ?";

        try (Connection connection = DBUtil.getDBConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("product_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getString("type")
                );
                orderedProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderedProducts;
    }

    // Helper method to check if a user exists in the database
    private boolean isUserPresent(int userId) {
        String query = "SELECT * FROM users WHERE userId = ?";
        try (Connection connection = DBUtil.getDBConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a user is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
