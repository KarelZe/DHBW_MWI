package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.JPA.Rolle;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.util.StringConverter;

import javax.persistence.Convert;
import java.util.ArrayList;


public class RegisterController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtPasswortCheck, txtVorname, txtNachname;
    @FXML
    private ComboBox<Unternehmen> cmbUnternehmen;

    private ScreenController controller;

    @FXML
    private void doRegister(ActionEvent event) {
        // lese Daten aus Textfeldern der scene_register.fxml
        String benutzername = txtBenutzername.getText().trim();
        String passwortKlartext = txtPasswort.getText();
        String passwortKlartextWiederholung = txtPasswortCheck.getText();
        String vorname = txtVorname.getText().trim();
        String nachname = txtNachname.getText().trim();
        Unternehmen unternehmen = cmbUnternehmen.getSelectionModel().getSelectedItem();
        System.out.println(unternehmen);
        System.out.println(vorname);
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);
        Rolle rolle = new Rolle();
        rolle.setId(1);

        //Benutzername prüfen
        if (benutzername.trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungeeigneter Benutzername");
            alert.setContentText("Bitte geben Sie einen Benutzernamen mit mindestens einem Zeichen ein. Dies darf kein Leerzeichen sein");
            alert.showAndWait();
            return;

        } else if (false) { //Datenbankabfrage nach allen Benutzern einfügen
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Benutzername bereits vergeben");
            alert.setContentText("Bitte geben Sie einen anderen Benutzernamen ein.");
            alert.showAndWait();
            return;
        }

        //Name prüfen
        if ((vorname.trim().length() == 0) || (nachname.trim().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungeeignete Eingaben für Vor- und Nachname");
            alert.setContentText("Bitte geben Sie als Vor- und Nachnamen Namen mit mindestens einem Zeichen ein. Dies darf kein Leerzeichen sein");
            alert.showAndWait();
            return;
        }

        //Passwortlänge prüfen
        if (passwortKlartext.length() < 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort zu kurz");
            alert.setContentText("Bitte geben Sie ein Passwort mit mindestens 5 Zeichen ein.");
            alert.showAndWait();
            return;
        }

        //Passwortübereinstimmung prüfen
        if (!passwortKlartext.equals(passwortKlartextWiederholung)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort korrigieren");
            alert.setContentText("Bitte geben Sie in beiden Passwortfeldern das gleiche Passwort ein.");
            alert.showAndWait();
            return;
        }

        Teilnehmer teilnehmer = new Teilnehmer(benutzername, passwortVerschluesselt, vorname, nachname, unternehmen, rolle);
        TeilnehmerRepository.persistTeilnehmer(teilnehmer);

    }

    //FIXME: Muss wahrscheinlich noch ein eigenes Model für Register benutzen ? -> Bin ich mir unsicher.
    @FXML
    private void initialize() {
        // Initialisiere ComboBox aus Modell
        ArrayList<Unternehmen> unternehmen = UnternehmenRepository.getAlleUnternehmen();
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);

        // Zeige Combobox nur mit Unternehmensnamen an.
        cmbUnternehmen.setConverter(new ConverterHelper().getUnternehmensConverter());
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
        controller = screenPage;
    }

    @FXML
    private void doLogin(ActionEvent event) {
        controller.setScreen(ScreensFramework.SCREEN_LOGIN);
    }

}
