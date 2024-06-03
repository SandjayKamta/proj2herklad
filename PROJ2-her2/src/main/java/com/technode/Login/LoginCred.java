package com.technode.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginCred {
    private String pass;
    private String email;

    // Default constructor needed for deserialization
    public LoginCred() {}

    public LoginCred(String pass, String email) {
        this.pass = hashPassword(pass);
        this.email = email;
    }

    // Getters and setters
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = hashPassword(pass);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private String hashPassword(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle the exception appropriately, maybe log it and return null or throw a custom exception
            return null;
        }
    }
}
