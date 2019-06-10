package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.TeilnehmerPrintModel;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;


public class PrintController implements ControlledScreen {
    public GridPane grdTeilnehmer;
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
        //System.out.println(AktuelleSpieldaten.getSpiel());
        List<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findAll();
        List<TeilnehmerPrintModel> teilnehmerPrintModel = teilnehmer.stream().map(TeilnehmerPrintModel::new).collect(Collectors.toList());
        // TODO: Überdenken, ob ArrayList wirklich sinnvoll, wegen Notwendigkeit zur sortierten Ausgabe.
        ObservableList<TeilnehmerPrintModel> observableList = FXCollections.observableArrayList(teilnehmerPrintModel);
        tvDruckansicht.setItems(observableList);
    }

    /**
     * Methode ermöglicht einen Druck der Tabelle, welche die Teilnehmerergebnisse enthält.
     * Die Ausgabe erfolgt auf einem DIN-A4 Blatt, im Querformat mit einem Standard-Seitenrand.
     * Implementierung ist adaptiert von https://dzone.com/articles/introduction-example-javafx-8
     */
    public void doPrint(){
        Printer drucker = Printer.getDefaultPrinter();
        PageLayout pageLayout = drucker.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);

        // FIXME: Für Skalierung muss eine tiefe Kopie der entsprechenden Node erzeugt werden, da andernfalls die Node zur Ausgabe mit verändert wird.
        // double scaleX = tvDruckansicht.getBoundsInParent().getWidth() / pageLayout.getPrintableWidth();
        // double scaleY = tvDruckansicht.getBoundsInParent().getHeight() / pageLayout.getPrintableHeight();

        PrinterJob job = PrinterJob.createPrinterJob();
        job.getJobSettings().setPageLayout(pageLayout);
        boolean success = job.printPage(tvDruckansicht);
        if (success) job.endJob();
    }
}
