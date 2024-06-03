package com.technode.Login;

import com.technode.Json.JsonInit;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class CredentialManager {

    private static Map<String, LoginCred> usersMap;

    static {
        try {
            String filePath = "src/main/java/com/technode/Login/password.json";
            usersMap = JsonInit.jsoninit(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            usersMap = null;
        }
    }

    public static LoginCred getCredentials(String user) {
        if (usersMap != null) {
            return usersMap.get(user);
        }
        return null;
    }


}