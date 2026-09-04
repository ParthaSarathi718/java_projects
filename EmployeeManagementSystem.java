import java.sql.*;
import java.util.*;

public class EmployeeManagementSystem {

    // Employee model (inner class)
    static class Employee {
        int id;
        String name;
        String department;
        double salary;

        Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }

    // Database connection method
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/employee_db";
        String user = "root";
        String password = "root"; // change this
        return DriverManager.getConnection(url, user, password);
    }

    // Add employee
    public static void addEmployee(Employee emp) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO employees(name, department, salary) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.name);
            stmt.setString(2, emp.department);
            stmt.setDouble(3, emp.salary);
            stmt.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View employees
    public static void viewEmployees() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("department") + " | " +
                                   rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete employee
    public static void deleteEmployee(int id) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM employees WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Employee deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.next();
                    System.out.print("Enter department: ");
                    String dept = sc.next();
                    System.out.print("Enter salary: ");
                    double salary = sc.nextDouble();
                    addEmployee(new Employee(0, name, dept, salary));
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    System.out.print("Enter employee ID to delete: ");
                    int id = sc.nextInt();
                    deleteEmployee(id);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

