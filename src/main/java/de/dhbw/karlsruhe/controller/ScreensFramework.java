package de.dhbw.karlsruhe.controller;


import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public static final String SCREEN_INVESTMENT_UEBERSICHT = "investment_uebersicht";
    public static final String SCREEN_TEILNEHMER_HISTORIE = "teilnehmer_historie";
    public static final String SCREEN_WERTPAPIER_KAUFEN = "wertpapier_kaufen";

    public static final String SCREEN_LOGIN_FILE = "scene_login.fxml";
    public static final String SCREEN_REGISTER_FILE = "scene_register.fxml";
    public static final String SCREEN_TEILNEHMER_BEARBEITEN_FILE = "scene_teilnehmer_bearbeiten.fxml";
    public static final String SCREEN_SPIEL_VERWALTEN_FILE = "scene_spiel_verwalten.fxml";
    public static final String SCREEN_UNTERNEHMEN_ANLEGEN_FILE = "scene_unternehmen_anlegen.fxml";
    public static final String SCREEN_WERTPAPIER_ANLEGEN_FILE = "scene_wertpapier_anlegen.fxml";
    public static final String SCREEN_TEILNEHMER_UEBERSICHT_FILE = "scene_teilnehmer_uebersicht.fxml";
    public static final String SCREEN_SPIEL_ANLEGEN_FILE = "scene_spiel_anlegen.fxml";
    public static final String SCREEN_PERIODE_ANLEGEN_FILE = "scene_periode_anlegen.fxml";
    public static final String SCREEN_PERIODEN_DETAIL_FILE = "scene_perioden_detail.fxml";
    public static final String SCREEN_TEILNEHMER_DRUCKEN_FILE = "scene_teilnehmer_druck.fxml";
    public static final String SCREEN_INVESTMENT_UEBERSICHT_FILE = "scene_investment_uebersicht.fxml";
    public static final String SCREEN_TEILNEHMER_HISTORIE_FILE = "scene_teilnehmer_historie.fxml";
    public static final String SCREEN_WERTPAPIER_KAUFEN_FILE = "scene_wertpapier_kaufen.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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
        screenController.loadScreen(ScreensFramework.SCREEN_INVESTMENT_UEBERSICHT, ScreensFramework.SCREEN_INVESTMENT_UEBERSICHT_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE, ScreensFramework.SCREEN_TEILNEHMER_HISTORIE_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_KAUFEN, ScreensFramework.SCREEN_WERTPAPIER_KAUFEN_FILE);
        // Lege Screen fest, der als Erstes aufgerufen wird.
        if (AktuelleSpieldaten.getSpiel() != null) { //Spiel konnte geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);


        } else { //es konnte kein Spiel geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
        }

        //MenüBar erstellen
        MenuBar mbHaupt = new MenuBar();
        //Menü erstelleb
        Menu mTeilnehmer = new Menu("Teilnehmer");
        //Menüpunkte erstellen
        MenuItem mIteilnehmerRegistrieren = new MenuItem("Teilnehmer registrieren");
        MenuItem mIteilnehmerLogin = new MenuItem("Teilnehmer einloggen");
        MenuItem mIwertpapierKaufen = new MenuItem("Wertpapier kaufen");
        //Menüpunkt zum Menü hinzufügen
        mTeilnehmer.getItems().addAll(mIteilnehmerLogin, mIteilnehmerRegistrieren, mIwertpapierKaufen);

        Menu mAdministration = new Menu("Administration");
        MenuItem mIspielAnlegen = new MenuItem("Spiel anlegen");
        MenuItem mIspielVerwalten = new MenuItem("Spiel verwalten");
        MenuItem mIperiodeAnlegen = new MenuItem("Periode anlegen");
        MenuItem mIperiodePflegen = new MenuItem("Periode pflegen");
        MenuItem mIunternehmenAnlegen = new MenuItem("Unternehmen anlegen");
        MenuItem mIwertpapierAnlegen = new MenuItem("Wertpapier anlegen");
        MenuItem mIteilnehmerUebersicht = new MenuItem("Teilnehmer zur\u00fccksetzen");

        mAdministration.getItems().addAll(mIspielAnlegen, mIspielVerwalten, mIperiodeAnlegen, mIperiodePflegen, mIunternehmenAnlegen, mIwertpapierAnlegen, mIteilnehmerUebersicht);

        Menu mAuswertung = new Menu("Auswertung");
        MenuItem mIteilnehmerDrucken = new MenuItem("Bestenliste drucken");
        mAuswertung.getItems().addAll(mIteilnehmerDrucken);

        //Menü zur Menübar hinzufügen
        mbHaupt.getMenus().addAll(mTeilnehmer, mAdministration, mAuswertung);

        VBox root = new VBox(mbHaupt);
        root.getChildren().addAll(screenController);
        Scene scene = new Scene(root);

        //Event hinzufügen
        mIteilnehmerLogin.setOnAction(event ->
                screenController.setScreen(ScreensFramework.SCREEN_LOGIN));
        mIteilnehmerRegistrieren.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_REGISTER, ScreensFramework.SCREEN_REGISTER_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_REGISTER);
        });
        mIunternehmenAnlegen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN, ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN);
        });
        mIwertpapierAnlegen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN, ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN);
        });
        mIteilnehmerUebersicht.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT, SCREEN_TEILNEHMER_UEBERSICHT_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);
        });
        mIteilnehmerDrucken.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_DRUCKEN, ScreensFramework.SCREEN_TEILNEHMER_DRUCKEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_DRUCKEN);
        });
        mIperiodePflegen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL, ScreensFramework.SCREEN_PERIODEN_DETAIL_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL);
        });
        mIperiodeAnlegen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_PERIODE_ANLEGEN, ScreensFramework.SCREEN_PERIODE_ANLEGEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_PERIODE_ANLEGEN);
        });
        mIspielAnlegen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN, ScreensFramework.SCREEN_SPIEL_ANLEGEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
        });
        mIspielVerwalten.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN, ScreensFramework.SCREEN_SPIEL_VERWALTEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN);
        });
        mIwertpapierKaufen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_KAUFEN, ScreensFramework.SCREEN_WERTPAPIER_KAUFEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_KAUFEN);
        });


        //css laden
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Anika");
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setMaximized(true);
        primaryStage.show();


    }


}
