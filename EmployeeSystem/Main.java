package EmployeeSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        // Test connection immediately
        Connection testConn = DBConnection.getConnection();
        if (testConn == null) return; // Stop if we can't connect
        
        while (true) {
            System.out.println("\n--- EMPLOYEE MANAGEMENT SYSTEM ---");
            System.out.println("1. Search for Employee (ID, Name, SSN)");
            System.out.println("2. Update Employee Data (Info)");
            System.out.println("3. Update Salary (Range Logic)");
            System.out.println("4. Generate Reports");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    doSearch(scan);
                    break;
                case 2:
                    // doUpdateInfo();
                    System.out.println("Feature coming next...");
                    break;
                case 3:
                    // doSalaryLogic();
                    System.out.println("Feature coming next...");
                    break;
                case 4:
                    // doReports();
                    System.out.println("Feature coming next...");
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void doSearch(Scanner scan) {
        System.out.println("\n--- SEARCH EMPLOYEE ---");
        System.out.println("Enter Name, SSN, or ID: ");
        String input = scan.nextLine();

        String sql = "SELECT * FROM employees WHERE Fname = ? OR Lname = ? OR SSN = ? OR empid = ?";

        try {
            // 1. Prepare the "Form"
            Connection conn = DBConnection.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // assume it's a string for Name/SSN first
            pstmt.setString(1, input); // First ? (Fname)
            pstmt.setString(2, input); // Second ? (Lname)
            pstmt.setString(3, input); // Third ? (SSN)
            
            // For the empid, we need to try to parse it as an int.
            try {
                int id = Integer.parseInt(input);
                pstmt.setInt(4, id);
            } catch (NumberFormatException e) {
                pstmt.setInt(4, 0); // If they didn't type a number, search for ID 0 (doesn't exist)
            }
        
            // 3. Run the search
            java.sql.ResultSet rs = pstmt.executeQuery();
        
            // 4. Print the results
            boolean found = false;
            System.out.println("\n--- RESULTS ---");
            while (rs.next()) { // Loop through every row found
                found = true;
                // Retrieve data by column name
                String first = rs.getString("Fname");
                String last = rs.getString("Lname");
                String ssn = rs.getString("SSN");
                double sal = rs.getDouble("Salary");
                int id = rs.getInt("empid");
                
                System.out.printf("ID: %d | Name: %s %s | SSN: %s | Salary: $%.2f\n", id, first, last, ssn, sal);
            }
            
            if (!found) {
                System.out.println("No employee found matching '" + input + "'");
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
