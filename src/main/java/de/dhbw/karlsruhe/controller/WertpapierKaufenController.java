package de.dhbw.karlsruhe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class WertpapierKaufenController implements ControlledScreen {


    private ScreenController screenController;

    @FXML
    private ListView lstVwWertpapiere;
    @FXML
    private Label lblAuswahl;
    @FXML
    private ComboBox cbWertpapierAuswahl;
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

    private void doWertpapierKaufen(ActionEvent event) {

    }


}
