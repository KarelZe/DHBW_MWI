package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class RegisterController {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtVorname, txtNachname, txtUnternehmen;
    @FXML
    private Label lblFehlermeldung;

    private LoginModel model;

    @FXML
    private void doRegister(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_register.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String vorname = txtVorname.getText();
        String nachname = txtNachname.getText();
        String  unternehmen = txtUnternehmen.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);

        try { model.setTeilnehmer(benutzername, vorname, nachname, unternehmen, passwortVerschluesselt);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Muss wahrscheinlich noch ein eigenes Model für Register benutzen ?
    @FXML
    private void initialize() {
        model = LoginModel.getInstanz();
    }
}

