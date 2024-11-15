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
            int courseCode = 1;

            conn = DriverManager.getConnection(url, user, password);

            String retrieveCourseCode = "SELECT code FROM courses ORDER BY code ASC LIMIT 1";
            ps = conn.prepareStatement(retrieveCourseCode);
            rs = ps.executeQuery();
            if (rs.next())
               courseCode =  rs.getInt(1);

            String alterColumnSubject = "ALTER TABLE IF EXISTS public.subjects ADD COLUMN course integer DEFAULT " + courseCode;
            ps = conn.prepareStatement(alterColumnSubject);
            ps.executeUpdate();

            String createForeignKey = "ALTER TABLE IF EXISTS public.subjects ADD CONSTRAINT \"FK_COURSE\" FOREIGN KEY (course)REFERENCES public.courses (code) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION NOT VALID";
            ps = conn.prepareStatement(createForeignKey);
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