package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.CrudRepository;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Ermöglicht das Bearbeiten eines bereits registrierten und eingeloggten Teilnehmers durch den Teilnehmer selbst
 * @author Max Schwaab
 */

public class TeilnehmerBearbeitenController implements ControlledScreen {

    @FXML
    private TextField vornameFeld, nachnameFeld, passwortFeld, passwortBestaetigenFeld;

    @FXML
    private ComboBox<Unternehmen> unternehmenComboBox;

    private Teilnehmer teilnehmer;

    private CrudRepository<Unternehmen> model;


    /**
     * Aktualisiert die Daten eines Teilnehmers in der Datenbank
     * Vorraussetzung: Bestimmte Kriterien sind erfüllt
     * @param event Event des aufrufenden Buttons
     */
    @FXML
    private void aktualisieren(ActionEvent event) {

        System.out.println(teilnehmer);

        String vorname = vornameFeld.getText().trim();
        String nachname = nachnameFeld.getText().trim();
        String passwort1 = passwortFeld.getText();
        String passwort2 = passwortBestaetigenFeld.getText();

        // TODO: ist redundant zur Registrierugn?
        //Name prüfen
        if ((vorname.length() == 0) || (nachname.length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Ungeeignete Eingaben für Vor- und Nachname");
            alert.setContentText("Bitte geben Sie als Vor- und Nachnamen Namen mit mindestens einem Zeichen ein. Dies darf kein Leerzeichen sein");
            alert.showAndWait();
            return;
        }

        if ((passwort1.length() > 0) || (passwort2.length() > 0)) {

            //Passwortlänge prüfen
            if (passwort1.length() < 5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Passwort zu kurz");
                alert.setContentText("Bitte geben Sie ein Passwort mit mindestens 5 Zeichen ein.");
                alert.showAndWait();
                return;
            }

            //Passwortübereinstimmung prüfen
            if (!passwort1.equals(passwort2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Passwort korrigieren");
                alert.setContentText("Bitte geben Sie in beiden Passwortfeldern das gleiche Passwort ein.");
                alert.showAndWait();
                return;
            }

            teilnehmer.setPasswort(EncryptionHelper.getStringAsMD5(passwort1));
        }

        if (unternehmenComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Unternehmen auswählen");
            alert.setContentText("Bitte wählen Sie ihr zugehöriges Unternehmen aus.");
            alert.showAndWait();
            return;
        }

        teilnehmer.setVorname(vorname);
        teilnehmer.setNachname(nachname);
        teilnehmer.setBenutzername(vorname + "." + nachname);
        teilnehmer.setUnternehmen(unternehmenComboBox.getValue());

        TeilnehmerRepository.getInstanz().save(teilnehmer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aktualisieren erfolgreich");
        alert.setContentText("Die Aktualisierung war erfolgreich. Ihr (neuer) Benutzername lautet: " + "\"" + teilnehmer.getBenutzername() + "\"");
        alert.showAndWait();

        //ToDo: Aktueller Teilnehmer neu setzen?

        System.out.println(teilnehmer);

    }

    /**
     * Initialisiert die Bearbeitung
     */
    @FXML
    private void initialize() {
        teilnehmer = AktuelleSpieldaten.getInstanz().getTeilnehmer();
        vornameFeld.setText(teilnehmer.getVorname());
        nachnameFeld.setText(teilnehmer.getNachname());
        ArrayList<Unternehmen> unternehmen = new ArrayList<>(UnternehmenRepository.getInstanz().findByUnternehmenArt(Unternehmen.UNTERNEHMEN_TEILNEHMER));
        ObservableList<Unternehmen> unternehmenList = FXCollections.observableArrayList(unternehmen);
        unternehmenComboBox.setItems(unternehmenList);
        unternehmenComboBox.setValue(teilnehmer.getUnternehmen());
        unternehmenComboBox.setConverter(new ConverterHelper().getUnternehmensConverter());
    }

    /**
     * Setzt den screenController
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
    }
}