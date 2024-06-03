package com.technode.Login;

public class UserSession {

    private static String currentUser;
    private static LoginCred currentUserCredentials;

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String user) {
        currentUser = user;
        currentUserCredentials = CredentialManager.getCredentials(user);
    }

    public static LoginCred getCurrentUserCredentials() {
        return currentUserCredentials;
    }

    public static String getCurrentUserEmail() {
        return (currentUserCredentials != null) ? currentUserCredentials.getEmail() : null;
    }

    public static void clearSession() {
        currentUser = null;
        currentUserCredentials = null;
    }
}
