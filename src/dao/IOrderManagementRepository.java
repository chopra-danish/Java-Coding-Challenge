package dao;

import entity.model.Product;
import entity.model.User;
import exception.OrderNotFoundException;
import exception.UserNotFoundException;

import java.util.List;

public interface IOrderManagementRepository {
    void createOrder(User user, List<Product> products) throws UserNotFoundException;
    void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException;
    void createProduct(User user, Product product);
    void createUser(User user);
    List<Product> getAllProducts();
    List<Product> getOrderByUser(User user);
}
