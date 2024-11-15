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
            String SQLAlter = "ALTER TABLE IF EXISTS public.subjects ADD COLUMN hours integer";
            statement.executeUpdate(SQLAlter);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}