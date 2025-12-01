package EmployeeSystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> searchEmployee();
                case "2" -> insertEmployee();
                case "3" -> updateEmployee();
                case "4" -> updateSalaryByRange();
                case "5" -> deleteEmployee();
                case "6" -> generateReports();
                case "0" -> {
                    System.out.println("Exiting program.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n===== Employee Management System =====");
        System.out.println("1. Search Employee");
        System.out.println("2. Insert Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Increase Salary by Range");
        System.out.println("5. Delete Employee");
        System.out.println("6. Generate Reports");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // ------------------ SEARCH ------------------
    private static void searchEmployee() {
        System.out.print("Enter Employee Name, SSN, or ID: ");
        String input = scanner.nextLine().trim();
        List<Employee> results = EmployeeDAO.search(input);

        if (results.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee e : results) {
                System.out.println(e);
            }
        }
    }

    // ------------------ INSERT ------------------
    private static void insertEmployee() {
        try {
            System.out.print("First Name: ");
            String fname = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lname = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Hire Date (yyyy-MM-dd): ");
            LocalDate hireDate = LocalDate.parse(scanner.nextLine().trim(), formatter);

            System.out.print("Salary: ");
            double salary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("SSN (no dashes): ");
            String ssn = scanner.nextLine().trim();

            Employee e = new Employee(0, fname, lname, email, hireDate, salary, ssn);
            EmployeeDAO.insert(e);
            System.out.println("Employee inserted successfully!");

        } catch (Exception ex) {
            System.out.println("Error inserting employee: " + ex.getMessage());
        }
    }

    // ------------------ UPDATE ------------------
    private static void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            List<Employee> results = EmployeeDAO.search(String.valueOf(id));

            if (results.isEmpty()) {
                System.out.println("Employee not found.");
                return;
            }

            Employee e = results.get(0);

            System.out.print("First Name (" + e.getFname() + "): ");
            String fname = scanner.nextLine().trim();
            if (!fname.isEmpty()) e.setFname(fname);

            System.out.print("Last Name (" + e.getLname() + "): ");
            String lname = scanner.nextLine().trim();
            if (!lname.isEmpty()) e.setLname(lname);

            System.out.print("Email (" + e.getEmail() + "): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) e.setEmail(email);

            System.out.print("Hire Date (" + e.getHireDate() + "): ");
            String dateStr = scanner.nextLine().trim();
            if (!dateStr.isEmpty()) e.setHireDate(LocalDate.parse(dateStr, formatter));

            System.out.print("Salary (" + e.getSalary() + "): ");
            String salaryStr = scanner.nextLine().trim();
            if (!salaryStr.isEmpty()) e.setSalary(Double.parseDouble(salaryStr));

            System.out.print("SSN (" + e.getSsn() + "): ");
            String ssn = scanner.nextLine().trim();
            if (!ssn.isEmpty()) e.setSsn(ssn);

            EmployeeDAO.update(e);
            System.out.println("Employee updated successfully!");

        } catch (Exception ex) {
            System.out.println("Error updating employee: " + ex.getMessage());
        }
    }

    // ------------------ UPDATE SALARY ------------------
    private static void updateSalaryByRange() {
        try {
            System.out.print("Enter min salary: ");
            double min = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter max salary: ");
            double max = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter percentage increase (e.g., 3.2): ");
            double percent = Double.parseDouble(scanner.nextLine().trim());

            int updated = EmployeeDAO.updateSalaryByRange(percent, min, max);
            System.out.println("Updated " + updated + " employee(s).");

        } catch (Exception ex) {
            System.out.println("Error updating salary: " + ex.getMessage());
        }
    }

    // ------------------ DELETE ------------------
    private static void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            EmployeeDAO.delete(id);
            System.out.println("Employee deleted successfully!");
        } catch (Exception ex) {
            System.out.println("Error deleting employee: " + ex.getMessage());
        }
    }

    // ------------------ REPORTS ------------------
    private static void generateReports() {
        System.out.println("\n1. Payroll History by Employee ID");
        System.out.println("2. Total Pay by Job Title (monthly)");
        System.out.println("3. Total Pay by Division (monthly)");
        System.out.print("Choose report: ");
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter Employee ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    List<String> payroll = EmployeeDAO.getPayrollHistory(id);
                    payroll.forEach(System.out::println);
                }
                case "2" -> {
                    System.out.print("Enter Month (yyyy-MM): ");
                    LocalDate month = LocalDate.parse(scanner.nextLine().trim() + "-01", formatter);
                    List<String> byJob = EmployeeDAO.getPayByJobTitle(month);
                    byJob.forEach(System.out::println);
                }
                case "3" -> {
                    System.out.print("Enter Month (yyyy-MM): ");
                    LocalDate month = LocalDate.parse(scanner.nextLine().trim() + "-01", formatter);
                    List<String> byDiv = EmployeeDAO.getPayByDivision(month);
                    byDiv.forEach(System.out::println);
                }
                default -> System.out.println("Invalid report choice.");
            }
        } catch (Exception ex) {
            System.out.println("Error generating report: " + ex.getMessage());
        }
    }
}


