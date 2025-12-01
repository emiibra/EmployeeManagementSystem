package EmployeeSystem;

import java.time.LocalDate;

public class Employee {
    private int empid;
    private String fname;
    private String lname;
    private String email;
    private LocalDate hireDate;
    private double salary;
    private String ssn;

    public Employee(int empid, String fname, String lname, String email, LocalDate hireDate, double salary, String ssn){
        this.empid = empid;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.hireDate = hireDate;
        this.salary = salary;
        this.ssn = ssn;
    }

    public int getEmpid(){return empid;}
    public String getFname(){ return fname;}
    public String getLname(){ return lname; }
    public String getEmail(){ return email; }
    public LocalDate getHireDate(){ return hireDate;}
    public double getSalary(){ return salary; }
    public String getSsn(){ return ssn; }

    public void setFname(String fname) { this.fname = fname; }
    public void setLname(String lname) { this.lname = lname; }
    public void setEmail(String email) { this.email = email; }
    public void setHireDate(LocalDate hireDate){ this.hireDate = hireDate;}
    public void setSalary(double salary) { this.salary = salary; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    @Override
    public String toString() {
        return "Employee[" +
                "ID=" + empid +
                ", Name=" + fname + " " + lname +
                ", Email=" + email +
                ", HireDate=" + hireDate +
                ", Salary=" + salary +
                ", SSN=" + ssn +
                "]";
    }}

