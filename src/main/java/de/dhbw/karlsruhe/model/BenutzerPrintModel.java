package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Model, um die Bestenliste aller Benutzer eines Spiels darstellen zu können
 *
 * @author Jan Carlos Riecken
 */
public class BenutzerPrintModel {
    private SimpleLongProperty id;
    private SimpleStringProperty vorname;
    private SimpleStringProperty nachname;
    private SimpleDoubleProperty portfoliowert;

    public BenutzerPrintModel(long id, String vorname, String nachname, Double gesamtSaldo) {
        this.id = new SimpleLongProperty(id);
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
        this.portfoliowert = new SimpleDoubleProperty(gesamtSaldo);
    }

    public BenutzerPrintModel(Benutzer benutzer) {
        this(benutzer.getId(), benutzer.getVorname(), benutzer.getNachname(), 0.0d);
        // Überschreibe Wert mit tatsächlichem Saldo
        PortfolioFassade portfolioFassade = PortfolioFassade.getInstanz();
        double gesamtSaldo = portfolioFassade.getGesamtSaldo(benutzer.getId(), findAktuellePeriode().getId());
        this.portfoliowert = new SimpleDoubleProperty(gesamtSaldo);
    }


    /**
     * Ermittelt die aktuelle Periode
     *
     * @return Perioden-Objekt
     * @throws NoSuchElementException
     * @author Raphael Winkler
     */
    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getVorname() {
        return vorname.get();
    }

    public void setVorname(String vorname) {
        this.vorname.set(vorname);
    }

    public SimpleStringProperty vornameProperty() {
        return vorname;
    }

    public String getNachname() {
        return nachname.get();
    }

    public void setNachname(String nachname) {
        this.nachname.set(nachname);
    }

    public SimpleStringProperty nachnameProperty() {
        return nachname;
    }

    public Double getPortfoliowert() {
        return portfoliowert.get();
    }

    public void setPortfoliowert(Double portfoliowert) {
        this.portfoliowert.set(portfoliowert);
    }

    public SimpleDoubleProperty portfoliowertProperty() {
        return portfoliowert;
    }
}
