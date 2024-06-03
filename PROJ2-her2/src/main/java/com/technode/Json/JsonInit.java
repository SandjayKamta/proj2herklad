package com.technode.Json;

import com.technode.Login.LoginCred;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonInit {

    public static Map<String, LoginCred> jsoninit(String filePath) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, LoginCred>>() {}.getType();
            // Convert JSON file to Map<String, LoginCred>
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
