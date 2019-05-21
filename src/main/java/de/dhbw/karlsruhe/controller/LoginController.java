package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.Helper.EncryptionHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class LoginController {
    @FXML private TextField txt_benutzername;
    @FXML private TextField txt_passwort;

    @FXML
    private void doLogin(ActionEvent event)
    {
        String username = txt_benutzername.getText();
        String passwortKlartext = txt_passwort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);
        System.out.println("["+ username + ","+ passwortKlartext + "," + passwortVerschluesselt+ "]");
    }


}

