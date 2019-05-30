package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.JPA.Rolle;
import de.dhbw.karlsruhe.model.JPA.Spiel;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.LoginRepository;
import de.dhbw.karlsruhe.model.RolleRepository;
import de.dhbw.karlsruhe.model.SpielRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class LoginController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort;

    private ScreenController screenController;

    @FXML
    private void doLogin(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_login.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);

        Alert alert;

        Teilnehmer teilnehmer = LoginRepository.getTeilnehmerByBenutzernameAndPasswort(benutzername, passwortVerschluesselt);
        if (teilnehmer == null) { //Benutzername und/oder Passwort falsch
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Benutzername oder Passwort");
            alert.setContentText("Bitte erfassen Sie Ihren Benutzername und Ihr Passwort erneut.");
            alert.showAndWait();
        }
        else { //Login erfolgreich
            System.out.println(teilnehmer + " @ " + teilnehmer.getUnternehmen() + " $ " + teilnehmer.getRolle());
            AktuelleSpieldaten.setTeilnehmer(teilnehmer);
            //ToDo: Übersichts-Screen anzeigen
            if (AktuelleSpieldaten.getTeilnehmer().getRolle().getId() == Rolle.ROLLE_SPIELLEITER) {
                //screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);
                screenController.setScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN);

            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setContentText("Login erfolgreich.");
                alert.showAndWait();
                screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN, ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN_FILE);
                screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN);
            }
        }
    }

    @FXML
    private void doRegistration(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_REGISTER);
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
        RolleRepository.insertRollenIfNotExists(); //legt Rollen in der Datenbank an, wenn sie noch nicht existieren (z.B. bei Neuaufsetzung der Datenbank)
    }

    @FXML
    private void doEditUser(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN);
    }

    public void doWertpapierAnlegen(ActionEvent actionEvent) {
        screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    private void initializeAktuellesSpiel() {
        Spiel aktuellesSpiel = SpielRepository.getAktivesSpiel();
        if(aktuellesSpiel != null) {
            AktuelleSpieldaten.setSpiel(aktuellesSpiel);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Initialisieren");
            alert.setContentText("Es konnte kein Spiel geladen werden.");
        }
    }

}

