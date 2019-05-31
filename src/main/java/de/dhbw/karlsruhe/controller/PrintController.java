package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.TeilnehmerPrintModel;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class PrintController implements ControlledScreen, Initializable {
    private static final String STANDARDPASSWORT = "Anika";
    public Node printNode;
    private ScreenController screenController;
    @FXML
    TableView<TeilnehmerPrintModel> TV_Teilnehmerdruckansicht;
    @FXML
    TableColumn<TeilnehmerPrintModel, String> TV_COL_Vorname;
    @FXML
    TableColumn<TeilnehmerPrintModel, String> TV_COL_Nachname;
    @FXML
    TableColumn<TeilnehmerPrintModel, Long> TV_COL_ID;
    @FXML
    TableColumn<TeilnehmerPrintModel, Double> TV_COL_Barwert;
    private ObservableList<TeilnehmerPrintModel> teilnehmerPrintModel = FXCollections.observableArrayList(getAlleTeilnehmerPrintModel());

    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TV_COL_ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TV_COL_Vorname.setCellValueFactory(new PropertyValueFactory<>("Vorname"));
        TV_COL_Nachname.setCellValueFactory(new PropertyValueFactory<>("Nachname"));
        TV_COL_Barwert.setCellValueFactory(new PropertyValueFactory<>("Barwert"));
        TV_Teilnehmerdruckansicht.setItems(teilnehmerPrintModel);
    }

    private List<TeilnehmerPrintModel> getAlleTeilnehmerPrintModel() {
        List<Teilnehmer> alleTeilnehmer = TeilnehmerRepository.getInstanz().findAll();
        List<TeilnehmerPrintModel> teilnehmerPrintModel = new ArrayList<>();
        for (Teilnehmer teilnehmer : alleTeilnehmer) {
            teilnehmerPrintModel.add(new TeilnehmerPrintModel(teilnehmer.getId(), teilnehmer.getVorname(), teilnehmer.getNachname()));
        }
        return teilnehmerPrintModel;
    }
    public void doPrint(){
       /* PrinterJob pJ = PrinterJob.createPrinterJob();

        if (pJ != null) {
            boolean success = pJ.showPrintDialog(screenController.getScreen("SCREEN_TEILNEHMER_DRUCKEN)").getScene().getWindow());
            if (success) {
                pJ.endJob();
            }
        }
        */
    }
}
