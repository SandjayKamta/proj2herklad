package com.technode.UI;
import com.technode.Login.Login;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginGUI extends Application {
    private Locale currentLocale = Locale.of("nl");

    private static Stage loginStage; // Static variable to hold the stage

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load resource bundle for the current locale
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.technode.messages", currentLocale);

        loginStage = primaryStage; // Assign the stage to the static variable
        primaryStage.setTitle("Login Screen");

        // Create the grid pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);  // Center the grid in the scene

        // Create the language switcher
        ChoiceBox<String> languageChoiceBox = new ChoiceBox<>();
        languageChoiceBox.getItems().addAll("Nederlands", "English");
        languageChoiceBox.setValue(currentLocale.getLanguage().equals("nl") ? "Nederlands" : "English"); // Set the default display value

        // Event handler for language selection
        languageChoiceBox.setOnAction(e -> {
            String selectedDisplayLanguage = languageChoiceBox.getValue();
            if ("English".equals(selectedDisplayLanguage)) {
                currentLocale = Locale.of("en");
            } else if ("Nederlands".equals(selectedDisplayLanguage)) {
                currentLocale = Locale.of("nl");
            }
            try {
                primaryStage.hide();
                start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        grid.getChildren().add(languageChoiceBox);

        // Add username label and text field
        Label userNameLabel = new Label(resourceBundle.getString("usernameLabel"));
        grid.add(userNameLabel, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // Add password label and text field
        Label pwLabel = new Label(resourceBundle.getString("passwordLabel"));
        grid.add(pwLabel, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Create an HBox for the buttons and center them
        HBox hbButtons = new HBox(10);  // Spacing between buttons is 10 pixels
        hbButtons.setAlignment(Pos.CENTER);  // Center the buttons in the HBox
        Button loginButton = new Button(resourceBundle.getString("loginButton"));
        Button cancelButton = new Button(resourceBundle.getString("cancelButton"));
        hbButtons.getChildren().addAll(loginButton, cancelButton);

        // Add the HBox to the grid
        grid.add(hbButtons, 1, 4);

        // Set action for buttons
        loginButton.setOnAction(e -> {
            String user = userTextField.getText();
            String pass = pwBox.getText();

            Login.LogingFrame(user, pass);
        });

        cancelButton.setOnAction(e -> closeLoginGUI()); // Call the method to close the GUI

        // Create the scene and set the size
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    // Static method to close the LoginGUI
    public static void closeLoginGUI() {
        if (loginStage != null) {
            loginStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}