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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.util.StringConverter;

import java.util.ArrayList;


public class RegisterController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtPasswortCheck, txtVorname, txtNachname;
    @FXML
    private ComboBox<Unternehmen> cmbUnternehmen;

    private Model model;
    private ScreensController controller;

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
        if(benutzername.trim().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungeeigneter Benutzername");
            alert.setContentText("Bitte geben Sie einen Benutzernamen mit mindestens einem Zeichen ein. Dies darf kein Leerzeichen sein");
            alert.showAndWait();
            return;

        }
        else if(false){ //Datenbankabfrage nach allen Benutzern einfügen
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Benutzername bereits vergeben");
            alert.setContentText("Bitte geben Sie einen anderen Benutzernamen ein.");
            alert.showAndWait();
            return;
        }

        //Name prüfen
        if((vorname.trim().length()==0)||(nachname.trim().length()==0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungeeignete Eingaben für Vor- und Nachname");
            alert.setContentText("Bitte geben Sie als Vor- und Nachnamen Namen mit mindestens einem Zeichen ein. Dies darf kein Leerzeichen sein");
            alert.showAndWait();
            return;
        }

        //Passwortlänge prüfen
        if (passwortKlartext.length()<5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort zu kurz");
            alert.setContentText("Bitte geben Sie ein Passwort mit mindestens 5 Zeichen ein.");
            alert.showAndWait();
            return;
        }

        //Passwortübereinstimmung prüfen
        if(!passwortKlartext.equals(passwortKlartextWiederholung)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort korrigieren");
            alert.setContentText("Bitte geben Sie in beiden Passwortfeldern das gleiche Passwort ein.");
            alert.showAndWait();
            return;
        }

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

        //ComboBox nur mit Namen der Unternehmen anzeigen
        StringConverter<Unternehmen> converter = new StringConverter<Unternehmen>() {
            @Override
            public String toString(Unternehmen u1) {
                //Nullpointer-Fehler
                //return u1.getName();
                return "test";
            }

            @Override
            public Unternehmen fromString(String id) {
                return null;
            }
        };

        cmbUnternehmen.setConverter(converter);

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

