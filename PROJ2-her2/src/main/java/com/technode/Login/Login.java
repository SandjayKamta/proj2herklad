package com.technode.Login;
import com.technode.UI.ARTGUI;
import com.technode.UI.LoginGUI;



public class Login {


    public static void LogingFrame(String user, String pass) {

        try {
            LoginCred cred = CredentialManager.getCredentialsByUsername(user);
            // Iterate through the map
            if (cred != null) {
            // Verify the hashed password
            if (cred.getPass().equals(hashPassword.hashPassword(pass))) {
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



}

