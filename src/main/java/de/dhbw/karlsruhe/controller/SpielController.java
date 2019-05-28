package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConstantsHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.JPA.Rolle;
import de.dhbw.karlsruhe.model.JPA.Spiel;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.RolleRepository;
import de.dhbw.karlsruhe.model.SpielRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Date;

public class SpielController implements ControlledScreen {

    @FXML
    private TextField txtStartkapital;

    private ScreenController screenController;

    @FXML
    private void spielAnlegen(ActionEvent event) {
        if (AktuelleSpieldaten.getTeilnehmer().getRolle().getId() == Rolle.ROLLE_SPIELLEITER) {
            Spiel spiel = new Spiel();
            try {
                spiel.setStartkapital(Double.valueOf(txtStartkapital.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Spiel anlegen");
                alert.setContentText("Bitte geben Sie eine Zahl ein.");
                alert.showAndWait();
            }
            spiel.setErstellungsdatum(new Date());
            spiel.setIst_aktiv(Spiel.SPIEL_INAKTIV);
            SpielRepository.persistSpiel(spiel);
        }
        else {
            //ToDo: Logout
        }
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    private void insertSpielleiter() {
        Teilnehmer spielleiter = new Teilnehmer();
        spielleiter.setBenutzername(ConstantsHelper.ADMIN_USERNAME);
        spielleiter.setPasswort(EncryptionHelper.getStringAsMD5(ConstantsHelper.ADMIN_PASSWORT));
        spielleiter.setVorname("Admin");
        spielleiter.setNachname("Admin");
        spielleiter.setRolle(RolleRepository.getSeminarleiterRolle());
    }
}
