package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.SpielRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;


/**
 * Controller für die Finanzübersicht des Teilnehmers
 * @author Raphael Winkler
 */
public class BenutzerDepotController implements ControlledScreen {

    private ScreenController screenController;
    private ArrayList<Periode> perioden;
    @FXML
    private TableView<ObservableList<String>> table;

    /**
     * Methode für den Zugriff auf den {@code ScreenController} des übergeordneten Screens.
     *
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    /**
     * Gibt die aktulle Periode als Objekt zurück
     * @return Periode als Objekt
     * @throws NoSuchElementException
     * @author Raphael Winkler
     */
    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

    /**
     * Initialisierung
     */
    @FXML
    private void initialize() {
        long teilnehmerID = AktuelleSpieldaten.getInstanz().getBenutzer().getId();
        long periodeID = findAktuellePeriode().getId();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(false);
        populateTable();

    }

    /**
     * Befüllt die Tabelle mit Werten
     * @author Raphael Winkler
     */
    private void populateTable() {
        // Create Columns
        perioden = new ArrayList<>(PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()));
        for (int i = 0; i < (3 + (perioden.size() - 1)); i++) {
            TableColumn<ObservableList<String>, String> column;
            final int finalIdx = i;
            if (i == 0) {
                column = new TableColumn<>("Finanzanlage"); // erste Column
            } else if (i == 1) {
                column = new TableColumn<>("Saldo"); // zweite Column
            } else if (i == 2) {
                column = new TableColumn<>("Rendite"); // dritte column
            } else
                column = new TableColumn<>("Rendite in Periode " + (i - 2)); // für jede Periode eine Column
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            table.getColumns().add(column);
        }


        // Erste Reihe
        createGesamdepotwertRow(createRenditeInPeriodenPlaceholder());
        //Zweite Reihe
        createZahlungsmittelkontoRow(createRenditeInPeriodenPlaceholder());
        //Dritte Reihe
        createFestgeldRow(createRenditeInPeriodenPlaceholder());
        //Vierte Reihe
        createEtfRow(createRenditeInPeriodenPlaceholder());
        //Fünfte Reihe
        createAktienGesamtRow(createRenditeInPeriodenPlaceholder());
        //Sechste Reihe
        createAnleihenGesamtRow(createRenditeInPeriodenPlaceholder());


    }

    /**
     * Diese Methode erzeugt eine Reihe in der Tabelle
     *
     * @param finanzanlage      String der Finanzanlagebezeichnung
     * @param saldo             Saldo der Finanzanlage
     * @param rendite           Rendite der Finanzanlage in aktueller Periode
     * @param renditeInPerioden Renditen der Finanzanlage in den vergangenen Perioden inkl. aktueller Periode
     * @return Reihe für die TableView
     * @author Raphael Winkler
     */
    private ObservableList<String> createRow(String finanzanlage, String saldo, String rendite, ArrayList<String> renditeInPerioden) {
        ObservableList<String> row = FXCollections.observableList(new ArrayList<>(Arrays.asList(finanzanlage, saldo, rendite)));
        row.addAll(renditeInPerioden);
        return row;
    }

    /**
     * Castet eine Liste von Double-Objekten in eine Liste von String-Objekten (inkl. %)
     * @param doubles Liste von Double-Objekten
     * @return Liste von String-Objekten
     * @author Raphael Winkler
     */
    private ArrayList<String> castDoubleListToStringList(ArrayList<Double> doubles) {
        ArrayList<String> strings = new ArrayList<>();
        for (Double d : doubles) {
            strings.add(d + " %");
        }
        return strings;
    }

    /**
     * Diese Methode erzeugt die Reihe des Gesamtdepots
     *
     * @param renditeInPerioden Renditen der Finanzanlage in den vergangenen Perioden inkl. aktueller Periode
     * @author Raphael Winkler
     */
    private void createGesamdepotwertRow(ArrayList<String> renditeInPerioden) {
        double startkapital = SpielRepository.getInstanz().getAktivesSpiel().getStartkapital();
        double saldo = PortfolioFassade.getInstanz().getGesamtSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId());
        double rendite = (saldo - startkapital) / startkapital * 100;

        table.getItems().add(createRow("Gesamtdepotwert", String.format("%.2f", saldo) + " \u20AC", String.format("%.2f", rendite) + "\u0025", renditeInPerioden));
    }

    /**
     * Diese Methode erzeugt die Reihe des Zahlungsmittelkontos
     * @author Raphael Winkler
     */
    private void createZahlungsmittelkontoRow(ArrayList<String> renditeInPerioden) {
        double saldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("Zahlungsmittelkonto", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPerioden));
    }

    /**
     * Diese Methode erzeugt die Reihe des Festgeldbestands
     * @param renditeInPerioden Renditen der Finanzanlage in den vergangenen Perioden inkl. aktueller Periode
     * @author Raphael Winkler
     */
    private void createFestgeldRow(ArrayList<String> renditeInPerioden) {
        double saldo = PortfolioFassade.getInstanz().getFestgeldSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("Festgeld", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPerioden));
    }

    /**
     * Diese Methode erzeugt die Reihe des ETF-Bestands
     * @param renditeInPerioden Renditen der Finanzanlage in den vergangenen Perioden inkl. aktueller Periode
     * @author Raphael Winkler
     */
    private void createEtfRow(ArrayList<String> renditeInPerioden) {
        double saldo = PortfolioFassade.getInstanz().getEtfSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId());
        table.getItems().add(createRow("ETF", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPerioden));
    }

    /**
     * Diese Methode erzeugt die Reihe des Aktien-Bestands
     * @param renditeInPerioden Renditen der Finanzanlage in den vergangenen Perioden inkl. aktueller Periode
     * @author Raphael Winkler
     */
    private void createAktienGesamtRow(ArrayList<String> renditeInPerioden) {
        long benutzerId = AktuelleSpieldaten.getInstanz().getBenutzer().getId();
        double saldo = PortfolioFassade.getInstanz().getAktienSaldo(benutzerId, findAktuellePeriode().getId());
        table.getItems().add(createRow("Aktien", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPerioden));
    }

    /**
     * Diese Methode erzeugt die Reihe des Anleihen-Bestands
     * @param renditeInPerioden Renditen der Finanzanlage in den vergangenen Perioden inkl. aktueller Periode
     * @author Raphael Winkler
     */
    private void createAnleihenGesamtRow(ArrayList<String> renditeInPerioden) {
        double saldo = PortfolioFassade.getInstanz().getAnleihenSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId());
        table.getItems().add(createRow("Anleihen", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPerioden));
    }

    /**
     * Diese Methode erzeugt Platzhalter für die Rendite der Vergangenen Perioden
     *
     * @return Platzhalter für RenditeInPerioden
     * @author Raphael Winkler
     */
    private ArrayList<String> createRenditeInPeriodenPlaceholder() {
        ArrayList<String> renditeInPeriodenPlaceholder = new ArrayList<>();
        for (int i = 1; i <= perioden.size() - 1; i++) {
            renditeInPeriodenPlaceholder.add("-");
        }
        return renditeInPeriodenPlaceholder;
    }

}

