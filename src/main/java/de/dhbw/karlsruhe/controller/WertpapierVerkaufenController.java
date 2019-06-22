package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

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
    private Label lblGesamtKosten;
    @FXML
    private Label lblGesamtKostenDisplay;
    @FXML
    private Button btnKaufen;
    @FXML
    private Label lblOrdervolumen;
    @FXML
    private Label lblOrdervolumenDisplay;
    @FXML
    private Label lblOrderGebuehren;
    @FXML
    private Label lblOrderGebuehrenDisplay;
    @FXML
    private Label lblAktuellerKurs;
    @FXML
    private Label lblAktuellerKursDisplay;


    private double teilnehmerZahlungsmittelkontoSaldo;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void initialize() {
        ArrayList<Portfolioposition> portfoliopositionen = new ArrayList<>(PortfolioFassade.getInstanz().getPortfoliopositionen(AktuelleSpieldaten.getTeilnehmer().getId(), findAktuellePeriode().getId()));
        ObservableList<Portfolioposition> portfoliopositionenComboBox = FXCollections.observableArrayList(portfoliopositionen);
        cbPortfoliopositionAuswahl.setItems(portfoliopositionenComboBox);
        cbPortfoliopositionAuswahl.setConverter(new ConverterHelper().getPortfoliopositionConverter());
        teilnehmerZahlungsmittelkontoSaldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getTeilnehmer().getId());
        lbZahlungsmittelSaldo.setText(Double.toString(teilnehmerZahlungsmittelkontoSaldo));
        lblOrderGebuehren.setText("Ordergeb\u00fchren (+ " + findAktuelleOrdergebuehr(findAktuellePeriode()) + " %):");


    }

    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

    private double findAktuelleOrdergebuehr(Periode aktuellePeriode) throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findById(aktuellePeriode.getId()).orElseThrow(NoSuchElementException::new).getOrdergebuehr();
    }

    private double findWertpapierKursValue(Periode aktuellePeriode, Wertpapier selectedWertpapier) throws NoSuchElementException {
        return KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(aktuellePeriode.getId(), selectedWertpapier.getId()).orElseThrow(NoSuchElementException::new).getKursValue();
    }

    @FXML
    private void doWertpapierKaufen(ActionEvent event) {


    }

    @FXML
    private void doBerechneKosten() {


    }

    @FXML
    private void doZeigeAktuellerKurs() {

    }


}
