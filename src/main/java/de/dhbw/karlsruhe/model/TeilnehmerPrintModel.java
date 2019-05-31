package de.dhbw.karlsruhe.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TeilnehmerPrintModel {
    private SimpleLongProperty id;
    private SimpleStringProperty vorname;
    private SimpleStringProperty nachname;
    private SimpleDoubleProperty barwert;

    public TeilnehmerPrintModel(long id, String vorname, String nachname) {
        this.id = new SimpleLongProperty(id);
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
        this.barwert = new SimpleDoubleProperty(10000);
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

    public Double getBarwert() {return barwert.get(); }

    public void setBarwert(Double barwert) { this.barwert.set(barwert);}

    public SimpleDoubleProperty barwertProperty() {
        return barwert;
    }
}
