package de.dhbw.karlsruhe.controller;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

// FIXME: @ Bilz Sauber dokumentieren
public class ScreensFramework extends Application {

    // Lege Namen und Pfade der einzelnen Scenes fest.
    static final String SCREEN_LOGIN = "login";
    static final String SCREEN_REGISTER = "register";
    static final String SCREEN_UNTERNEHMEN_ANLEGEN = "unternehmen anlegen";
    static final String SCREEN_TEILNEHMER_BEARBEITEN = "teilnehmer bearbeiten";
    private static final String SCREEN_LOGIN_FILE = "scene_login.fxml";
    private static final String SCREEN_REGISTER_FILE = "scene_register.fxml";
    static final String SCREEN_UNTERNEHMEN_ANLEGEN_FILE = "scene_unternehmen_anlegen.fxml";
    static final String SCREEN_TEILNEHMER_BEARBEITEN_FILE = "scene_teilnehmer_bearbeiten.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        // Füge Screens zum ScreensController hinzu
        ScreenController screenController = new ScreenController();
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_REGISTER, ScreensFramework.SCREEN_REGISTER_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN, ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN, ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN_FILE);
        // Lege Screen fest, der als Erstes aufgerufen wird.
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
        Group root = new Group();
        root.getChildren().addAll(screenController);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Anika");
        window.show();
    }


}
