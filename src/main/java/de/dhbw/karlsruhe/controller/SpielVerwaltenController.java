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
    private ComboBox<Spiel> CB_Spiele;

    private ScreenController screenController;

    private Spiel neuesSpiel;

    @FXML
    private void initialize() {
        List<Spiel> alleSpiele = SpielRepository.getAlleSpiele();
        ObservableList<Spiel> spieleListe = FXCollections.observableArrayList(alleSpiele);
        CB_Spiele.setItems(spieleListe);
        CB_Spiele.getSelectionModel().select(AktuelleSpieldaten.getSpiel());
        CB_Spiele.setConverter(new ConverterHelper().getSpieleConverter());
    }

    @FXML
    private void doSelektiertesSpielSpeichern(ActionEvent event) {
        Spiel altesSpiel = AktuelleSpieldaten.getSpiel();
        Spiel neuesSpiel = CB_Spiele.getSelectionModel().getSelectedItem();
        if (altesSpiel.getId() != neuesSpiel.getId()) { //Selektierung hat sich verändert
            altesSpiel.setIst_aktiv(Spiel.SPIEL_INAKTIV);
            neuesSpiel.setIst_aktiv(Spiel.SPIEL_AKTIV);
            SpielRepository.persistSpiel(altesSpiel);
            SpielRepository.persistSpiel(neuesSpiel);
            AktuelleSpieldaten.setSpiel(neuesSpiel);
            LogoutHelper.logout(screenController);
        }
    }

    @FXML
    private void doSelektiertesSpielLoeschen(){
        Spiel aktuellesSpiel=CB_Spiele.getValue();
        System.out.println(aktuellesSpiel);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }
}
