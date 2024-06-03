package com.technode.Login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technode.Json.JsonInit;
import com.technode.UI.ARTGUI;
import com.technode.UI.LoginGUI;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

//import static com.technode.Login.CredentialManager.usersMap;


public class Login {


    public static void LogingFrame(String user, String pass) {

        try {
            LoginCred cred = CredentialManager.getCredentials(user);
            // Iterate through the map
            if (cred != null) {
            // Verify the hashed password
            if (cred.getPass().equals(hashPassword(pass))) {
                UserSession.setCurrentUser(user);
                LoginGUI.closeLoginGUI();
                ARTGUI gui = new ARTGUI();
                gui.show();
            } else {
                System.out.println("Invalid password");
            }
            } else {
                System.out.println("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String hashPassword(String pass) {
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
            return null;
        }
    }
}

