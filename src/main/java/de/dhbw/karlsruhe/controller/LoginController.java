package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.LoginModel;
import de.dhbw.karlsruhe.model.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController {


    @FXML
    private TextField txtBenutzername, txtPasswort;
    @FXML
    private Label lblFehlermeldung;

    private LoginModel model;

    @FXML
    private void doLogin(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_login.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);

        Teilnehmer teilnehmer = model.getTeilnehmer(benutzername, passwortVerschluesselt);
        if (teilnehmer == null) {
            System.out.println("Login falsch");
            lblFehlermeldung.setText("Falscher Benutzername oder Passwort.");
        }
        else {
            System.out.println(teilnehmer + " @ " + teilnehmer.getUnternehmen() + " $ " + teilnehmer.getRolle());
            lblFehlermeldung.setText("Login erfolgt.");
        }
    }

    @FXML
    private void doRegistration(ActionEvent event) {
        // TODO: Noch implementieren @ Bilz
        System.out.println("Regsistrieren");
    }

    // Zur Erklärung https://stackoverflow.com/a/51392331
    // Zur Erklärung https://javabeginners.de/Frameworks/JavaFX/FXML.php
    @FXML
    private void initialize() {
        model = LoginModel.getInstanz();
    }
}

