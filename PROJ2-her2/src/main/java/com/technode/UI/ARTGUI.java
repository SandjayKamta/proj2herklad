package com.technode.UI;
import com.technode.Login.LoginCred;
import com.technode.Login.UserSession;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ARTGUI {

    public void show() {
        Stage secondStage = createStage();
        TabPane tabPane = createTabPane();

        createRegularTabs(tabPane);
        createProfileTab(tabPane);

        Scene scene = new Scene(tabPane, 800, 600);
        secondStage.setScene(scene);
        secondStage.show();
    }

    private Stage createStage() {
        Stage stage = new Stage();
        stage.setTitle("Second GUI");
        return stage;
    }

    private TabPane createTabPane() {
        return new TabPane();
    }

    private void createRegularTabs(TabPane tabPane) {
        Tab regularTab = new Tab();
        regularTab.setText("Tab");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);

        VBox chatBox = initializeChatBox(); // Obtain the chat box

        HBox hbButtons = new HBox(10);  // Spacing between buttons is 10 pixels
        hbButtons.setAlignment(Pos.TOP_RIGHT); // Align HBox to the top-right corner of the grid
        Button newTabButton = new Button("New Tab");
        Button newProfileTabButton = new Button("New Profile Tab");
        hbButtons.getChildren().addAll(newTabButton, newProfileTabButton);

        // Create a dummy node to push the buttons to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbButtons.getChildren().add(spacer);

        grid.add(hbButtons, 0, 0);
        newTabButton.setOnAction(e -> createRegularTabs(tabPane));
        newProfileTabButton.setOnAction(e -> createProfileTab(tabPane));

        // Add the chat box to the grid
        grid.add(chatBox, 0, 1); // Assuming chat box goes below the button

        regularTab.setContent(grid);
        tabPane.getTabs().add(regularTab);
    }


    private void createProfileTab(TabPane tabPane) {
        Tab profileTab = new Tab();
        profileTab.setText("Profile");
        String currentUser = UserSession.getCurrentUser();
        LoginCred currentUserCred = UserSession.getCurrentUserCredentials();
        VBox profileContent = new VBox();
        profileContent.setAlignment(Pos.CENTER);
        profileContent.setSpacing(10);
        profileContent.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        Label profileLabel = new Label("Profile Page");
        Label userNameLabel = new Label("User Name:");
        TextField UserNameTextField = new TextField("John doe");
        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField(currentUserCred.getEmail());

        profileContent.getChildren().addAll(profileLabel, userNameLabel, UserNameTextField, emailLabel, emailTextField);
        profileTab.setContent(profileContent);
        tabPane.getTabs().add(profileTab);
    }

    private VBox initializeChatBox() {
        VBox chatBox = new VBox();
        chatBox.setSpacing(10);
        chatBox.setAlignment(Pos.TOP_LEFT);

        // Create components for the chat box
        Label chatLabel = new Label("Chat Box");
        TextArea chatTextArea = new TextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setPrefRowCount(10);
        TextField userInputField = new TextField();
        Button sendButton = new Button("Send");

        // Add components to the chat box
        chatBox.getChildren().addAll(chatLabel, chatTextArea, userInputField, sendButton);

        // Event handling for sending messages
        sendButton.setOnAction(e -> {
            String userInput = userInputField.getText();
            // You can process the user input here or pass it to another method/class
            chatTextArea.appendText("You: " + userInput + "\n");
            userInputField.clear();
        });

        return chatBox;
    }
}
