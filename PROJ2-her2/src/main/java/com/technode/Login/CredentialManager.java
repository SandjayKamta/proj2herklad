package com.technode.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.technode.Json.JsonInit;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;




public class CredentialManager {

    private static final String FILE_PATH = "/home/jvndalen/IdeaProjects/proj2herklad/PROJ2-her2/src/main/java/com/technode/Login/password.json";
    private static Map<String, LoginCred> usersMap;

    static {
        try {
            usersMap = JsonInit.jsoninit(FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            usersMap = null;
        }
    }

    public static LoginCred getCredentialsByUsername(String user) {
        if (usersMap != null) {
            return usersMap.get(user);
        }
        return null;
    }

    public static LoginCred getCredentialsById(String id) {
        if (usersMap != null) {
            for (LoginCred cred : usersMap.values()) {
                if (cred.getId().equals(id)) {
                    return cred;
                }
            }
        }
        return null;
    }

    public static String getUsernameById(String id) {
        if (usersMap != null) {
            for (Map.Entry<String, LoginCred> entry : usersMap.entrySet()) {
                if (entry.getValue().getId().equals(id)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public static Set<String> getAllUserKeys() {
        if (usersMap != null) {
            return usersMap.keySet();
        }
        return null;
    }

    public static void addUser(String username, String password, String email) {
        try {

            // Add user to the map
            usersMap.put(username, new LoginCred(password, email));

            // Save the updated map to the JSON file
            saveToJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserPassword(String id, String newPassword) {
        LoginCred userCred = getCredentialsById(id);
        if (userCred != null) {
            userCred.setPass(newPassword);
            try {
                saveToJson();
                System.out.println("Password updated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User not found!");
        }
    }

    public static void updateUserEmail(String id, String newEmail) {
        LoginCred userCred = getCredentialsById(id);
        if (userCred != null) {
            userCred.setEmail(newEmail);
            try {
                saveToJson();
                System.out.println("Email updated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User not found!");
        }
    }
    public static void updateUsername(String oldUsername, String newUsername) {
        LoginCred userCred = usersMap.remove(oldUsername);
        if (userCred != null) {
            usersMap.put(newUsername, userCred);
            try {
                saveToJson();
                System.out.println("Username updated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User not found!");
        }
    }

    private static void saveToJson() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(usersMap, fileWriter);
        }
    }


}
