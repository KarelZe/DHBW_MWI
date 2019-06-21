package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WertpapierKaufenController implements ControlledScreen {


    private ScreenController screenController;

    @FXML
    private ListView lstVwWertpapiere;
    @FXML
    private Label lblAuswahl;
    @FXML
    private ComboBox<Wertpapier> cbWertpapierAuswahl;
    @FXML
    private Label lbAnzahl;
    @FXML
    private TextField txtAnzahl;
    @FXML
    private Label lblKosten;
    @FXML
    private Label lblKostenDisplay;
    @FXML
    private Button btnKaufen;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void initialize() {
        ArrayList<Wertpapier> wertpapiere = WertpapierRepository.getInstanz().findAll().stream()
                .filter(w -> w.getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_STARTKAPITAL).collect(Collectors.toCollection(ArrayList::new));
        ObservableList<Wertpapier> wertpapiereComboBox = FXCollections.observableArrayList(wertpapiere);
        cbWertpapierAuswahl.setItems(wertpapiereComboBox);
        cbWertpapierAuswahl.setConverter(new ConverterHelper().getWertpapierConverter());
    }

    @FXML
    private void doWertpapierKaufen(ActionEvent event) {


    }

    private void validateOrder() {

    }


}
