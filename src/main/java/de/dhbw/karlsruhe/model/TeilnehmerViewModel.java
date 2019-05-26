package de.dhbw.karlsruhe.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.boot.registry.selector.SimpleStrategyRegistrationImpl;

public class TeilnehmerViewModel {
    private SimpleLongProperty id;
    private SimpleStringProperty vorname;
    private SimpleStringProperty nachname;

    public TeilnehmerViewModel(long id, String vorname, String nachname) {
        this.id = new SimpleLongProperty(id);
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getVorname() {
        return vorname.get();
    }

    public SimpleStringProperty vornameProperty() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname.set(vorname);
    }

    public String getNachname() {
        return nachname.get();
    }

    public SimpleStringProperty nachnameProperty() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname.set(nachname);
    }
}
