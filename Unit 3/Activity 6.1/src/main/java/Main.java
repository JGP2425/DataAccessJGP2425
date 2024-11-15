import org.postgresql.jdbc2.ArrayAssistant;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "1234";

            conn = DriverManager.getConnection(url, user, password);
            String preparedStatement = "INSERT INTO subjects (name, year, hours) VALUES ( ?, ?, ?)";
            ps = conn.prepareStatement(preparedStatement);

            ArrayList<String> subjectNamesAdded = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("---- NEW SUBJECT ----");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Year: ");
                int year = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Hours: ");
                int hours = scanner.nextInt();
                scanner.nextLine();

                ps.setString(1, name);
                ps.setInt(2, year);
                ps.setInt(3, hours);
                ps.executeUpdate();

                subjectNamesAdded.add(name);

                System.out.print("Do you want to continue adding more subjects (Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("N")) {
                    break;
                }
            }

            System.out.println("---------------------");
            System.out.println("Subjects added: " + subjectNamesAdded.size());
            System.out.println("---------------------");
            for (int i = 0; i < subjectNamesAdded.size(); i++) {
                System.out.println(i + 1 + ". " + subjectNamesAdded.get(i));
            }
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
}