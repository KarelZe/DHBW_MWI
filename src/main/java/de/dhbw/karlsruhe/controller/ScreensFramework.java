package de.dhbw.karlsruhe.controller;


import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

// FIXME: @ Bilz Sauber dokumentieren
public class ScreensFramework extends Application {

    // Lege Namen und Pfade der einzelnen Scenes fest.
    public static final String SCREEN_LOGIN = "login";
    public static final String SCREEN_REGISTER = "register";
    public static final String SCREEN_UNTERNEHMEN_ANLEGEN = "unternehmen anlegen";
    public static final String SCREEN_WERTPAPIER_ANLEGEN = "wertpapier anlegen";
    public static final String SCREEN_TEILNEHMER_BEARBEITEN = "teilnehmer bearbeiten";
    public static final String SCREEN_TEILNEHMER_UEBERSICHT = "teilnehmer_uebersicht";
    public static final String SCREEN_SPIEL_ANLEGEN = "spiel_anlegen";
    public static final String SCREEN_SPIEL_VERWALTEN = "spiel_verwalten";
    public static final String SCREEN_PERIODEN_DETAIL = "perioden_detail";
    public static final String SCREEN_PERIODE_ANLEGEN = "periode_anlegen";
    public static final String SCREEN_TEILNEHMER_DRUCKEN = "teilnehmer_drucken";

    public static final String SCREEN_LOGIN_FILE = "scene_login.fxml";
    public static final String SCREEN_REGISTER_FILE = "scene_register.fxml";
    static final String SCREEN_TEILNEHMER_BEARBEITEN_FILE = "scene_teilnehmer_bearbeiten.fxml";
    static final String SCREEN_SPIEL_VERWALTEN_FILE = "scene_spiel_verwalten.fxml";
    private static final String SCREEN_UNTERNEHMEN_ANLEGEN_FILE = "scene_unternehmen_anlegen.fxml";
    private static final String SCREEN_WERTPAPIER_ANLEGEN_FILE = "scene_wertpapier_anlegen.fxml";
    private static final String SCREEN_TEILNEHMER_UEBERSICHT_FILE = "scene_teilnehmer_uebersicht.fxml";
    private static final String SCREEN_SPIEL_ANLEGEN_FILE = "scene_spiel_anlegen.fxml";
    private static final String SCREEN_PERIODE_ANLEGEN_FILE = "scene_periode_anlegen.fxml";
    private static final String SCREEN_PERIODEN_DETAIL_FILE = "scene_perioden_detail.fxml";
    private static final String SCREEN_TEILNEHMER_DRUCKEN_FILE = "scene_teilnehmer_druck.fxml";

    private static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        // Füge Screens zum ScreensController hinzu
        ScreenController screenController = new ScreenController();
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_REGISTER, ScreensFramework.SCREEN_REGISTER_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN, ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN, ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN_FILE);
        //screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN, ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT, ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN, ScreensFramework.SCREEN_SPIEL_ANLEGEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN, ScreensFramework.SCREEN_SPIEL_VERWALTEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_DRUCKEN, ScreensFramework.SCREEN_TEILNEHMER_DRUCKEN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL, ScreensFramework.SCREEN_PERIODEN_DETAIL_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_PERIODE_ANLEGEN, ScreensFramework.SCREEN_PERIODE_ANLEGEN_FILE);
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

        //MenüBar erstellen
        MenuBar menuBar = new MenuBar();
        //Menü erstelleb
        Menu buttonMenu = new Menu("Menü");
        //Menüpunkte erstellen
        MenuItem teilnehmer_hinz = new MenuItem("Teilnehmer hinzufügen");
        //Menüpunkt zum Menü hinzufügen
        buttonMenu.getItems().add(teilnehmer_hinz);
        //Event hinzufügen
        teilnehmer_hinz.setOnAction(e ->screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN_FILE));
        //Menü zur Menübar hinzufügen
        menuBar.getMenus().add(buttonMenu);
        //css laden
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        window.setScene(scene);
        window.setTitle("Anika");
        window.setMaximized(true);
        window.show();
    }


}
