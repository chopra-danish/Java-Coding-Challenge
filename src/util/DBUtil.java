package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // Method to establish and return the database connection
    public static Connection getDBConn() throws SQLException {
        Connection conn = null;
        try {
            // Load database properties from the db.properties file
            String dbUrl = "jdbc:mysql://localhost:3306/order_management"; // Replace with your database URL
            String username = "root"; // Replace with your database username
            String password = "nikhil"; // Replace with your database password

            // Establish the connection
            conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Error while establishing database connection: " + e.getMessage());
            throw e; // Rethrow the exception for further handling if necessary
        }
        return conn;
    }
}
