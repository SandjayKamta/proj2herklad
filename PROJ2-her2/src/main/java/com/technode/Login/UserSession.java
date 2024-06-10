package com.technode.Login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserSession {

    private static String currentUserId;
    private static LoginCred currentUserCredentials;
    private static final String SESSION_PATH = "/home/jvndalen/IdeaProjects/proj2herklad/PROJ2-her2/src/main/java/com/technode/Json/";


    public static String getCurrentUser() {
        return currentUserId;
    }

    public static void setCurrentUser(String username) {
        LoginCred cred = CredentialManager.getCredentialsByUsername(username);
        if (cred != null) {
            currentUserId = cred.getId();
            currentUserCredentials = cred;
        }
    }

    public static LoginCred getCurrentUserCredentials() {
        return currentUserCredentials;
    }

    public static String getCurrentUserEmail() {
        return (currentUserCredentials != null) ? currentUserCredentials.getEmail() : null;
    }

    public static void changePassword(String newPassword) {
        if (currentUserCredentials != null) {
            CredentialManager.updateUserPassword(currentUserId, newPassword);
            currentUserCredentials = CredentialManager.getCredentialsById(currentUserId);
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public static void changeEmail(String newEmail) {
        if (currentUserCredentials != null) {
            CredentialManager.updateUserEmail(currentUserId, newEmail);
            currentUserCredentials = CredentialManager.getCredentialsById(currentUserId);
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public static void clearSession() {
        currentUserId = null;
        currentUserCredentials = null;
    }

    public static void saveSessionData(UserSessionData sessionData) {
        if (currentUserId == null) return;
        try (FileWriter writer = new FileWriter(SESSION_PATH + "session_" + currentUserId + ".json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(sessionData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserSessionData loadSessionData() {
        if (currentUserId == null) return createDefaultSessionData();
        try (FileReader reader = new FileReader(SESSION_PATH + "session_" + currentUserId + ".json")) {
            Gson gson = new Gson();
            UserSessionData sessionData = gson.fromJson(reader, UserSessionData.class);
            if (sessionData == null || sessionData.getTabs() == null) {
                sessionData = createDefaultSessionData();
                saveSessionData(sessionData);
            }
            return sessionData;
        } catch (IOException e) {
            // Return a default session if the file is not found or there is an error
            UserSessionData sessionData = createDefaultSessionData();
            saveSessionData(sessionData);
            return sessionData;
        }
    }

    private static UserSessionData createDefaultSessionData() {
        UserSessionData sessionData = new UserSessionData();
        UserSessionData.TabData defaultTab = new UserSessionData.TabData("Tab", new ArrayList<>());
        sessionData.setTabs(new ArrayList<>(List.of(defaultTab)));
        return sessionData;
    }
}
