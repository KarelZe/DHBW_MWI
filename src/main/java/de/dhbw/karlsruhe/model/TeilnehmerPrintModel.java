package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TeilnehmerPrintModel {
    private SimpleLongProperty id;
    private SimpleStringProperty vorname;
    private SimpleStringProperty nachname;
    private SimpleDoubleProperty portfoliowert;

    public TeilnehmerPrintModel(long id, String vorname, String nachname, Double gesamtSaldo) {
        this.id = new SimpleLongProperty(id);
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
        this.portfoliowert = new SimpleDoubleProperty(gesamtSaldo);
    }

    public TeilnehmerPrintModel(Teilnehmer teilnehmer) {
        this(teilnehmer.getId(), teilnehmer.getVorname(), teilnehmer.getNachname(), 0.0d);
        // Überschreibe Wert mit tatsächlichem Saldo
        PortfolioFassade portfolioFassade = PortfolioFassade.getInstanz();
        double gesamtSaldo = portfolioFassade.getGesamtSaldo(teilnehmer.getId());
        this.portfoliowert = new SimpleDoubleProperty(gesamtSaldo);
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
