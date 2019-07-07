package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Controller für das Menü
 * @author Markus Bilz
 */
public class MenuController implements ControlledScreen, InvalidationListener {
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu mTeilnehmer, mAdministration, mAuswertung, mSpiel;
    @FXML
    private MenuItem mIspielInitialisieren, mIspielAnlegen, mIspielVerwalten, mIperiodeAnlegen, mIperiodePflegen, mIunternehmenAnlegen, mIwertpapierAnlegen, mIteilnehmerUebersicht, mIteilnehmerDrucken, mIwertpapierVerkaufen, mIwertpapierKaufen, mIteilnehmerHistorie, mIteilnehmerLogin, mIteilnehmerRegistrieren;
    @FXML
    private Button btnLogout;

    private ScreenController screenController;

    /**
     * Event-Handler für den Logout-Button
     * @param actionEvent Event
     */
    public void doLogout(ActionEvent actionEvent) {
        AktuelleSpieldaten.getInstanz().setBenutzer(null);
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }

    /**
     * Konkrete Implementierung für den Zugriff auf den Controller des übergeordneten Screens
     *
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    /**
     * Initialisierung
     * @author Markus Bilz
     */
    @FXML
    private void initialize() {
        AktuelleSpieldaten aktuelleSpieldaten = AktuelleSpieldaten.getInstanz();
        aktuelleSpieldaten.addListener(this);
        System.out.println(aktuelleSpieldaten);

        // Füge Screens zum ScreensController hinzu
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN, ScreensFramework.SCREEN_SPIEL_ANLEGEN_FILE);

        // Lege Screen fest, der als Erstes aufgerufen wird.
        if (AktuelleSpieldaten.getInstanz().getSpiel() != null) { //Spiel konnte geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);


        } else { //es konnte kein Spiel geladen werden
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
        }

        //Event hinzufügen
        mIteilnehmerLogin.setOnAction(event -> {
                    screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
                    screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
                }
        );
        mIteilnehmerRegistrieren.setOnAction(e -> {
            screenController.loadScreen(ScreensFramework.SCREEN_REGISTER, ScreensFramework.SCREEN_REGISTER_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_REGISTER);
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
            screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT, ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT_FILE);
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

        // Konfiguriere Menü anhand des aktuellen Spiels
        konfiguriereMenu();
    }

    /**
     * Methode passt Menü in Abhängigkeit der Rechte des angemeldeten Users und des aktuellen Spiels an.
     * Implementierung des Observer-Patterns (GOF).
     *
     * @param observable Observable zur Verarbeitung
     * @author Markus Bilz
     */
    @Override
    public void invalidated(Observable observable) {
        konfiguriereMenu();
    }

    /**
     * Methode zur Konfiguration des Menüs. Rollen-basiert werden Menüs aktiviert oder deaktiviert.
     * @author Markus Bilz
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
        // setze initialen Stand. Diese Einträge erfordern keine besonderen Berechtigungen.
        mIteilnehmerLogin.setDisable(false);
        mIteilnehmerRegistrieren.setDisable(false);
        // aktiviere Rollen-basiert einzelne Menüs oder Menü-Einträge
        if (benutzer == null) {
            mTeilnehmer.setDisable(false);
            mSpiel.setDisable(false);
        } else if (benutzer.getRolle().getId() == Rolle.ROLLE_SPIELLEITER) {
            mAdministration.setDisable(false);
            mAuswertung.setDisable(false);
            mSpiel.setDisable(false);
            btnLogout.setDisable(false);
        } else {
            mTeilnehmer.setDisable(false);
            mIteilnehmerHistorie.setDisable(false);
            mIwertpapierKaufen.setDisable(false);
            mIwertpapierVerkaufen.setDisable(false);
            mIteilnehmerRegistrieren.setDisable(true);
            mIteilnehmerLogin.setDisable(true);
            btnLogout.setDisable(false);
        }
    }
}
