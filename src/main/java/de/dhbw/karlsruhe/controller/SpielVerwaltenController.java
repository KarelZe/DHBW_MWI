package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.helper.LogoutHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.SpielRepository;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class SpielVerwaltenController implements ControlledScreen {

    @FXML
    private TextField txtStartkapital;

    @FXML
    private ComboBox<Spiel> cmbSpiele;

    private ScreenController screenController;

    private Spiel neuesSpiel;

    @FXML
    private void initialize() {
        List<Spiel> alleSpiele = SpielRepository.getAlleSpiele();
        ObservableList<Spiel> spieleListe = FXCollections.observableArrayList(alleSpiele);
        cmbSpiele.setItems(spieleListe);
        cmbSpiele.getSelectionModel().select(AktuelleSpieldaten.getSpiel());
        cmbSpiele.setConverter(new ConverterHelper().getSpieleConverter());
    }

    @FXML
    private void doSelektiertesSpielSpeichern(ActionEvent event) {
        Spiel altesSpiel = AktuelleSpieldaten.getSpiel();
        Spiel neuesSpiel = cmbSpiele.getSelectionModel().getSelectedItem();
        if (altesSpiel.getId() != neuesSpiel.getId()) { //Selektierung hat sich verändert
            altesSpiel.setIst_aktiv(Spiel.SPIEL_INAKTIV);
            neuesSpiel.setIst_aktiv(Spiel.SPIEL_AKTIV);
            SpielRepository.persistSpiel(altesSpiel);
            SpielRepository.persistSpiel(neuesSpiel);
            AktuelleSpieldaten.setSpiel(neuesSpiel);
            LogoutHelper.logout(screenController);
        }
    }

    // TODO: Implementierung noch vorzunehmen
    @FXML
    private void doSelektiertesSpielLoeschen() throws UnsupportedOperationException{
        try {
            SpielRepository.deleteSpiel(cmbSpiele.getValue());
        }

        catch (Exception e) {
            // Fehlerbehandlung
        }


    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

}
