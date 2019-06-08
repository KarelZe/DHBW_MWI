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
import javafx.scene.control.Alert;
import java.util.Optional;
import java.util.List;
import javafx.scene.control.ButtonType;

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

    //ToDo: Nach Löschvorgang aktivies Spiel automatisch neu setzen?
    @FXML
    private void doSelektiertesSpielLoeschen() {
        Spiel zuLoeschendesSpiel=cmbSpiele.getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Spiel löschen?");
        alert.setContentText("Soll das Spiel mit der ID "+zuLoeschendesSpiel.getId()+" unwiderruflich gelöscht werden?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            SpielRepository.deleteSpiel(zuLoeschendesSpiel);

            List<Spiel> alleSpiele = SpielRepository.getAlleSpiele();
            ObservableList<Spiel> spieleListe = FXCollections.observableArrayList(alleSpiele);
            cmbSpiele.setItems(spieleListe);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spiel löschen erfolgreich");
            alert.setContentText("Das Spiel mit der ID "+zuLoeschendesSpiel.getId()+" wurde gelöscht.");
            alert.showAndWait();
        }

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

}
