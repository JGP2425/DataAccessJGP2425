import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args)  {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "1234";
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String SQLInsert = "INSERT INTO subjects (name, year) VALUES ('MARKUP LANGUAGES', 1)";
            int rowCount = statement.executeUpdate(SQLInsert);

            if (rowCount != 0) {
                String SQLSelect = "SELECT * FROM subjects ORDER BY code DESC LIMIT 1;";
                ResultSet rs = statement.executeQuery(SQLSelect);

                System.out.println("Rows affected: " + rowCount);
                System.out.println("----------------------------------------");
                System.out.println("Code" + "\t" + "Name" + "\t" + "Year");
                System.out.println("----------------------------------------");

                while (rs.next()) {
                    System.out.println(rs.getInt(1) + "\t " +
                            rs.getString(2) + "\t " +
                            rs.getInt(3));
                }
                rs.close();
                con.close();
            }
            else {
                System.out.println("Any row affected");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}