package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.CurrentUser;
import de.dhbw.karlsruhe.model.LoginRepository;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort;
    @FXML
    private Label lblFehlermeldung;

    private LoginRepository loginRepository;
    private ScreenController screenController;

    @FXML
    private void doLogin(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_login.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);

        Teilnehmer teilnehmer = loginRepository.getTeilnehmerByBenutzernameAndPasswort(benutzername, passwortVerschluesselt);
        if (teilnehmer == null) { //Benutzername und/oder Passwort falsch
            lblFehlermeldung.setText("Der Benutzername oder das Passwort sind falsch.");
        }
        else { //Login erfolgreich
            System.out.println(teilnehmer + " @ " + teilnehmer.getUnternehmen() + " $ " + teilnehmer.getRolle());
            lblFehlermeldung.setText("Login erfolgreich.");
            CurrentUser angemeldeterUser = new CurrentUser(teilnehmer);
            //ToDo: Übersichts-Screen anzeigen
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

    private void doEditUser(ActionEvent event) {
        //controller.setScreen(ScreensFramework.);
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }
}

