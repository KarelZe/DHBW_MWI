package de.dhbw.karlsruhe.controller;


import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Diese Klasse stellt den Einstiegspunkt für die Anwendung dar. Sie verwaltet hierfür die Referenzen auf
 * alle Scenees der Anwendung. Sie implementiert den {@code ScreenController} für den Wechsel und
 * den Aufruf von Scenes.
 *
 * @author Markus Bilz, Christian Fix, Jan Carlos Riecken, Raphael Winkler, Max Schwab, Ismail Nasir
 */

public class ScreensFramework extends Application implements InvalidationListener {

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
    public static final String SCREEN_WERTPAPIER_VERKAUFEN = "wertpapier_verkaufen";

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
    public static final String SCREEN_WERTPAPIER_VERKAUFEN_FILE = "scene_wertpapier_verkaufen.fxml";

    private Menu mTeilnehmer, mAdministration, mAuswertung, mSpiel;
    private MenuItem mIteilnehmerHistorie, mIwertpapierKaufen, mIwertpapierVerkaufen, mIteilnehmerRegistrieren, mIteilnehmerLogin, mIteilnehmerBearbeiten;
    private Button btnLogout;
    private ScreenController screenController = new ScreenController();

    /**
     * Diese Methode wird beim Start der Anwendung aufgerufen.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Diese Methode wird beim Start der Anwendung aufgerufen. Sie ist Teil des Lifecycles von JavaFX.
     * Sie erzeugt das Menü einschließlich der Menüeinträge. Darüber hinaus wird eine initiale Scene geladen.
     * Diese entspricht einem Screen zum Login, sofern ein Spiel vorhanden ist und andernfalls einem Screen für die
     * Anlage eines Spiels. Neben den Scenes werden darüber hinaus Eigenschaften wie Fenstergröße oder Icon festgelegt,
     * die über die gesamte Anwendung konsistent sind.
     *
     * @param primaryStage Container, in dem alle visuellen Elemente angezeigt werden.
     */
    @Override
    public void start(Stage primaryStage) {


        AktuelleSpieldaten aktuelleSpieldaten = AktuelleSpieldaten.getInstanz();
        aktuelleSpieldaten.addListener(this);
        System.out.println(aktuelleSpieldaten);

        // Füge Screens zum ScreensController hinzu
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN, ScreensFramework.SCREEN_SPIEL_ANLEGEN_FILE);

        // Lege Screen fest, der als Erstes aufgerufen wird.
        if (aktuelleSpieldaten.getSpiel() != null) { //Spiel konnte geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);


        } else { //es konnte kein Spiel geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
        }

        //MenüBar erstellen
        MenuBar mbHaupt = new MenuBar();
        //Menü erstellen
        mTeilnehmer = new Menu("Benutzer");
        //Menüpunkte erstellen
        mIteilnehmerRegistrieren = new MenuItem("Benutzer registrieren");
        mIteilnehmerLogin = new MenuItem("Benutzer einloggen");
        mIteilnehmerBearbeiten = new MenuItem("Benutzer bearbeiten");
        mIteilnehmerHistorie = new MenuItem("Transaktionshistorie anzeigen");
        mIwertpapierKaufen = new MenuItem("Wertpapier kaufen");
        mIwertpapierVerkaufen = new MenuItem("Wertpapier verkaufen");

        //Menüpunkt zum Menü hinzufügen
        mTeilnehmer.getItems().addAll(mIteilnehmerLogin, mIteilnehmerRegistrieren, mIteilnehmerBearbeiten, mIteilnehmerHistorie, mIwertpapierKaufen, mIwertpapierVerkaufen);

        mSpiel = new Menu("Spiel");
        MenuItem mIspielInitialisieren = new MenuItem("Spiel initialisieren");
        mSpiel.getItems().addAll(mIspielInitialisieren);

        mAdministration = new Menu("Administration");
        MenuItem mIspielAnlegen = new MenuItem("Spiel anlegen");
        MenuItem mIspielVerwalten = new MenuItem("Spiel verwalten");
        MenuItem mIperiodeAnlegen = new MenuItem("Periode anlegen");
        MenuItem mIperiodePflegen = new MenuItem("Periode pflegen");
        MenuItem mIunternehmenAnlegen = new MenuItem("Unternehmen anlegen");
        MenuItem mIwertpapierAnlegen = new MenuItem("Wertpapier anlegen");
        MenuItem mIteilnehmerUebersicht = new MenuItem("Benutzer zur\u00fccksetzen");

        mAdministration.getItems().addAll(mIspielAnlegen, mIspielVerwalten, mIperiodeAnlegen, mIperiodePflegen, mIunternehmenAnlegen, mIwertpapierAnlegen, mIteilnehmerUebersicht);

