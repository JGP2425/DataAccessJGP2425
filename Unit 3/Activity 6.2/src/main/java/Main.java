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

            String dropTableIfExists = "DROP TABLE IF EXISTS courses";
            ps = conn.prepareStatement(dropTableIfExists);
            ps.executeUpdate();

            String preparedStatement = "CREATE TABLE public.courses (code serial NOT NULL, name character varying(90) NOT NULL, PRIMARY KEY (code)); ALTER TABLE IF EXISTS public.courses OWNER to postgres;";
            ps = conn.prepareStatement(preparedStatement);
            ps.executeUpdate();

            String insterStatement = "INSERT INTO courses (name) VALUES ('Multiplatform app development'), ('Web development')";
            ps = conn.prepareStatement(insterStatement);
            ps.executeUpdate();
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