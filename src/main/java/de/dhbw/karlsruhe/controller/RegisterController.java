package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Model;
import de.dhbw.karlsruhe.model.Rolle;
import de.dhbw.karlsruhe.model.Teilnehmer;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class RegisterController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtVorname, txtNachname, txtUnternehmen;

    private Model model;
    private ScreensController controller;

    @FXML
    private void doRegister(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_register.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String vorname = txtVorname.getText();
        String nachname = txtNachname.getText();
        // FIXME: Auswahl an Unternehmen müsste aus DB gelesen werden. Feldtyp sollte ein Dropdown sein.
        String unternehmenId = txtUnternehmen.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);
        Unternehmen unternehmen = new Unternehmen();
        // FIXME: Auswahl an Unternehmen msüste aus DB gelesen werden.
        unternehmen.setId(1);
        Rolle rolle = new Rolle();
        rolle.setId(1);
        Teilnehmer teilnehmer = new Teilnehmer(benutzername, passwortVerschluesselt, vorname, nachname, unternehmen, rolle);
        model.setTeilnehmer(teilnehmer);
    }

    //FIXME: Muss wahrscheinlich noch ein eigenes Model für Register benutzen ? -> Bin ich mir unsicher.
    @FXML
    private void initialize() {
        model = Model.getInstanz();
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @FXML
    private void doLogin(ActionEvent event) {
        controller.setScreen(ScreensFramework.SCREEN_LOGIN);
    }

}

