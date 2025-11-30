package EmployeeSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database Credentials
    private static final String URL = "jdbc:mysql://localhost:3306/Final_Project"; // Change 'Final_Project' if your DB name is different
    private static final String USER = "root"; 
    private static final String PASSWORD = "EDIT"; // <--- PUT YOUR DBeaver PASSWORD HERE

    // Method to get the connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // This loads the driver you downloaded
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DEBUG: Connected to database successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL Driver not found. Did you add the .jar file?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Could not connect to database.");
            e.printStackTrace();
        }
        return conn;
    }
}
