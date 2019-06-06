package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.SpielRepository;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Optional;


public class LoginController implements ControlledScreen {


    public Button btnTeilnehmerBearbeiten;
    @FXML
    private TextField txtBenutzername, txtPasswort;

    private ScreenController screenController;

    @FXML
    private void doLogin(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_login.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);

        Optional<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findByBenutzernameAndPasswort(benutzername, passwortVerschluesselt);
        teilnehmer.ifPresentOrElse(t -> {
                    System.out.println(t + " @ " + t.getUnternehmen() + " $ " + t.getRolle());
                    AktuelleSpieldaten.setTeilnehmer(t);
                    //ToDo: Übersichts-Screen anzeigen
                    // TODO: @ Bilz Ersetzen durch Filter oder Map? https://www.callicoder.com/java-8-optional-tutorial/
                    if (AktuelleSpieldaten.getTeilnehmer().getRolle().getId() == Rolle.ROLLE_SPIELLEITER) {
                        //screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);
                        screenController.setScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN);

                    } else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login");
                        alert.setContentText("Login erfolgreich.");
                        alert.showAndWait();
                        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN, ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN_FILE);
                        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN);
                    }
                },
                () -> {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Benutzername oder Passwort");
                    alert.setContentText("Bitte erfassen Sie Ihren Benutzername und Ihr Passwort erneut.");
                    alert.showAndWait();
                });
    }

    @FXML
    private void doRegistration(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_REGISTER);
    }

    @FXML
    private void doPrint(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_DRUCKEN);
    }

    @FXML
    void doUnternehmenAnlegen(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN);
    }

    // Zur Erklärung https://stackoverflow.com/a/51392331
    // Zur Erklärung https://javabeginners.de/Frameworks/JavaFX/FXML.php
    @FXML
    private void initialize() {
        initializeAktuellesSpiel();
    }

    @FXML
    private void doEditUser(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN);
    }

    public void doWertpapierAnlegen() {
        screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN);
    }

    public void doTeilnehmerUebersicht() {
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);
    }

    public void doSpielAnlegen() {
        screenController.setScreen(ScreensFramework.SCREEN_SPIEL_ANLEGEN);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    private void initializeAktuellesSpiel() {
        Spiel aktuellesSpiel = SpielRepository.getAktivesSpiel();
        if (aktuellesSpiel != null) {
            AktuelleSpieldaten.setSpiel(aktuellesSpiel);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Initialisieren");
            alert.setContentText("Es konnte kein Spiel geladen werden.");
        }
    }

    public void doPeriodeAnlegen() {
        screenController.setScreen(ScreensFramework.SCREEN_PERIODE_ANLEGEN);
    }


    public void doPeriodenPflegenDetail() {
        screenController.setScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL);
    }
}

