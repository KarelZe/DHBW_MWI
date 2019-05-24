package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Model;
import de.dhbw.karlsruhe.model.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort;
    @FXML
    private Label lblFehlermeldung;

    private Model model;
    private ScreensController controller;

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
        controller.setScreen(ScreensFramework.SCREEN_REGISTER);
    }


    @FXML
    void doUnternehmenAnlegen(ActionEvent event) {
        controller.setScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN);
    }

    // Zur Erklärung https://stackoverflow.com/a/51392331
    // Zur Erklärung https://javabeginners.de/Frameworks/JavaFX/FXML.php
    @FXML
    private void initialize() {
        model = Model.getInstanz();
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }
}

