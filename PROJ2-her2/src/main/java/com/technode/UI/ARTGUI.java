package com.technode.UI;
import com.technode.Login.*;
import com.technode.QueryResolution.*;
import com.technode.QueryResolution.textToImage.TextToImageResolutionStrategyFactory;
import com.technode.Settings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.technode.Login.LoginCred;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.technode.Login.hashPassword.hashPassword;


public class ARTGUI { ;
    public static String currentUser = UserSession.getCurrentUser();
    public LoginCred currentUserCred = UserSession.getCurrentUserCredentials();
    private String currentUsername = CredentialManager.getUsernameById(currentUser);
    private String CurrentPass = currentUserCred.getPass();
    private String CurrentEmail = currentUserCred.getEmail();
    private Stage secondStage;
    private TabPane tabPane;
    private ResourceBundle resourceBundle;

    public void ARTGUI(String CurrentPass, String CurrentEmail) {
        this.CurrentPass = CurrentPass;
        this.CurrentEmail = CurrentEmail;

    }

    public void show() {
        resourceBundle = ResourceBundle.getBundle("com.technode.messages", Settings.currentLocale);

        secondStage = createStage();
        tabPane = createTabPane();

        createProfileTab(tabPane);
        LoadUserTabs();
        createAddTab(tabPane); // Create the special "add tab"


        Scene scene = new Scene(tabPane, 800, 600);
        secondStage.setScene(scene);
        secondStage.show();

        secondStage.setOnCloseRequest(event -> saveUserTabs());
    }

    private Stage createStage() {
        Stage stage = new Stage();
        stage.setTitle(resourceBundle.getString("userTitle") + " " + currentUsername);
        return stage;
    }

    private TabPane createTabPane() {
        return new TabPane();
    }

    private  void LoadUserTabs(){
                UserSessionData sessionData = UserSession.loadSessionData();
                if (sessionData != null && !sessionData.getTabs().isEmpty()) {
                    for (UserSessionData.TabData tabData : sessionData.getTabs()) {
                        createLoadedTab(tabData);
                    }


                } else if (sessionData.getTabs().isEmpty()) {
                    createNewTab(tabPane); // Create a new tab if session data is null
                } else {
                    createNewTab(tabPane);
                }
        }


    private void saveUserTabs() {
        List<UserSessionData.TabData> tabDataList = new ArrayList<>();
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(resourceBundle.getString("profile"))) continue; // Skip profile tab
            if (tab.getText().equals("+")) continue;

            VBox chatBox = (VBox) ((GridPane) tab.getContent()).getChildren().get(1);
            TextArea chatTextArea = (TextArea) chatBox.getChildren().get(1);

            List<String> messages = new ArrayList<>();
            for (String message : chatTextArea.getText().split("\n")) {
                messages.add(message);
            }

