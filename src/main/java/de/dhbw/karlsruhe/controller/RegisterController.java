package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.LoginModel;
import de.dhbw.karlsruhe.model.Rolle;
import de.dhbw.karlsruhe.model.Teilnehmer;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class RegisterController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtVorname, txtNachname, txtUnternehmen;

    private LoginModel model;
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

        Teilnehmer teilnehmer = new Teilnehmer();
        teilnehmer.setBenutzername(benutzername);
        teilnehmer.setPasswort(passwortVerschluesselt);
        teilnehmer.setVorname(vorname);
        teilnehmer.setNachname(nachname);
        Unternehmen unternehmen = new Unternehmen();
        // FIXME: Auswahl an Unternehmen msüste aus DB gelesen werden.
        unternehmen.setId(1);
        teilnehmer.setUnternehmen(unternehmen);
        Rolle rolle = new Rolle();
        rolle.setId(1);
        teilnehmer.setRolle(rolle);
        model.setTeilnehmer(teilnehmer);
    }

    //FIXME: Muss wahrscheinlich noch ein eigenes Model für Register benutzen ? -> Ja, macht Sinn :-)
    @FXML
    private void initialize() {
        model = LoginModel.getInstanz();
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

