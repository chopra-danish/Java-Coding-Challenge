/*package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    // Method to establish and return the database connection
    public static Connection getDBConn() throws SQLException {
        Connection conn = null;
        try {
            // Load database properties from file
            Properties properties = new Properties();
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));

            // Retrieve properties (update with your database details)
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Establish the connection
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established successfully.");
        } catch (Exception e) {
            System.out.println("Error while establishing database connection: " + e.getMessage());
            throw new SQLException("Failed to establish database connection.");
        }
        return conn;
    }
}
*/
package util;

import java.sql.*;

public class DBConnUtil {
	private static Connection conn;
    public static Connection getConnection(String filename) {
        if (conn == null) 
        {
            try 
            {
                String cs = DBPropertyUtil.getConnectionString(filename);
                conn = DriverManager.getConnection(cs);
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        return conn;
    }
}