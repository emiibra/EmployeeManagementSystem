package EmployeeSystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ---------------- SEARCH ----------------
    public static List<Employee> search(String input) {
        List<Employee> results = new ArrayList<>();
        String sql = """
                SELECT * FROM employees
                WHERE Fname = ? OR Lname = ? OR SSN = ? OR empid = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, input);
            stmt.setString(2, input);
            stmt.setString(3, input);

            int id = -1;
            try { id = Integer.parseInt(input); } catch (NumberFormatException ignored) {}
            stmt.setInt(4, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("empid"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getString("email"),
                        rs.getDate("HireDate").toLocalDate(),
                        rs.getDouble("Salary"),
                        rs.getString("SSN")
                );
                results.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


    // ---------------- INSERT ----------------
    public static void insert(Employee e) throws SQLException {
        // Validate required fields
        if (e.getFname() == null || e.getFname().isBlank() ||
            e.getLname() == null || e.getLname().isBlank() ||
            e.getSsn() == null || e.getSsn().isBlank()) {
            throw new SQLException("First name, last name, and SSN are required.");
        }
    
        // Check for duplicate SSN
        String checkSql = "SELECT COUNT(*) FROM employees WHERE SSN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
    
            checkStmt.setString(1, e.getSsn());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("SSN already exists.");
            }
        }
    
        // Insert employee into database
        String sql = """
                INSERT INTO employees (Fname, Lname, email, HireDate, Salary, SSN)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, e.getFname());
            stmt.setString(2, e.getLname());
            stmt.setString(3, e.getEmail());
            stmt.setDate(4, Date.valueOf(e.getHireDate()));
            stmt.setDouble(5, e.getSalary());
            stmt.setString(6, e.getSsn());
    
            stmt.executeUpdate();
        }
    }
    


    // ---------------- UPDATE EMPLOYEE --------------------------------
    public static void update(Employee e) throws SQLException {
        String sql = """
                UPDATE employees
                SET Fname=?, Lname=?, email=?, HireDate=?, Salary=?, SSN=?
                WHERE empid=?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getFname());
            stmt.setString(2, e.getLname());
            stmt.setString(3, e.getEmail());
            stmt.setDate(4, Date.valueOf(e.getHireDate()));
            stmt.setDouble(5, e.getSalary());
            stmt.setString(6, e.getSsn());
            stmt.setInt(7, e.getEmpid());

            stmt.executeUpdate();
        }
    }


    // ---------------- DELETE ----------------
    public static void delete(int empid) throws SQLException {
        String deleteDivision = "DELETE FROM employee_division WHERE empid=?";
        String deleteJobTitles = "DELETE FROM employee_job_titles WHERE empid=?";
        String deletePayroll = "DELETE FROM payroll WHERE empid=?";
        String deleteEmployee = "DELETE FROM employees WHERE empid=?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement(deleteDivision);
            PreparedStatement stmt2 = conn.prepareStatement(deleteJobTitles);
            PreparedStatement stmt3 = conn.prepareStatement(deletePayroll);
            PreparedStatement stmt4 = conn.prepareStatement(deleteEmployee)) {

            // Delete all dependent rows first
            stmt1.setInt(1, empid);
            stmt1.executeUpdate();

            stmt2.setInt(1, empid);
            stmt2.executeUpdate();

            stmt3.setInt(1, empid);
            stmt3.executeUpdate();

            // Finally, delete the employee
            stmt4.setInt(1, empid);
            stmt4.executeUpdate();
         }
    }


    // --------------- UPDATE SALARY BY RANGE ----------------
    public static int updateSalaryByRange(double percent, double min, double max) throws SQLException {
        String sql = """
                UPDATE employees
                SET Salary = Salary + (Salary * ? / 100)
                WHERE Salary >= ? AND Salary < ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, percent);
            stmt.setDouble(2, min);
            stmt.setDouble(3, max);

            return stmt.executeUpdate();
        }
    }


    //  REPORTS 

    // Payroll History
    public static List<String> getPayrollHistory(int empid) {
        List<String> out = new ArrayList<>();

        String sql = """
                SELECT payID, pay_date, earnings
                FROM payroll
                WHERE empid = ?
                ORDER BY pay_date
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                out.add("PayID: " + rs.getInt("payID") +
                        " | Date: " + rs.getDate("pay_date") +
                        " | Earnings: $" + rs.getDouble("earnings"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }


    // Total Pay by Job Title (monthly)
    public static List<String> getPayByJobTitle(LocalDate month) {
        List<String> out = new ArrayList<>();

        LocalDate start = month.withDayOfMonth(1);
        LocalDate end = month.withDayOfMonth(month.lengthOfMonth());

        String sql = """
                SELECT jt.job_title, SUM(p.earnings) AS total
                FROM payroll p
                JOIN employees e ON p.empid = e.empid
                JOIN employee_job_titles ejt ON e.empid = ejt.empid
                JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id
                WHERE p.pay_date BETWEEN ? AND ?
                GROUP BY jt.job_title
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                out.add("Job Title: " + rs.getString("job_title") +
                        " | Total Earnings: $" + rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }


    // Total Pay by Division (monthly)
    public static List<String> getPayByDivision(LocalDate month) {
        List<String> out = new ArrayList<>();

        LocalDate start = month.withDayOfMonth(1);
        LocalDate end = month.withDayOfMonth(month.lengthOfMonth());

        String sql = """
                SELECT d.Name AS division, SUM(p.earnings) AS total
                FROM payroll p
                JOIN employees e ON p.empid = e.empid
                JOIN employee_division ed ON e.empid = ed.empid
                JOIN division d ON ed.div_ID = d.ID
                WHERE p.pay_date BETWEEN ? AND ?
                GROUP BY d.Name
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                out.add("Division: " + rs.getString("division") +
                        " | Total Earnings: $" + rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }
}
