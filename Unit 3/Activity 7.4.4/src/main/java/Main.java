import javax.security.sasl.SaslClient;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String url =
            "jdbc:postgresql://localhost:5432/employees";
    static final String user = "postgres";
    static final String password = "1234";

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                printMainMenu();

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        showEmployeesByJob(conn);
                        break;
                    case 2:
                        showEmployeesByDept(conn);
                        break;
                    case 3:
                        showEmployeesByPatternName(conn);
                        break;
                    case 4:
                        exit = true;
                        printMessage("Goodbye!");
                        break;
                    default:
                        printMessage("Invalid option. Please select a valid option.");
                }
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e);
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private static void showEmployeesByJob(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write the job to filter the employees: ");
        String inputJob = scanner.nextLine().toUpperCase();
        CallableStatement statement = conn.prepareCall("SELECT * FROM employeebyjob('" + inputJob + "');");
        ResultSet rs = statement.executeQuery();
        printHeader();

        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + "\t"+
                    rs.getString(3) + "\t" + "\t" + "\t" + rs.getString(4));
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

    private static void showEmployeesByDept(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write the dept to filter the employees: ");
        String inputDept = scanner.nextLine().toUpperCase();
        CallableStatement statement = conn.prepareCall("SELECT * FROM employeebydept('" + inputDept + "');");
        ResultSet rs = statement.executeQuery();
        printHeader();

        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + "\t"+
                    rs.getString(3) + "\t" + "\t" + "\t" + rs.getString(4));
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

    private static void showEmployeesByPatternName(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write the pattern to filter the name of employees: ");
        String inputDept = scanner.nextLine().toUpperCase();
        CallableStatement statement = conn.prepareCall("SELECT * FROM employeebynameinpattern('" + inputDept + "');");
        ResultSet rs = statement.executeQuery();
        printHeader();

        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + "\t"+
                    rs.getString(3) + "\t" + "\t" + "\t" + rs.getString(4));
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

    private static void printMainMenu() {
        printMessage("\n---- STORED PROCEDURES ----");
        printMessage("1. Show all employees by specific job.");
        printMessage("2. Show all employees by specific department.");
        printMessage("3. Show all employees with a specific pattern in name.");
        printMessage("4. Exit");
        printMessage("\n----------------------------");
        printMessage("Choose an option: ");
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printHeader() {
        System.out.println("Emp. Number" + "\t" + "Name" + "\t" + "Job" + "\t" + "\t" + "Dept. Number");
        System.out.println("------------------------------------------------------------------------------------------");
    }

}