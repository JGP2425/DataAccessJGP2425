import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "1234";
            conn = DriverManager.getConnection(url, user, password);

            st = conn.createStatement();
            String SQLAlter = "ALTER TABLE IF EXISTS public.subjects ADD COLUMN hours integer";
            st.executeUpdate(SQLAlter);

        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (st != null) {
                try {
                    st.close();
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