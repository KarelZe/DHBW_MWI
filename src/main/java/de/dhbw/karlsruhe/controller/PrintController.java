package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.BenutzerPrintModel;
import de.dhbw.karlsruhe.model.BenutzerRepository;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 *Klasse ist Kontroller der Druckansicht
 * Der Nutzer kann hier den angezeigten Inhalt über den Windows dialog drucken oder
 * Der Nutzer kann hier den Inhalt der Tabelle als Excel aud den Desktop exportieren
 * @author Jan Carlos Riecken
 */
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
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

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
    public void doPrint() {
        Printer drucker = Printer.getDefaultPrinter();
        PageLayout pageLayout = drucker.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);

        // FIXME: Für Skalierung muss eine tiefe Kopie der entsprechenden Node erzeugt werden, da andernfalls die Node zur Ausgabe mit verändert wird.
        // Wird Trotzdem noch verändert

        Node node = tvDruckansicht;

        /**
         Alle Veränderungen an der Node führen auch dazu, dass die node in der Benutzeroberfläche verändert wird
         *//*
        double scaleX = node.getBoundsInParent().getWidth() / pageLayout.getPrintableWidth();
        double scaleY =  node.getBoundsInParent().getHeight() / pageLayout.getPrintableHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));
        ode.getStyleClass().clear();
         */
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
        node.getTransforms().clear();
    }

    public void doHistorie() {
        screenController.loadScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE, ScreensFramework.SCREEN_TEILNEHMER_HISTORIE_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_TEILNEHMER_HISTORIE);
    }
    /**
     *Methode ist mit Button verknüpft und führt createCSV Methode aus
     * @author Jan Carlos Riecken
     */
    public void doCSV(){
        try {
            createCSV();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Excel Datei erstellt");
            alert.setContentText("Die Datei befindet sich auf dem Desktop");
            alert.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showHistoriewithID(long id) {
        TeilnehmerHistorieController.teilnehmerID = id;
        doHistorie();
    }

    /**
     *Erstellt eine CSV Datei aus einer Arraylist von Benutzerprintmodel und speichert diese auf dem Desktop
     * @author Jan Carlos Riecken
     */
    public void createCSV() throws IOException {
        List<Benutzer> benutzer = BenutzerRepository.getInstanz().findAll();
        List<BenutzerPrintModel> benutzerPrintModel = benutzer.stream().map(BenutzerPrintModel::new).collect(Collectors.toList());
        FileWriter writer;
        try {
            writer = new FileWriter(System.getProperty("user.home") + "/Desktop"+"/Bestenliste.csv"); //Speichert immer auf dem Desktop (Achtung bei der verwendung von Onedrive)
        }catch (FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Auf Bestenlisete.csv kann nicht zugegriffen werden");
            alert.setContentText("Bitte schlie\u00dfen sie die Datei Bestenliste.csv");
            alert.showAndWait();
            try {
                createCSV();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return;
        }

        List<String> row = new ArrayList<>();

        //Header
        row.add("Vorname");
        row.add("Nachname");
        row.add("Portfoliowert");
        String header = row.stream().collect(Collectors.joining(";")); //Delimiter für deutsches CSV format ist ; und nicht ,
        writer.write(header);
        writer.write("\n"); // newline


        for(BenutzerPrintModel s : benutzerPrintModel) {
            row.clear();
            row.add(s.getVorname());
            row.add(s.getNachname());
            row.add(String.valueOf(s.getPortfoliowert()));
            String collect = row.stream().collect(Collectors.joining(";")); //Delimiter für deutsches CSV format ist ; und nicht ,
            writer.write(collect);
            System.out.println(collect);
            writer.write("\n"); // newline
        }
        writer.close();
    }
}