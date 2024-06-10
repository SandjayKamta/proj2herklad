package com.technode.UI;

import com.technode.Login.CredentialManager;
import com.technode.Settings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

import static com.technode.Login.CredentialManager.addUser;
import static com.technode.Login.CredentialManager.getCredentialsByUsername;
import static com.technode.Login.CredentialManager.getCredentialsById;

public class addUserGUI {
    public void show() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.technode.messages", Settings.currentLocale);

        Stage add = createStage();
        Label usernameLabel = new Label(resourceBundle.getString("usernameLabel") + ":");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label(resourceBundle.getString("passwordLabel") + ":");
        PasswordField passwordField = new PasswordField();

        Label emailLabel = new Label(resourceBundle.getString("email") + ":");
        TextField emailField = new TextField();

        Button addButton = new Button(resourceBundle.getString("registerButton"));
        addButton.setOnAction(e ->{
            String User = usernameField.getText();
            String Pass = passwordField.getText();
            String Email = emailField.getText();

            addUserMethod(User, Pass, Email);
            add.close();
        });

        // Create a grid pane and set its properties
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        // Add the labels and text fields to the grid pane
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(addButton, 3, 3);

        // Create the scene with the grid pane
        Scene scene = new Scene(grid, 800, 600);


        add.setScene(scene);
        add.show();
    }
    private Stage createStage() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.technode.messages", Settings.currentLocale);

        Stage stage = new Stage();
        stage.setTitle(resourceBundle.getString("registerButton"));
        return stage;
    }
    public static void addUserMethod(String User, String Pass, String Email) {
        CredentialManager.addUser(User, Pass, Email);
    }
}
