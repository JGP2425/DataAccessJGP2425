package org.jgp2425.unit.finalactivity_v1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utils {
    public static String MD5Converter (String password) {
        try {
            //Instance of MessageDigest
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());

            //Creation of the hashed password
            StringBuilder MD5pwd = new StringBuilder();
            for (byte b: hashBytes) {
                String hexString = Integer.toHexString(0xff & b);
                if (hexString.length() == 1)
                    MD5pwd.append('0');
                MD5pwd.append(hexString);
            }

            //Return of the value of the stringBuilder
            return String.valueOf(MD5pwd);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword (String plainPassword, String MD5Hash) {
        String hashedInput = MD5Converter(plainPassword);
        return hashedInput.equalsIgnoreCase(MD5Hash);
    }

    public static boolean isDatabaseAvailable() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/onlinemarket", "postgres", "postgres")) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
