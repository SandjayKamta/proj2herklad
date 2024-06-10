package com.technode.UI.Element;

import com.technode.Settings;
import com.technode.UI.Language;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.util.*;


public class LanguageSwitch extends ChoiceBox<String> {

    //Map<String, String> response = Map.of("java", "Java is een programmeertaal");

//    public String message(String question, String documentation) {
//        Optional langs = response.entrySet().stream()
//                .filter(kv -> question.contains(kv.getKey()))
//                .map(kv -> kv.getValue())
//                .findAny();
//    }

    // TODO: implement ENUM in setValue and setOnAction

    public LanguageSwitch(Stage stage, Class<? extends Application> clazz) {
        super(FXCollections.observableList(Arrays.stream(Language.values())
                .map(l -> l.description)
                .toList()));
        this.setValue(Settings.currentLocale.getLanguage().equals("nl") ? "Nederlands" : "English"); // Set the default display value


        // Event handler for language selection
        this.setOnAction(e -> {
            String selectedDisplayLanguage = this.getValue();
            if ("English".equals(selectedDisplayLanguage)) {
                Settings.currentLocale = Locale.of("en");
            } else if ("Nederlands".equals(selectedDisplayLanguage)) {
                Settings.currentLocale = Locale.of("nl");
            }
            try {
                stage.hide();
                Application object = clazz.getDeclaredConstructor().newInstance();
                object.start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
