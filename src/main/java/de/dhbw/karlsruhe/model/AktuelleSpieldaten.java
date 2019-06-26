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
 * @author Christian Fix, Markus Bilz
 */
public class AktuelleSpieldaten implements Observable {
    private static AktuelleSpieldaten instanz;
    private Teilnehmer teilnehmer;
    private Spiel spiel;
    private List<InvalidationListener> alleListener = new ArrayList<>();

    /**
     * Singleton Implementierung (GOF).
     */
    private AktuelleSpieldaten() {
    }

    /**
     * Singleton Implementierung (GOF)
     *
     * @return instanz
     */
    public static AktuelleSpieldaten getInstanz() {
        if (instanz == null)
            instanz = new AktuelleSpieldaten();
        return instanz;
    }

    /**
     * Methode zum Registrieren eines Listener.
     * @param listener Listener zur Benachrichtigung
     */
    @Override
    public void addListener(InvalidationListener listener) {
        alleListener.add(listener);
    }

    /**
     * Methode zum Entfernen eines Listener.
     * @param listener Listener zum Entfernen
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        alleListener.remove(listener);
    }

    /**
     * Methode zum Benachrichtigen der Listener.
     */
    private void notifyListeners() {
        for (InvalidationListener name : alleListener) {
            name.invalidated(this);
        }
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

}
