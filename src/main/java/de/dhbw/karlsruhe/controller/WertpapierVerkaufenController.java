package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.buchung.BuchungsFactory;
import de.dhbw.karlsruhe.buchung.Buchungsart;
import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.TransaktionsArt;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class WertpapierVerkaufenController implements ControlledScreen {


    private ScreenController screenController;

    @FXML
    private Label lbZahlungsmittelkonto;
    @FXML
    private Label lbZahlungsmittelSaldo;
    @FXML
    private ListView lstVwWertpapiere;
    @FXML
    private Label lblAuswahl;
    @FXML
    private ComboBox<Portfolioposition> cbPortfoliopositionAuswahl;
    @FXML
    private Label lbAnzahl;
    @FXML
    private TextField txtAnzahl;
    @FXML
    private Label lblGesamtErloes;
    @FXML
    private Label lblGesamtErloesDisplay;
    @FXML
    private Button btnVerkaufen;
    @FXML
    private Label lblOrdervolumen;
    @FXML
    private Label lblOrdervolumenDisplay;
    @FXML
    private Label lblOrderGebuehren;
    @FXML
    private Label lblOrderGebuehrenDisplay;



    private double teilnehmerZahlungsmittelkontoSaldo;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void initialize() {
        ArrayList<Portfolioposition> portfoliopositionen = PortfolioFassade.getInstanz().getPortfoliopositionen(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId()).stream()
                .filter(p -> p.getWertpapier().getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_STARTKAPITAL).collect(Collectors.toCollection(ArrayList::new));
        ObservableList<Portfolioposition> portfoliopositionenComboBox = FXCollections.observableArrayList(portfoliopositionen);
        cbPortfoliopositionAuswahl.setItems(portfoliopositionenComboBox);
        cbPortfoliopositionAuswahl.setConverter(new ConverterHelper().getPortfoliopositionConverter());
        teilnehmerZahlungsmittelkontoSaldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        lbZahlungsmittelSaldo.setText(String.format("%.2f", teilnehmerZahlungsmittelkontoSaldo));
        lblOrderGebuehren.setText("Ordergeb\u00fchren (- " + findAktuelleOrdergebuehr(findAktuellePeriode()) + " %):");


    }

    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

    private double findAktuelleOrdergebuehr(Periode aktuellePeriode) throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findById(aktuellePeriode.getId()).orElseThrow(NoSuchElementException::new).getOrdergebuehr() * 100;
    }

    private double findWertpapierKursValue(Periode aktuellePeriode, Wertpapier selectedWertpapier) throws NoSuchElementException {
        return KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(aktuellePeriode.getId(), selectedWertpapier.getId()).orElseThrow(NoSuchElementException::new).getKurs();
    }

    @FXML
    private void doWertpapierVerkaufen(ActionEvent event) {
        Wertpapier selectedWertpapier;
        double anzahlZuVerkaufen;
        double aktuelleOrderGebuehr;
        double anzahlVorhandeneWertpapierPositionen;

        Periode aktuellePeriode;

        try {
            aktuellePeriode = findAktuellePeriode();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }
        try {
            aktuelleOrderGebuehr = findAktuelleOrdergebuehr(aktuellePeriode);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }

        try {
            selectedWertpapier = cbPortfoliopositionAuswahl.getValue().getWertpapier();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte wählen Sie ein Wertpapier aus.");
            alert.showAndWait();
            return;
        }
        try {
            anzahlZuVerkaufen = Double.valueOf(txtAnzahl.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte geben Sie eine Zahl in das Anzahlfeld ein.");
            alert.showAndWait();
            return;
        }
        try {
            anzahlVorhandeneWertpapierPositionen = PortfolioFassade.getInstanz().getCountOfPositionen(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId(), selectedWertpapier.getId());
            if (anzahlVorhandeneWertpapierPositionen >= anzahlZuVerkaufen) {
                BuchungsFactory buchungsFactory = new BuchungsFactory();
                Buchungsart buchungsart = buchungsFactory.create(TransaktionsArt.TRANSAKTIONSART_VERKAUFEN);
                BuchungRepository.getInstanz().save(buchungsart.create(aktuellePeriode, AktuelleSpieldaten.getInstanz().getBenutzer(), selectedWertpapier, anzahlZuVerkaufen));
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wertpapierkauf");
                alert.setContentText("Verkauf erfolgreich!");
                alert.showAndWait();
                screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_VERKAUFEN, ScreensFramework.SCREEN_WERTPAPIER_VERKAUFEN_FILE);
                screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_VERKAUFEN);

            } else {
                throw new Exception("Fehler: anzahlVorhandeneWertpapierPositionen < anzahlZuVerkaufen");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Nicht gen\u00fcgend Positionen vorhanden.");
            alert.showAndWait();
        }
    }

    @FXML
    private void doBerechneErloes() {
        Wertpapier selectedWertpapier;
        double anzahlZuVerkaufen;
        double wertpapierKursValue;
        double orderVolumen;
        double aktuelleOrderGebuehr;
        double orderGesamtErloes;
        Periode aktuellePeriode;

        try {
            selectedWertpapier = cbPortfoliopositionAuswahl.getValue().getWertpapier();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte wählen Sie ein Wertpapier aus.");
            alert.showAndWait();
            return;
        }
        try {
            anzahlZuVerkaufen = Double.valueOf(txtAnzahl.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte geben Sie eine Zahl in das Anzahlfeld ein.");
            alert.showAndWait();
            return;
        }


        aktuellePeriode = findAktuellePeriode();
        aktuelleOrderGebuehr = findAktuelleOrdergebuehr(aktuellePeriode);
        wertpapierKursValue = findWertpapierKursValue(aktuellePeriode, selectedWertpapier);
        orderVolumen = wertpapierKursValue * anzahlZuVerkaufen;
        orderGesamtErloes = orderVolumen * (1 - (aktuelleOrderGebuehr / 100));     //todo fix raphael
        lblOrdervolumenDisplay.setText(String.format("%.2f", orderVolumen) + "\u20ac");
        lblOrderGebuehrenDisplay.setText(String.format("%.2f", orderVolumen * aktuelleOrderGebuehr / 100) + "\u20ac");
        lblGesamtErloesDisplay.setText(String.format("%.2f", orderGesamtErloes) + "\u20ac");

    }



}
