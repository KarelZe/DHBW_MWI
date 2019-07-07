package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Benutzer;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Model, um einen Benutzer in der GUI anzeigen zu können
 *
 * @author Christian Fix
 */
public class BenutzerViewModel {
    private SimpleLongProperty id;
    private SimpleStringProperty vorname;
    private SimpleStringProperty nachname;

    public BenutzerViewModel(Benutzer benutzer) {
        this(benutzer.getId(), benutzer.getVorname(), benutzer.getNachname());
    }

    public BenutzerViewModel(long id, String vorname, String nachname) {
        this.id = new SimpleLongProperty(id);
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
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
}
