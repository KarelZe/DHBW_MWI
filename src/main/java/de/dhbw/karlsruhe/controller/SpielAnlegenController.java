package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ColorHelper;
import de.dhbw.karlsruhe.helper.ConstantsHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.*;
import de.dhbw.karlsruhe.model.JPA.Spiel;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Date;

public class SpielAnlegenController implements ControlledScreen {

    @FXML
    private TextField txtStartkapital;

    private ScreenController screenController;

    private Spiel neuesSpiel;

    @FXML
    private void doSpielAnlegen(ActionEvent event) {
        boolean isNeuanlage = false;
        this.neuesSpiel = new Spiel();
        try {
            this.neuesSpiel.setStartkapital(Double.valueOf(txtStartkapital.getText()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Spiel anlegen");
            alert.setContentText("Bitte geben Sie eine Zahl ein.");
            alert.showAndWait();
        }
        this.neuesSpiel.setErstellungsdatum(new Date());
        if (AktuelleSpieldaten.getSpiel() == null) { //kein Spiel gesetzt -> erstelltes Spiel auf aktiv setzen
            this.neuesSpiel.setIst_aktiv(Spiel.SPIEL_AKTIV);
            isNeuanlage = true;
        } else {
            this.neuesSpiel.setIst_aktiv(Spiel.SPIEL_INAKTIV);
        }
        SpielRepository.persistSpiel(this.neuesSpiel);
        AktuelleSpieldaten.setSpiel(this.neuesSpiel);
        insertSpielleiterInDB();
        insertGMAXInDB();
        if (isNeuanlage) {
            screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN, ScreensFramework.SCREEN_SPIEL_VERWALTEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
        }
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    private void insertSpielleiterInDB() {
        Teilnehmer spielleiter = new Teilnehmer();
        spielleiter.setBenutzername(ConstantsHelper.ADMIN_USERNAME);
        spielleiter.setPasswort(EncryptionHelper.getStringAsMD5(ConstantsHelper.ADMIN_PASSWORT));
        spielleiter.setVorname("Admin");
        spielleiter.setNachname("Admin");
        spielleiter.setRolle(RolleRepository.getSeminarleiterRolle());
        spielleiter.setSpiel(this.neuesSpiel);
        TeilnehmerRepository.persistTeilnehmer(spielleiter);
    }

    private void insertGMAXInDB() {
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setFarbe(ColorHelper.color2String(Color.BLACK));
        unternehmen.setName("GMAX");
        unternehmen.setIst_spielbar(Unternehmen.UNTERNEHMEN_KAPITALANLAGEGESELLSCHAFT);
        UnternehmenRepository.getInstanz().save(unternehmen);
    }
}
