package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Optional;


public class RegisterController implements ControlledScreen {


    @FXML
    private TextField txtBenutzername, txtPasswort, txtPasswortCheck, txtVorname, txtNachname;
    @FXML
    private ComboBox<Unternehmen> cmbUnternehmen;

    private ScreenController controller;

    @FXML
    private void doRegister(ActionEvent event) {
        // Lese Daten aus Textfeldern der scene_register.fxml
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
        rolle.setId(Rolle.ROLLE_TEILNEHMER);

        // Länge des Benutzernamens prüfen
        if (benutzername.trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Benutzername");
            alert.setContentText("Bitte geben Sie einen Benutzername ein.");
            alert.showAndWait();
            return;
        }

        // Eindeutigkeit des Benutzernamens prüfen
        Optional<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findByBenutzername(benutzername);
        teilnehmer.ifPresent(t -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Dieser Benutzername existiert bereits. Bitte wenden Sie sich an den Seminarleiter.");
                    alert.setTitle("Benutzername");
                    alert.setContentText("Dieser Benutzername existiert bereits. Bitte wenden Sie sich an den Seminarleiter.");
                    alert.showAndWait();
                    return;
                }
        );


        //Zuordnung zu Unternehmen prüfen
        System.out.println("U: " + unternehmen);
        if (unternehmen == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unternehmensauswahl");
            alert.setContentText("Bitte legen Sie Ihr Unternehmen wie im Planspiel fest.");
            alert.showAndWait();
            return;
        }


        //Name prüfen
        if ((vorname.trim().length() == 0) || (nachname.trim().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vor- und Nachname");
            alert.setContentText("Bitte geben Sie einen Vor- und Nachname ein.");
            alert.showAndWait();
            return;
        }

        //Passwortübereinstimmung prüfen
        if (!passwortKlartext.equals(passwortKlartextWiederholung)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort");
            alert.setContentText("Bitte geben Sie in beiden Passwortfeldern das gleiche Passwort ein.");
            alert.showAndWait();
            return;
        }

        //ToDo: Unterstützung für Umlaute einbauen
        //Passwortlänge prüfen
        if (passwortKlartext.length() < 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort");
            alert.setContentText("Bitte geben Sie ein Passwort mit mindestens fünf Zeichen ein.");
            alert.showAndWait();
            return;
        }

        Teilnehmer teilnehmerZurSpeicherung = new Teilnehmer(benutzername, passwortVerschluesselt, vorname, nachname, unternehmen, rolle, AktuelleSpieldaten.getSpiel());
        TeilnehmerRepository.getInstanz().save(teilnehmerZurSpeicherung);

        //Teilnehmer über erfolgreiche Registrierung informieren
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erfolgreich registiriert");
        alert.setContentText("Sie wurden erfolgreich mit dem Benutzernamen "+"\""+teilnehmerZurSpeicherung.getBenutzername()+"\""+" registriert!");
        alert.showAndWait();

        //Registrierung abschließen und zu Login wechseln
        controller.setScreen(ScreensFramework.SCREEN_LOGIN);

    }

    @FXML
    private void initialize() {
        // Initialisiere ComboBox aus Modell
        ArrayList<Unternehmen> unternehmen = new ArrayList<>(UnternehmenRepository.getInstanz().findAllSpielbar());
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);

        //Den Benutzernamen aus Vor- und Nachnamen generieren und in entsprechendes Feld einfügen
        txtBenutzername.textProperty().bind(Bindings.concat(txtVorname.textProperty(), ".", txtNachname.textProperty()));

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
