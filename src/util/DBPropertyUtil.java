/*package util;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    // Method to load properties from the specified properties file
    public static String getConnectionString(String propertiesFileName) throws IOException {
        Properties properties = new Properties();

        // Try reading the properties fil1e from the classpath
        try (InputStream inputStream = DBPropertyUtil.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream == null) {
                throw new IOException("Unable to find properties file: " + propertiesFileName);
            }
            // Load the properties from the input stream
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IOException("Failed to load database properties from file: " + propertiesFileName, e);
        }

        // Get the database connection string from the properties
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        // Return the full connection string (JDBC format)
        return "jdbc:mysql://" + url + "?user=" + username + "&password=" + password;
    }
}
*/
/*package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    // Method to load properties from the specified properties file
    public static String getConnectionString(String propertiesFileName) throws IOException {
        Properties properties = new Properties();

        // Try reading the properties file
        try (FileInputStream inputStream = new FileInputStream(propertiesFileName)) {
            // Load the properties from the file
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IOException("Failed to load database properties from file: " + propertiesFileName, e);
        }

        // Get the database connection string from the properties
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        // Return the full connection string (JDBC format)
        return "jdbc:mysql://" + url + "?user=" + username + "&password=" + password;
    }
}
*/
package util;

import java.io.*;
import java.util.*;

public class DBPropertyUtil {

	public static String getConnectionString(String fileName) {
        Properties pr = new Properties();
        try (FileInputStream f = new FileInputStream(fileName)) 
        {
            pr.load(f);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        String url = pr.getProperty("db.url");
        String username = pr.getProperty("db.username");
        String password = pr.getProperty("db.password");

        return url + "?user=" + username + "&password=" + password;
    }
}