            tabDataList.add(new UserSessionData.TabData(tab.getText(), messages));
        }
        UserSessionData sessionData = new UserSessionData();
        sessionData.setTabs(tabDataList);
        UserSession.saveSessionData(sessionData);
    }

    private void createTab(TabPane tabPane, String title, List<String> chatMessages) {
        Tab tab = new Tab();
        tab.setText(title);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);

        VBox chatBox = initializeChatBox(chatMessages);

        HBox hbButtons = new HBox(10);
        hbButtons.setAlignment(Pos.TOP_RIGHT);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbButtons.getChildren().add(spacer);

        grid.add(hbButtons, 0, 0);
        grid.add(chatBox, 0, 1);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void createImageTab(TabPane tabPane, String title, List<String> chatMessages) {
        Tab tab = new Tab();
        tab.setText(title);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);

        VBox chatBox = initializeImageBox(chatMessages);

        HBox hbButtons = new HBox(10);
        hbButtons.setAlignment(Pos.TOP_RIGHT);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbButtons.getChildren().add(spacer);

        grid.add(hbButtons, 0, 0);
        grid.add(chatBox, 0, 1);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void createLoadedTab(UserSessionData.TabData tabData) {
        createTab(tabPane, tabData.getTitle(), tabData.getChatMessages());
    }

    private void createNewTab(TabPane tabPane) {
        // Show an input dialog to get the title of the new tab
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(resourceBundle.getString("newTab"));
        dialog.setHeaderText(resourceBundle.getString("createNewTab"));
        dialog.setContentText(resourceBundle.getString("enterTabTitle") + ":");

        dialog.showAndWait().ifPresent(title -> {
            
            chooseTypeDialog(tabPane, title, new ArrayList<>());
        });
    }

    private void chooseTypeDialog(TabPane tabPane, String title, List<String> chatMessages) {
        // Show an input dialog to get the title of the new tab
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.setTitle(resourceBundle.getString("newTab"));
        dialog.setHeaderText(resourceBundle.getString("createNewTab"));
        ObservableList<String> list = dialog.getItems();
        //Adding items to the list
        list.add("Text");
        list.add("Text to Image");

        dialog.showAndWait().ifPresent(_ -> {
            
            if(dialog.getSelectedItem() == "Text") {
                createTab(tabPane, title, chatMessages);

                // Create and set TabData for the new tab
                UserSessionData.TabData tabData = new UserSessionData.TabData();
                tabData.setTitle(title);
                tabData.setChatMessages(new ArrayList<>());
                saveNewTabData(tabData);
            } else {
                createImageTab(tabPane, title, chatMessages);

                // Create and set TabData for the new tab
                UserSessionData.TabData tabData = new UserSessionData.TabData();
                tabData.setTitle(title);
                tabData.setChatMessages(new ArrayList<>());
                saveNewTabData(tabData);
            }
        });
    }

    private void saveNewTabData(UserSessionData.TabData tabData) {
        UserSessionData sessionData = UserSession.loadSessionData();
        if (sessionData == null) {
            sessionData = new UserSessionData();
        }
        List<UserSessionData.TabData> tabs = sessionData.getTabs();
        if (tabs == null) {
            tabs = new ArrayList<>();
        }
        tabs.add(tabData);
        sessionData.setTabs(tabs);
        UserSession.saveSessionData(sessionData);
    }

    private void createProfileTab(TabPane tabPane) {
        Tab profileTab = new Tab();
        profileTab.setText(resourceBundle.getString("profile"));
        profileTab.setClosable(false);
        String currentUsername = CredentialManager.getUsernameById(UserSession.getCurrentUser());
        LoginCred currentUserCred = UserSession.getCurrentUserCredentials();
        VBox profileContent = new VBox();
        profileContent.setAlignment(Pos.CENTER);
        profileContent.setSpacing(10);
        profileContent.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        Label profileLabel = new Label(resourceBundle.getString("profilePage"));
        Label userNameLabel = new Label(resourceBundle.getString("usernameLabel"));
        TextField UserNameTextField = new TextField(currentUsername);
        Label emailLabel = new Label(resourceBundle.getString("email"));
        TextField emailTextField = new TextField(currentUserCred.getEmail());
        Label newPassLabel = new Label(resourceBundle.getString("newPassword"));
        TextField newPassTextField = new TextField();
        Label FillPassLabel = new Label(resourceBundle.getString("currentPassword"));
        TextField FillPassTextField = new TextField();

        Button change = new Button(resourceBundle.getString("change"));
        change.setOnAction(e -> {
            String email = emailTextField.getText();
            String newPassword = newPassTextField.getText();
            String currentPassword = FillPassTextField.getText();
            String newUsername = UserNameTextField.getText();
            change(newUsername, newPassword, email, currentPassword);
        });

        Button LogOutButton = new Button(resourceBundle.getString("logOut"));
        LogOutButton.setOnAction(e -> {
            saveUserTabs();
            UserSession.clearSession();
            secondStage.close();
            LoginGUI.openLoginGUI();
        });

        profileContent.getChildren().addAll(profileLabel, userNameLabel, UserNameTextField, emailLabel, emailTextField, newPassLabel, newPassTextField, FillPassLabel, FillPassTextField, change, LogOutButton);
        profileTab.setContent(profileContent);
        tabPane.getTabs().add(profileTab);
    }

    // Overloaded method to create an empty chat box
    private VBox initializeChatBox() {
        return initializeChatBox(new ArrayList<>());
    }

    private VBox initializeChatBox(List<String> chatMessages) {
        VBox chatBox = new VBox();
        chatBox.setSpacing(10);
        chatBox.setAlignment(Pos.TOP_LEFT);

        // Create components for the chat box
        Label chatLabel = new Label("Chat Box");
        TextArea chatTextArea = new TextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setPrefRowCount(10);
        for (String message : chatMessages) {
            chatTextArea.appendText(message + "\n");
        }
        TextField userInputField = new TextField();
        Button sendButton = new Button(resourceBundle.getString("send"));

        // Add components to the chat box
        chatBox.getChildren().addAll(chatLabel, chatTextArea, userInputField, sendButton);

        // Event handling for sending messages
        sendButton.setOnAction(e -> {
            String userInput = userInputField.getText();
            // You can process the user input here or pass it to another method/class
            chatTextArea.appendText("You: " + userInput + "\n");
            userInputField.clear();
            Object promptAnswer;
            QueryResolutionResult result = resolveQuery(userInput);
            promptAnswer = result.resultData();
            chatTextArea.appendText("Chatbot: " + promptAnswer + "\n");
        });

        return chatBox;
    }

    private VBox initializeImageBox(List<String> chatMessages) {
        VBox chatBox = new VBox();
        chatBox.setSpacing(10);
        chatBox.setAlignment(Pos.TOP_LEFT);

        // Create components for the chat box
        Label chatLabel = new Label("Image Box");
        
        VBox chats = new VBox();
        
        ScrollPane scrollPane = new ScrollPane(chats);
       
        TextField userInputField = new TextField();
        Button sendButton = new Button(resourceBundle.getString("send"));

        // Add components to the chat box
        chatBox.getChildren().addAll(chatLabel, scrollPane, userInputField, sendButton);

        // Event handling for sending messages
        sendButton.setOnAction(e -> {
            String userInput = userInputField.getText();
            // You can process the user input here or pass it to another method/class
            userInputField.clear();
            Image promptAnswer;
            QueryResolutionResult<Image> result = resolveImageQuery(userInput);
            promptAnswer = result.resultData();
//            chatTextArea.appendText("Chatbot: " + promptAnswer + "\n");
            ImageView imageView = createImageView(promptAnswer);
            chats.getChildren().add(imageView);
        });

        return chatBox;
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        setImageOnImageView(imageView, image);
        return imageView;
    }

    private void setImageOnImageView(ImageView imageView, Image imagePath) {
        imageView.setImage(imagePath);
    }
    
    public static void change(String newUsername, String newPassword, String newEmail, String currentPassword) {
        LoginCred currentCred = UserSession.getCurrentUserCredentials();

        // Verify the current password
        if (currentCred.getPass().equals(hashPassword(currentPassword))) {
            if (newEmail != null && !newEmail.isEmpty()) {
                UserSession.changeEmail(newEmail);
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                UserSession.changePassword(newPassword);
            }
            if (newUsername != null && !newUsername.isEmpty()) {
                String oldUsername = CredentialManager.getUsernameById(UserSession.getCurrentUser());
                CredentialManager.updateUsername(oldUsername, newUsername);
            }
            // Update current user credentials to reflect the changes
            UserSession.setCurrentUser(newUsername);
            System.out.println("Changes saved successfully!");
        } else {
            System.out.println("Current password is incorrect.");
        }
    }
    private QueryResolutionResult resolveQuery(String prompt) {
        QueryResolutionStrategyFactory factory = new TextToTextFactory();
        QueryResolutionStrategy strategy = factory.createQueryResolutionStrategy();
        QueryResolutionForm<String> form = new QueryResolutionForm<>(prompt);
        return strategy.resolve(form);
    }

    private QueryResolutionResult resolveImageQuery(String prompt) {
        QueryResolutionStrategyFactory factory = new TextToImageResolutionStrategyFactory();
        QueryResolutionStrategy strategy = factory.createQueryResolutionStrategy();
        QueryResolutionForm<String> form = new QueryResolutionForm<>(prompt);
        return strategy.resolve(form);
    }
    private void createAddTab(TabPane tabPane) {
        Tab addTab = new Tab();
        addTab.setText("+");
        addTab.setClosable(false);

        // Add a listener to the "+" tab to handle creating new tabs
        addTab.setOnSelectionChanged(event -> {
            if (addTab.isSelected()) {
                createNewTab(tabPane); // Create a new tab
                // Switch to the last tab (newly created one)
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 1);
                tabPane.getTabs().remove(addTab); // Remove the current "+" tab
                createAddTab(tabPane); // Add a new "+" tab
            }
        });

        tabPane.getTabs().add(addTab);
    }




}