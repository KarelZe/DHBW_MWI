package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Model;
import de.dhbw.karlsruhe.model.Rolle;
import de.dhbw.karlsruhe.model.Teilnehmer;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class RegisterController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtVorname, txtNachname;
    @FXML
    private ComboBox<Unternehmen> cmbUnternehmen;

    private Model model;
    private ScreensController controller;

    @FXML
    private void doRegister(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_register.fxml
        String benutzername = txtBenutzername.getText();
        String passwortKlartext = txtPasswort.getText();
        String vorname = txtVorname.getText();
        String nachname = txtNachname.getText();
        Unternehmen unternehmen = cmbUnternehmen.getSelectionModel().getSelectedItem();
        System.out.println(unternehmen);
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);
        Rolle rolle = new Rolle();
        rolle.setId(1);
        Teilnehmer teilnehmer = new Teilnehmer(benutzername, passwortVerschluesselt, vorname, nachname, unternehmen, rolle);
        model.setTeilnehmer(teilnehmer);
    }

    //FIXME: Muss wahrscheinlich noch ein eigenes Model für Register benutzen ? -> Bin ich mir unsicher.
    @FXML
    private void initialize() {
        model = Model.getInstanz();

        // Initialisiere ComboBox aus Modell
        ArrayList<Unternehmen> unternehmen = model.getUnternehmen();
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);

        // FIXME: Anzeige aufhübschen z. B. https://stackoverflow.com/a/35744640
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

