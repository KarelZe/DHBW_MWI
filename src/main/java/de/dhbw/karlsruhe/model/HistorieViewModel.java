package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Model, um die Transaktionshistorie in der GUI anzuzeigen
 *
 * @author Jan Carlos Riecken
 */
public class HistorieViewModel {
    private SimpleLongProperty id;
    private SimpleStringProperty kaufart, wertpapierart, unternehmen;
    private SimpleDoubleProperty volume;

    public HistorieViewModel(Buchung buchung) {
        this(buchung.getId(), buchung.getTransaktionsArt().getBeschreibung(), buchung.getWertpapier().getWertpapierArt().getName(), buchung.getWertpapier().getUnternehmen().getName(), buchung.getVolumen());
        System.out.println("ID: " + buchung.getId() + "; Transaktionsart: " + buchung.getTransaktionsArt().getBeschreibung() + " ; Wertpapierart: " + buchung.getWertpapier().getWertpapierArt().getName());
    }

    public HistorieViewModel(long id, String kaufart, String wertpapierart, String unternehmen, Double volume) {
        this.id = new SimpleLongProperty(id);
        this.kaufart = new SimpleStringProperty(kaufart);
        this.wertpapierart = new SimpleStringProperty(wertpapierart);
        this.unternehmen = new SimpleStringProperty(unternehmen);
        this.volume = new SimpleDoubleProperty(volume.intValue());
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

    public String getKaufart() {
        return kaufart.get();
    }

    public void setKaufart(String kaufart) {
        this.kaufart.set(kaufart);
    }

    public SimpleStringProperty kaufartProperty() {
        return kaufart;
    }

    public String getWertpapierart() {
        return wertpapierart.get();
    }

    public void setWertpapierart(String wertpapierart) {
        this.wertpapierart.set(wertpapierart);
    }

    public SimpleStringProperty wertpapierartProperty() {
        return wertpapierart;
    }

    public String getUnternehmen() {
        return unternehmen.get();
    }

    public void setUnternehmen(String unternehmen) {
        this.unternehmen.set(unternehmen);
    }

    public SimpleStringProperty unternehmenProperty() {
        return unternehmen;
    }

    public double getVolume() {
        return volume.get();
    }

    public void setVolume(double volume) {
        this.volume.set(volume);
    }

    public SimpleDoubleProperty volumeProperty() {
        return volume;
    }
}
