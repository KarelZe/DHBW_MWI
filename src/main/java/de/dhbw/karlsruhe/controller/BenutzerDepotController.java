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

public class BenutzerDepotController implements ControlledScreen {

    private ScreenController screenController;
    private List<Portfolioposition> depotAktien;
    private List<Portfolioposition> depotAnleihen;
    private List<Portfolioposition> depotETF;
    private List<Portfolioposition> depotFestgeld;
    private ArrayList<Periode> perioden;
    @FXML
    private TableView<ObservableList<String>> table;



    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

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

    private ObservableList<String> createRow(String finanzanlage, String saldo, String rendite, ArrayList<String> renditeInPerioden) {
        ObservableList<String> row = FXCollections.observableList(new ArrayList<>(Arrays.asList(finanzanlage, saldo, rendite)));
        row.addAll(renditeInPerioden);
        return row;
    }

    private ArrayList<String> castDoubleListToStringList(ArrayList<Double> doubles) {
        ArrayList<String> strings = new ArrayList<>();
        for (Double d : doubles) {
            strings.add(d + " %");
        }
        return strings;
    }

    private void createGesamdepotwertRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getGesamtSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());


        table.getItems().add(createRow("Gesamtdepotwert", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));

    }

    private void createZahlungsmittelkontoRow(int periodenAnzahl) {
        ArrayList<String> renditeInPeriodenPlaceholder = new ArrayList<>();
        for (int i = 1; i <= periodenAnzahl; i++) {
            renditeInPeriodenPlaceholder.add("-");
        }
        double saldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());


        table.getItems().add(createRow("Zahlungsmittelkonto", String.format("%.2f", saldo) + " \u20AC", "-", renditeInPeriodenPlaceholder));
    }

    private void createFestgeldRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getFestgeldSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("Festgeld", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    private void createEtfRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getEtfSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("ETF", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    private void createAktienGesamtRow(ArrayList<Double> renditeinPerioden) {
        long benutzerId = AktuelleSpieldaten.getInstanz().getBenutzer().getId();
        double saldo = PortfolioFassade.getInstanz().getAktienSaldo(benutzerId);
        double rendite = PortfolioFassade.getInstanz().getRenditeAktienGesamt(benutzerId, findAktuellePeriode().getId());

        table.getItems().add(createRow("Aktien", String.format("%.2f", saldo) + " \u20AC", rendite + " %", castDoubleListToStringList(renditeinPerioden)));
    }

    private void createAnleihenGesamtRow(ArrayList<Double> renditeinPerioden) {
        double saldo = PortfolioFassade.getInstanz().getAnleihenSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        table.getItems().add(createRow("Anleihen", String.format("%.2f", saldo) + " \u20AC", 0 + " %", castDoubleListToStringList(renditeinPerioden)));
    }








    private double calcRendite(long teilnehmerID, long periodeID, Portfolioposition portfolioposition) {
/*        List<Buchung> buchungen = BuchungRepository.getInstanz().findByTeilnehmerId(teilnehmerID);
        buchungen = buchungen.stream().filter(b -> b.getWertpapier().getId() == portfolioposition.getWertpapier()
                .filter(b ->)
                .getId()).collect(Collectors.toList());
        Kurs aktuellerKurs = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodeID, portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new);

        for (Buchung b : buchungen) {
            long buchungPeriodeId = b.getPeriode().getId();
            Kurs buchungKurs = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(buchungPeriodeId, b.getWertpapier().getId()).orElseThrow(NoSuchElementException::new);

        }*/


        return 0;
    }


}

