package de.dhbw.karlsruhe.controller;


import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

// FIXME: @ Bilz Sauber dokumentieren
public class ScreensFramework extends Application {

    // Lege Namen und Pfade der einzelnen Scenes fest.
    public static final String SCREEN_LOGIN = "login";
    public static final String SCREEN_REGISTER = "register";
    public static final String SCREEN_UNTERNEHMEN_ANLEGEN = "unternehmen anlegen";
    public static final String SCREEN_TEILNEHMER_BEARBEITEN = "teilnehmer bearbeiten";
    public static final String SCREEN_TEILNEHMER_UEBERSICHT = "teilnehmer_uebersicht";
    public static final String SCREEN_SPIEL_ANLEGEN = "spiel_anlegen";
    public static final String SCREEN_SPIEL_VERWALTEN = "spiel_verwalten";
    public static final String SCREEN_LOGIN_FILE = "scene_login.fxml";
    public static final String SCREEN_REGISTER_FILE = "scene_register.fxml";
    public static final String SCREEN_UNTERNEHMEN_ANLEGEN_FILE = "scene_unternehmen_anlegen.fxml";
    public static final String SCREEN_TEILNEHMER_BEARBEITEN_FILE = "scene_teilnehmer_bearbeiten.fxml";
    public static final String SCREEN_TEILNEHMER_UEBERSICHT_FILE = "scene_teilnehmer_uebersicht.fxml";
    public static final String SCREEN_SPIEL_ANLEGEN_FILE = "scene_spiel_anlegen.fxml";
    public static final String SCREEN_SPIEL_VERWALTEN_FILE = "scene_spiel_verwalten.fxml";

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
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT, ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN, ScreensFramework.SCREEN_SPIEL_ANLEGEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN, ScreensFramework.SCREEN_SPIEL_VERWALTEN_FILE);
        // Lege Screen fest, der als Erstes aufgerufen wird.
        if(AktuelleSpieldaten.getSpiel() != null) { //Spiel konnte geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
        }
        else { //es konnte kein Spiel geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
        }
        Group root = new Group();
        root.getChildren().addAll(screenController);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Anika");
        window.show();


    }


}
