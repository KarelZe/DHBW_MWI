package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.TeilnehmerPrintModel;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;


public class PrintController implements ControlledScreen {
    @FXML
    TableView<TeilnehmerPrintModel> tvDruckansicht;
    @FXML
    TableColumn<TeilnehmerPrintModel, String> tblColVorname;
    @FXML
    TableColumn<TeilnehmerPrintModel, String> tblColNachname;
    @FXML
    TableColumn<TeilnehmerPrintModel, Long> tblColId;
    @FXML
    TableColumn<TeilnehmerPrintModel, Double> tblColPortfoliowert;

    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    /**
     * Initialize Methode von JavaFX. Siehe hierzu https://stackoverflow.com/a/51392331
     */
    @FXML
    private void initialize() {
        List<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findAll();
        List<TeilnehmerPrintModel> teilnehmerPrintModel = teilnehmer.stream().map(TeilnehmerPrintModel::new).collect(Collectors.toList());
        ObservableList<TeilnehmerPrintModel> observableList = FXCollections.observableArrayList(teilnehmerPrintModel);
        tvDruckansicht.setItems(observableList);
    }

    public void doPrint(){
        /*
        PrinterJob pJ = PrinterJob.createPrinterJob();

        if (pJ != null) {
            boolean success = pJ.showPrintDialog(screenController.getScreen("SCREEN_TEILNEHMER_DRUCKEN)").getScene().getWindow());
            if (success) {
                pJ.endJob();
            }
        }
        */
    }
}
