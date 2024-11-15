import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.security.sasl.SaslClient;
import javax.xml.transform.Result;
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
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        PreparedStatement st3 = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            st1 = conn.prepareStatement("INSERT INTO employee (empno, ename, job, deptno) VALUES( ?,?,?,?)");
            st2 = conn.prepareStatement("SELECT * FROM dept WHERE deptno = ?");
            st3 = conn.prepareStatement("INSERT INTO dept (deptno, dname, loc) VALUES (?,?,?)");
            Scanner scanner = new Scanner(System.in);

            boolean exit = false;
            while(!exit) {
                System.out.println("------------------------------");
                System.out.println("----- ADD A NEW EMPLOYEE -----");
                System.out.println("------------------------------");
                System.out.print("Employee Number: ");
                int enumber = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Employee Name: ");
                String ename = scanner.nextLine().toUpperCase();
                System.out.print("Employee job: ");
                String job = scanner.nextLine().toUpperCase();
                System.out.print("Dept Number: ");
                int deptno = scanner.nextInt();
                scanner.nextLine();

                st2.setInt(1, deptno);
                ResultSet rs = st2.executeQuery();
                if (rs.next())
                {
                    st1.setInt(1, enumber);
                    st1.setString(2, ename);
                    st1.setString(3, job);
                    st1.setInt(4, deptno);

                    st1.executeUpdate();
                    System.out.println("Employee added successfully!");
                }
                else {
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.println("---- WARNING: The dept informed don't exist in DATABASE, create it to continue ----");
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.print("Dept Name: ");
                    String deptname = scanner.nextLine().toUpperCase();
                    System.out.print("Location: ");
                    String location = scanner.nextLine().toUpperCase();

                    st3.setInt(1, deptno);
                    st3.setString(2,deptname);
                    st3.setString(3, location);

                    st3.executeUpdate();
                    System.out.println("Department added successfully!");

                    st1.setInt(1, enumber);
                    st1.setString(2, ename);
                    st1.setString(3, job);
                    st1.setInt(4, deptno);

                    st1.executeUpdate();
                    System.out.println("Employee added and assigned to the new department!");
                }

                System.out.print("Would you like to add a new employee? (Y/N): ");
                String exitString = scanner.nextLine();
                if (exitString.equalsIgnoreCase("N"))
                    exit = true;

                conn.commit();
            }

        }
        catch (SQLException e)
        {
            if (conn != null)
                conn.rollback();
            throw new SQLException(e);
        }
//        finally
//        {
//            if (conn != null) {
//                try {
//                    conn.close();
//                }
//                catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (st1 != null) {
//                try {
//                    st1.close();
//                }
//                catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (st2 != null) {
//                try {
//                    st2.close();
//                }
//                catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (st3 != null) {
//                try {
//                    st3.close();
//                }
//                catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

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