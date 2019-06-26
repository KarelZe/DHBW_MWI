package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Spiel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;


/**
 * Diese Klasse stellt Methoden zur Abfrage des aktuellen Spiels und des eingeloggten Teilnehmers zur Verfügung.
 * Sie repräsentiert eine Art Session, welche die aktuelle Konfiguration des Spiels enthält. Die Klasse
 * muss muss für den Zugriff von Rollen- oder Spielbasierten Zugriffen verwendet werden.
 *
 * @author Christian Fix
 */
public class AktuelleSpieldaten implements Observable {
    private static AktuelleSpieldaten instanz;
    private Teilnehmer teilnehmer;
    private Spiel spiel;
    private List<InvalidationListener> alleListener = new ArrayList<>();

    private AktuelleSpieldaten() {
    }

    public static AktuelleSpieldaten getInstanz() {
        if (instanz == null)
            instanz = new AktuelleSpieldaten();
        return instanz;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        alleListener.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        alleListener.remove(listener);
    }

    public Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
        notifyListeners();
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    private void notifyListeners() {
        for (InvalidationListener name : alleListener) {
            name.invalidated(this);
        }
    }
}