        mAuswertung = new Menu("Auswertung");
        MenuItem mIteilnehmerDrucken = new MenuItem("Bestenliste drucken");
        mAuswertung.getItems().addAll(mIteilnehmerDrucken);

        //Menü zur Menübar hinzufügen
        mbHaupt.getMenus().addAll(mTeilnehmer, mAdministration, mAuswertung, mSpiel);

        // Erzeuge Button für Log-Out.
        btnLogout = new Button("Ausloggen");
        btnLogout.setOnAction(event -> doLogout());

        // Konfiguriere Menü anhand des aktuellen Spiels
        konfiguriereMenu();

        HBox menuWrapper = new HBox(mbHaupt, btnLogout);
        menuWrapper.setSpacing(5);
        menuWrapper.setPadding(new Insets(5, 5, 5, 5));
        HBox.setHgrow(mbHaupt, Priority.ALWAYS);
        HBox.setHgrow(btnLogout, Priority.NEVER);
        // Konfiguriere Scene-Aufbau
        VBox root = new VBox(menuWrapper);
        root.getChildren().addAll(screenController);

        Scene scene = new Scene(root);

        //Event hinzufügen
        mIteilnehmerLogin.setOnAction(event -> {
            screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
        });
        mIteilnehmerRegistrieren.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_REGISTER, ScreensFramework.SCREEN_REGISTER_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_REGISTER);
        });
        mIteilnehmerBearbeiten.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN, ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN);
        });

        mIteilnehmerHistorie.setOnAction((event -> {
            screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE, ScreensFramework.SCREEN_TEILNEHMER_HISTORIE_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE);
        }));
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
        mIwertpapierVerkaufen.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_VERKAUFEN, ScreensFramework.SCREEN_WERTPAPIER_VERKAUFEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_VERKAUFEN);
        });
        mIspielInitialisieren.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN, ScreensFramework.SCREEN_SPIEL_ANLEGEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
        });

        // CSS laden
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());

        // Setze Text in Fenster Bar und Logo
        primaryStage.setScene(scene);
        primaryStage.setTitle("Anika");
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    /**
     * Methode passt Menü in Abhängigkeit der Rechte des angemeldeten Users und des aktuellen Siels an.
     * Implementierung des Observer-Patterns (GOF).
     *
     * @param observable Observable zur Verarbeitung
     */
    @Override
    public void invalidated(Observable observable) {
        konfiguriereMenu();
    }

    /**
     * Methode zur Konfiguration des Menüs. Rollen-basiert werden Menüs aktiviert oder deaktiviert.
     */
    private void konfiguriereMenu() {
        Benutzer benutzer = AktuelleSpieldaten.getInstanz().getBenutzer();
        Spiel spiel = AktuelleSpieldaten.getInstanz().getSpiel();
        // setze initialen Stand für Menüeinträge
        mAdministration.setDisable(true);
        mAuswertung.setDisable(true);
        mTeilnehmer.setDisable(true);
        mSpiel.setDisable(true);
        btnLogout.setDisable(true);
        // setze initialen Stand für Menüeinträge
        mIteilnehmerHistorie.setDisable(true);
        mIwertpapierKaufen.setDisable(true);
        mIwertpapierVerkaufen.setDisable(true);
        mIteilnehmerBearbeiten.setDisable(true);
        // setze initialen Stand. Diese Einträge erfordern keine besonderen Berechtigungen.
        mIteilnehmerLogin.setDisable(false);
        mIteilnehmerRegistrieren.setDisable(false);

        if (benutzer != null) {
            // Es ist Benutzer vorhanden und unterscheide nach Rollen
            if (benutzer.getRolle().getId() == Rolle.ROLLE_SPIELLEITER) {
                mAdministration.setDisable(false);
                mAuswertung.setDisable(false);
                mSpiel.setDisable(false);
                btnLogout.setDisable(false);
                mIteilnehmerLogin.setDisable(false);
            } else {
                mTeilnehmer.setDisable(false);
                mIteilnehmerHistorie.setDisable(false);
                mIwertpapierKaufen.setDisable(false);
                mIwertpapierVerkaufen.setDisable(false);
                mIteilnehmerRegistrieren.setDisable(true);
                mIteilnehmerLogin.setDisable(true);
                btnLogout.setDisable(false);
            }
            mIteilnehmerBearbeiten.setDisable(false);

        } else if (spiel != null) {
            // Es ist Spiel vorhanden, aber kein aktiver Teilnehmer
            mTeilnehmer.setDisable(false);
        } else {
            // Es ist weder Benutzer noch Spiel vorhanden betrifft den aller ersten Anwendungsstart.
            mSpiel.setDisable(false);
        }
    }

    /**
     * Methode zum Durchführen eines Log-Outs.
     */
    private void doLogout() {
        AktuelleSpieldaten.getInstanz().setBenutzer(null);
        screenController.loadScreen(SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }

}