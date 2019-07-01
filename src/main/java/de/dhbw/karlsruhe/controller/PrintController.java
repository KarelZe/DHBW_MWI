package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.BenutzerPrintModel;
import de.dhbw.karlsruhe.model.BenutzerRepository;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;

import java.util.List;
import java.util.stream.Collectors;


public class PrintController implements ControlledScreen {
    public GridPane grdTeilnehmer;
    @FXML
    TableView<BenutzerPrintModel> tvDruckansicht;
    @FXML
    TableColumn<BenutzerPrintModel, String> tblColVorname;
    @FXML
    TableColumn<BenutzerPrintModel, String> tblColNachname;
    @FXML
    TableColumn<BenutzerPrintModel, Long> tblColId;
    @FXML
    TableColumn<BenutzerPrintModel, Double> tblColPortfoliowert;
    @FXML
    private Button btnHistorieAnzeigen;

    private ScreenController screenController;

    @Override
    public void setScreenParent(ScreenController screenPage) {this.screenController = screenPage; }

    /**
     * Initialize Methode von JavaFX. Siehe hierzu https://stackoverflow.com/a/51392331
     */
    @FXML
    private void initialize() {
        List<Benutzer> benutzer = BenutzerRepository.getInstanz().findAll();
        List<BenutzerPrintModel> benutzerPrintModel = benutzer.stream().map(BenutzerPrintModel::new).collect(Collectors.toList());
        // TODO: Überdenken, ob ArrayList wirklich sinnvoll, wegen Notwendigkeit zur sortierten Ausgabe.
        ObservableList<BenutzerPrintModel> observableList = FXCollections.observableArrayList(benutzerPrintModel);
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
        // Wird Trotzdem noch verändert

        Node node = tvDruckansicht;
        double scaleX = node.getBoundsInParent().getWidth() / pageLayout.getPrintableWidth();
        double scaleY = node.getBoundsInParent().getHeight() / pageLayout.getPrintableHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(node.getScene().getWindow())){
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    public void doHistorie(){
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE, ScreensFramework.SCREEN_TEILNEHMER_HISTORIE_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE);
    }

    public void showHistoriewithID(long id) {
        TeilnehmerHistorieController.teilnehmerID =id;
        doHistorie();
    }
}
