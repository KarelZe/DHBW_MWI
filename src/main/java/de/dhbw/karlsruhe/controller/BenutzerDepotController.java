package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.*;

/**
 * Controller für die Finanzübersicht des Teilnehmers
 * @author Raphael Winkler
 */
public class BenutzerDepotController implements ControlledScreen {

    private ScreenController screenController;
    private List<Portfolioposition> depotAktien;
    private List<Portfolioposition> depotAnleihen;
    private List<Portfolioposition> depotETF;
    private List<Portfolioposition> depotFestgeld;
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
        depotAktien = PortfolioFassade.getInstanz().getAktienPositionen(teilnehmerID, periodeID);
        depotAnleihen = PortfolioFassade.getInstanz().getAnleihePositionen(teilnehmerID, periodeID);
        depotETF = PortfolioFassade.getInstanz().getEtfPositionen(teilnehmerID, periodeID);
        depotFestgeld = PortfolioFassade.getInstanz().getFestgeldPositionen(teilnehmerID, periodeID);
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

        // PoC
        ArrayList<Double> renditeinPerioden = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));

        // Erste Reihe
        createGesamdepotwertRow(renditeinPerioden);
        //Zweite Reihe
        createZahlungsmittelkontoRow(perioden.size() - 1);
        //Dritte Reihe
        createFestgeldRow(renditeinPerioden);
        //Vierte Reihe
        createEtfRow(renditeinPerioden);
        //Fünfte Reihe
        createAktienGesamtRow(renditeinPerioden);
        //Sechste Reihe
        createAnleihenGesamtRow(renditeinPerioden);


    }

    /**
     * Erzeugt eine Zeile
     * @param finanzanlage Finanzanlage
     * @param saldo Saldo
     * @param rendite Rendite
     * @param renditeInPerioden Liste von Renditen (je Periode ein Eintrag)
     * @return
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
     * Erzeugt eine Zeile, die den Gesamtdepotwert angibt
     * @param renditeinPerioden Liste von Double-Objekten
     * @author Raphael Winkler
     */
    private void createGesamdepotwertRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getGesamtSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());

        table.getItems().add(createRow("Gesamtdepotwert", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    /**
     * Erzeugt eine Zeile, die den Saldo des Zahlungsmittelkontos angibt
     * @param periodenAnzahl Anzahl der Perioden
     * @author Raphael Winkler
     */
    private void createZahlungsmittelkontoRow(int periodenAnzahl) {
        ArrayList<String> renditeInPeriodenPlaceholder = new ArrayList<>();
        for (int i = 1; i <= periodenAnzahl; i++) {
            renditeInPeriodenPlaceholder.add("-");
        }
        double saldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());

        table.getItems().add(createRow("Zahlungsmittelkonto", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPeriodenPlaceholder));
    }

    /**
     * Erzeugt eine Zeile, die den Saldo des Festgeldes angibt
     * @param renditeinPerioden Liste von Double-Objekten
     * @author Raphael Winkler
     */
    private void createFestgeldRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getFestgeldSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("Festgeld", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    /**
     * Erzeugt eine Zeile, die den Saldo des ETFs angibt
     * @param renditeinPerioden Liste von Double-Objekten
     * @author Raphael Winkler
     */
    private void createEtfRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getEtfSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId());

        table.getItems().add(createRow("ETF", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    /**
     * Erzeugt eine Zeile, die den Saldo der Aktien angibt
     * @param renditeinPerioden Liste von Double-Objekten
     * @author Raphael Winkler
     */
    private void createAktienGesamtRow(ArrayList<Double> renditeinPerioden) {
        long benutzerId = AktuelleSpieldaten.getInstanz().getBenutzer().getId();
        double saldo = PortfolioFassade.getInstanz().getAktienSaldo(benutzerId);
        double rendite = PortfolioFassade.getInstanz().getRenditeAktienGesamt(benutzerId, findAktuellePeriode().getId());

        table.getItems().add(createRow("Aktien", String.format("%.2f", saldo) + " \u20AC", rendite + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    /**
     * Erzeugt eine Zeile, die den Saldo der Anleihen angibt
     * @param renditeinPerioden Liste von Double-Objekten
     * @author Raphael Winkler
     */
    private void createAnleihenGesamtRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getAnleihenSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("Anleihen", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }
}

