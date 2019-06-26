package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.SpielRepository;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;

public class SpielVerwaltenController implements ControlledScreen {

    @FXML
    private TextField txtStartkapital;

    @FXML
    private ComboBox<Spiel> cmbSpiele;

    private ScreenController screenController;

    private Spiel neuesSpiel;

    private SpielRepository spielRepository = SpielRepository.getInstanz();

    @FXML
    private void initialize() {
        List<Spiel> alleSpiele = spielRepository.findAll();
        ObservableList<Spiel> spieleListe = FXCollections.observableArrayList(alleSpiele);
        cmbSpiele.setItems(spieleListe);
        cmbSpiele.getSelectionModel().select(AktuelleSpieldaten.getInstanz().getInstanz().getSpiel());
        cmbSpiele.setConverter(new ConverterHelper().getSpieleConverter());
    }

    @FXML
    private void doSelektiertesSpielSpeichern(ActionEvent event) {
        Spiel altesSpiel = AktuelleSpieldaten.getInstanz().getSpiel();
        Spiel neuesSpiel = cmbSpiele.getSelectionModel().getSelectedItem();
        if (altesSpiel.getId() != neuesSpiel.getId()) { //Selektierung hat sich verändert
            altesSpiel.setIst_aktiv(Spiel.SPIEL_INAKTIV);
            neuesSpiel.setIst_aktiv(Spiel.SPIEL_AKTIV);
            spielRepository.save(altesSpiel);
            spielRepository.save(neuesSpiel);
            AktuelleSpieldaten.getInstanz().setSpiel(neuesSpiel);
            AktuelleSpieldaten.getInstanz().setTeilnehmer(null);
            screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
        }
    }

    //ToDo: Nach Löschvorgang aktivies Spiel automatisch neu setzen?
    @FXML
    private void doSelektiertesSpielLoeschen() {
        Spiel zuLoeschendesSpiel=cmbSpiele.getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Spiel l\u00F6schen?");
        alert.setContentText("Soll das Spiel mit der ID " + zuLoeschendesSpiel.getId() + " unwiderruflich gel\u00F6scht werden?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            if(zuLoeschendesSpiel.getIst_aktiv() == Spiel.SPIEL_AKTIV) { //zu löschendes Spiel ist aktiv
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Spiel l\u00F6schen nicht m\u00F6glich");
                alert.setContentText("Es kann kein aktives Spiel gel\u00F6scht werden.");
                alert.showAndWait();
                return;
            }
            SpielRepository.deleteSpiel(zuLoeschendesSpiel);

            List<Spiel> alleSpiele = spielRepository.findAll();
            ObservableList<Spiel> spieleListe = FXCollections.observableArrayList(alleSpiele);
            cmbSpiele.setItems(spieleListe);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spiel l\u00F6schen erfolgreich");
            alert.setContentText("Das Spiel mit der ID " + zuLoeschendesSpiel.getId() + " wurde gel\u00F6scht.");
            alert.showAndWait();

            screenController.loadScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN, ScreensFramework.SCREEN_SPIEL_VERWALTEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_SPIEL_VERWALTEN);
        }

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

}
