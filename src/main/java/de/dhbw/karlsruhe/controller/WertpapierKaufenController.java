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


/**
 * Controller für Finanzanlage kaufen
 * @author Raphael Winkler
 */
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
    private Label lblGesamtKosten;
    @FXML
    private Label lblGesamtKostenDisplay;
    @FXML
    private Button btnKaufen;
    @FXML
    private Label lblZwischensumme;
    @FXML
    private Label lblZwischensummeDisplay;
    @FXML
    private Label lblOrderGebuehren;
    @FXML
    private Label lblOrderGebuehrenDisplay;


    private double teilnehmerZahlungsmittelkontoSaldo;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    /**
     * Initialisierung
     * @author Raphael Winkler
     */
    @FXML
    private void initialize() {
        ArrayList<Wertpapier> wertpapiere = WertpapierRepository.getInstanz().findAll().stream()
                .filter(w -> w.getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_STARTKAPITAL).collect(Collectors.toCollection(ArrayList::new)); // hole alle Wertpapiere außer Startkapital-Wertpapiere

        // Checken ob Wertpapier Kurs = 0 hat
        wertpapiere.removeIf(w -> KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), w.getId()).orElseThrow(NoSuchElementException::new).getKurs() == 0);


        ObservableList<Wertpapier> wertpapiereComboBox = FXCollections.observableArrayList(wertpapiere);
        cbWertpapierAuswahl.setItems(wertpapiereComboBox);
        cbWertpapierAuswahl.setConverter(new ConverterHelper().getWertpapierConverter());
        teilnehmerZahlungsmittelkontoSaldo = PortfolioFassade.getInstanz().getZahlungsmittelkontoSaldo(AktuelleSpieldaten.getInstanz().getBenutzer().getId());
        lbZahlungsmittelSaldo.setText(String.format("%.2f", teilnehmerZahlungsmittelkontoSaldo));
        lblOrderGebuehren.setText("Ordergeb\u00fchren (+ " + (findAktuelleOrdergebuehr(findAktuellePeriode()) * 100) + " %):");


    }

    /**
     * Ermittelt die aktuelle Periode
     * @return Perioden-Objekt
     * @throws NoSuchElementException
     * @author Raphael Winkler
     */
    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

    /**
     * Ermittelt die aktuell gültige Ordergebühr
     * @param aktuellePeriode aktuelle Periode
     * @return Ordergebühr
     * @throws NoSuchElementException
     * @author Raphael Winkler
     */
    private double findAktuelleOrdergebuehr(Periode aktuellePeriode) throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findById(aktuellePeriode.getId()).orElseThrow(NoSuchElementException::new).getOrdergebuehr();
    }

    /**
     * Ermittelt den Kurs eines übergebenen Wertpapiers
     * @param aktuellePeriode aktuelle Periode
     * @param selectedWertpapier Wertpapier, für das der Kurs ermittelt werden soll
     * @return Kurs
     * @throws NoSuchElementException
     * @author Raphael Winkler
     */
    private double findWertpapierKursValue(Periode aktuellePeriode, Wertpapier selectedWertpapier) throws NoSuchElementException {
        return KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(aktuellePeriode.getId(), selectedWertpapier.getId()).orElseThrow(NoSuchElementException::new).getKurs();
    }

    /**
     * Methode zur Speicherung der Kaufbuchung.
     *
     * @param event Event des aufrufenden Buttons
     */
    @FXML
    private void doWertpapierKaufen(ActionEvent event) {
        Wertpapier selectedWertpapier;
        double anzahlZuKaufen;
        double wertpapierKursValue;
        double orderVolumen;
        double aktuelleOrderGebuehr;
        double orderGesamtKosten;
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
            if (anzahlZuKaufen <= 0) { // keine negative Anzahl
                throw new NumberFormatException();
            } else if ((anzahlZuKaufen != Math.floor(anzahlZuKaufen)) || Double.isInfinite(anzahlZuKaufen)) { // Keine Zahl mit Nachkommastellen
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte geben Sie eine ganze positive Zahl in das Anzahlfeld ein.");
            alert.showAndWait();
            return;
        }
        try {
            wertpapierKursValue = findWertpapierKursValue(aktuellePeriode, selectedWertpapier);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }
        try {
            if (selectedWertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE || selectedWertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF) {
                orderVolumen = anzahlZuKaufen; // aus technischen Gründen ist das gespeicherte Volumen = der Stückzahl.
                orderGesamtKosten = orderVolumen * wertpapierKursValue * (aktuelleOrderGebuehr + 1);
            } else if (selectedWertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD) {
                orderVolumen = anzahlZuKaufen * wertpapierKursValue; // aus technischen Gründen ist das gespeicherte Volumen = der gekauften Festgeldsumme
                orderGesamtKosten = orderVolumen * (aktuelleOrderGebuehr + 1);
            } else { // Anleihe
                orderVolumen = anzahlZuKaufen * 1000; // aus technischen Gründen ist das gespeicherte Volumen = des Nominalvolumens
                orderGesamtKosten = orderVolumen * (wertpapierKursValue / 100) * (aktuelleOrderGebuehr + 1);
            }


            if (orderGesamtKosten <= teilnehmerZahlungsmittelkontoSaldo) {
                BuchungsFactory buchungsFactory = new BuchungsFactory();
                Buchungsart buchungsart = buchungsFactory.create(TransaktionsArt.TRANSAKTIONSART_KAUFEN);
                BuchungRepository.getInstanz().save(buchungsart.create(aktuellePeriode, AktuelleSpieldaten.getInstanz().getBenutzer(), selectedWertpapier, anzahlZuKaufen));
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wertpapierkauf");
                alert.setContentText("Kauf erfolgreich!");
                alert.showAndWait();
                screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_KAUFEN, ScreensFramework.SCREEN_WERTPAPIER_KAUFEN_FILE);
                screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_KAUFEN);
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

    /**
     * Methode zur Berechnung und Aktualisierung der Anzeigen Zwischensumme, Gebuehren und Gesamtkosten
     */
    @FXML
    private void doBerechneKosten() {
        Wertpapier selectedWertpapier;
        double anzahlZuKaufen;
        double wertpapierKursValue;
        double zwischensumme;
        double aktuelleOrderGebuehr;
        double orderGesamtKosten;
        Periode aktuellePeriode;

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
            if (anzahlZuKaufen <= 0) { // keine negative Anzahl
                throw new NumberFormatException("Negative Zahl eingetragen");
            } else if ((anzahlZuKaufen != Math.floor(anzahlZuKaufen)) || Double.isInfinite(anzahlZuKaufen)) { // Keine Zahl mit Nachkommastellen
                throw new NumberFormatException("Kommazahl eingetragen");
            }
        } catch (NumberFormatException e) {
            if (e.getMessage().equals("empty String")) {

                lblZwischensummeDisplay.setText(0 + "\u20ac");
                lblOrderGebuehrenDisplay.setText(0 + "\u20ac");
                lblGesamtKostenDisplay.setText(0 + "\u20ac");
                return;
            }
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte geben Sie eine ganze positive Zahl in das Anzahlfeld ein.");
            alert.showAndWait();
            return;
        }


        aktuellePeriode = findAktuellePeriode();
        aktuelleOrderGebuehr = findAktuelleOrdergebuehr(aktuellePeriode);
        wertpapierKursValue = findWertpapierKursValue(aktuellePeriode, selectedWertpapier);


        if (selectedWertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE || selectedWertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF) {
            zwischensumme = anzahlZuKaufen * wertpapierKursValue;
            orderGesamtKosten = zwischensumme * (aktuelleOrderGebuehr + 1);

        } else if (selectedWertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD) {
            zwischensumme = anzahlZuKaufen * wertpapierKursValue;
            orderGesamtKosten = zwischensumme * (aktuelleOrderGebuehr + 1);
        } else { // Anleihe
            zwischensumme = anzahlZuKaufen * 1000 * (wertpapierKursValue / 100);
            orderGesamtKosten = zwischensumme * (aktuelleOrderGebuehr + 1);
        }

        lblOrderGebuehrenDisplay.setText(String.format("%.2f", zwischensumme * aktuelleOrderGebuehr) + "\u20ac");
        lblZwischensummeDisplay.setText(String.format("%.2f", zwischensumme) + "\u20ac");
        lblGesamtKostenDisplay.setText(String.format("%.2f", orderGesamtKosten) + "\u20ac");

    }


}
