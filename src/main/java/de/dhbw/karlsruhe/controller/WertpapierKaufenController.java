package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.buchung.BuchungsFactory;
import de.dhbw.karlsruhe.buchung.Buchungsart;
import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.*;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
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

public class WertpapierKaufenController implements ControlledScreen {


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
    private ComboBox<Wertpapier> cbWertpapierAuswahl;
    @FXML
    private Label lbAnzahl;
    @FXML
    private TextField txtAnzahl;
    @FXML
    private Label lblKosten;
    @FXML
    private Label lblKostenDisplay;
    @FXML
    private Button btnKaufen;


    private double teilnehmerZahlungsmittelkontoSaldo;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void initialize() {
        ArrayList<Wertpapier> wertpapiere = WertpapierRepository.getInstanz().findAll().stream()
                .filter(w -> w.getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_STARTKAPITAL).collect(Collectors.toCollection(ArrayList::new)); // hole alle Wertpapiere außer Startkapital-Wertpapiere
        ObservableList<Wertpapier> wertpapiereComboBox = FXCollections.observableArrayList(wertpapiere);
        cbWertpapierAuswahl.setItems(wertpapiereComboBox);
        cbWertpapierAuswahl.setConverter(new ConverterHelper().getWertpapierConverter());
        teilnehmerZahlungsmittelkontoSaldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getTeilnehmer().getId());
        lbZahlungsmittelSaldo.setText(Double.toString(teilnehmerZahlungsmittelkontoSaldo));


    }

    @FXML
    private void doWertpapierKaufen(ActionEvent event) {
        Wertpapier selectedWertpapier;
        double anzahlZuKaufen;
        double wertpapierKursValue;
        double kaufwert;
        double aktuelleOrderGebuehr;
        double orderGesamtKosten;
        Periode aktuellePeriode;

        try {
            aktuellePeriode = PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }
        try {
            aktuelleOrderGebuehr = PeriodenRepository.getInstanz().findById(aktuellePeriode.getId()).orElseThrow(NoSuchElementException::new).getOrdergebuehr();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }

        try {
            selectedWertpapier = cbWertpapierAuswahl.getValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte wählen Sie ein Wertpapier aus.");
            alert.showAndWait();
            return;
        }
        try {
            anzahlZuKaufen = Double.valueOf(txtAnzahl.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte geben Sie eine Zahl in das Anzahlfeld ein.");
            alert.showAndWait();
            return;
        }
        try {
            wertpapierKursValue = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(aktuellePeriode.getId(), selectedWertpapier.getId()).orElseThrow(NoSuchElementException::new).getKursValue();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }
        try {
            kaufwert = wertpapierKursValue * anzahlZuKaufen;
            orderGesamtKosten = kaufwert * (aktuelleOrderGebuehr / 100 + 1);
            if (orderGesamtKosten <= teilnehmerZahlungsmittelkontoSaldo) {
                BuchungsFactory buchungsFactory = new BuchungsFactory();
                Buchungsart buchungsart = buchungsFactory.create(TransaktionsArt.TRANSAKTIONSART_KAUFEN);
                BuchungRepository.getInstanz().save(buchungsart.create(aktuellePeriode, AktuelleSpieldaten.getTeilnehmer(), selectedWertpapier, anzahlZuKaufen));
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wertpapierkauf");
                alert.setContentText("Kauf erfolgreich!");
                alert.showAndWait();

            } else {
                throw new Exception("Fehler: orderGesamtKosten > teilnehmerZahlungsmittelkontoSaldo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Das Ordervolumen wird vom Zahlungsmittelkonto nicht gedeckt.");
            alert.showAndWait();
        }





    }

    private void validateOrder() {

    }


}
