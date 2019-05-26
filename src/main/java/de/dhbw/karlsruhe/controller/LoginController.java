package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Berechtigungsrolle;
import de.dhbw.karlsruhe.model.CurrentUser;
import de.dhbw.karlsruhe.model.LoginRepository;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort;
    @FXML
    private Label lblFehlermeldung;

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
            CurrentUser angemeldeterUser = new CurrentUser(teilnehmer);
            //ToDo: Übersichts-Screen anzeigen
            if(angemeldeterUser.getTeilnehmer().getRolle().getId() == Long.valueOf(Berechtigungsrolle.SEMINARLEITER.ordinal())) {
                screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_UEBERSICHT);
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

    }

    @FXML
    private void doEditUser(ActionEvent event) {
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_BEARBEITEN);
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }
}

