package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.BenutzerRepository;
import de.dhbw.karlsruhe.model.SpielRepository;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Spiel;
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

    /**
     * Eventhandler für den Login-Button
     * @param event
     */
    @FXML
    private void doLogin(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_login.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);

        Optional<Benutzer> teilnehmer = BenutzerRepository.getInstanz().findByBenutzernameAndPasswort(benutzername, passwortVerschluesselt);
        teilnehmer.ifPresentOrElse(t -> {
                    System.out.println(t + " @ " + t.getUnternehmen() + " $ " + t.getRolle());
                    AktuelleSpieldaten.getInstanz().setBenutzer(t);
                    //ToDo: Übersichts-Screen anzeigen
                    // TODO: Ersetzen durch Filter oder Map? https://www.callicoder.com/java-8-optional-tutorial/
                    if (AktuelleSpieldaten.getInstanz().getBenutzer().getRolle().getId() == Rolle.ROLLE_SPIELLEITER) {
                        //screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);
                       screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT, ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT_FILE);
                       screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);

                    } else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
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
                    alert.setHeaderText(null);
                    alert.setTitle("Benutzername oder Passwort");
                    alert.setContentText("Bitte erfassen Sie Ihren Benutzername und Ihr Passwort erneut.");
                    alert.showAndWait();
                });
    }

    // Zur Erklärung https://stackoverflow.com/a/51392331
    // Zur Erklärung https://javabeginners.de/Frameworks/JavaFX/FXML.php

    /**
     * Initialisierung des Logins
     */
    @FXML
    private void initialize() {
        initializeAktuellesSpiel();
    }

    /**
     * Konkrete Implementierung für den Zugriff auf den Controller des übergeordneten Screens
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    /**
     * Initialisiert das aktuelle Spiel
     */
    private void initializeAktuellesSpiel() {
        Spiel aktuellesSpiel = SpielRepository.getInstanz().getAktivesSpiel();
        if (aktuellesSpiel != null) {
            AktuelleSpieldaten.getInstanz().setSpiel(aktuellesSpiel);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Initialisieren");
            alert.setContentText("Es konnte kein Spiel geladen werden.");
        }
    }
}

