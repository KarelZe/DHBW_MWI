package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.TeilnehmerPrintModel;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
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
    TableView<TeilnehmerPrintModel> tvDruckansicht;
    @FXML
    TableColumn<TeilnehmerPrintModel, String> tblColVorname;
    @FXML
    TableColumn<TeilnehmerPrintModel, String> tblColNachname;
    @FXML
    TableColumn<TeilnehmerPrintModel, Long> tblColId;
    @FXML
    TableColumn<TeilnehmerPrintModel, Double> tblColPortfoliowert;
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

        /*
        job.getJobSettings().setPageLayout(pageLayout);
        boolean success = job.printPage(tvDruckansicht);
        if (success) job.endJob();
        */
    }

    public void doHistorie(){
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE, ScreensFramework.SCREEN_TEILNEHMER_HISTORIE_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE);
    }
}
