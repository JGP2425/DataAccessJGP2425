package com.jgp.OnlineMarket.OnlineMarket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}