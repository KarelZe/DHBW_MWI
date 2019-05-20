package de.dhbw.karlsruhe;

import de.dhbw.karlsruhe.model.DAO.Helper.EncryptionHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.security.MessageDigest;

public class LoginController {
    @FXML private TextField txt_benutzername;
    @FXML private TextField txt_passwort;

    @FXML
    private void doLogin(ActionEvent event)
    {
        String username = txt_benutzername.getText();
        String passwortKlartext = txt_passwort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);
        //ToDo
    }


}

