package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Spiel;
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
    private Benutzer benutzer;
    private Spiel spiel;
    private List<InvalidationListener> alleListener = new ArrayList<>();

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Christian Fix
     */
    private AktuelleSpieldaten() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * <p>
     * Implementierung als Singleton Pattern (GOF).
     * </p>
     *
     * @return Instanz von {@link AktuelleSpieldaten}
     * @author Christian Fix
     */
    public static AktuelleSpieldaten getInstanz() {
        if (instanz == null)
            instanz = new AktuelleSpieldaten();
        return instanz;
    }

    /**
     * Methode zum Registrieren eines Listener.
     *
     * <p>
     * Implementierung des Observer Patterns (GOF).
     * </p>
     *
     * @param listener Listener zur Benachrichtigung
     * @author Markus Bilz
     */
    @Override
    public void addListener(InvalidationListener listener) {
        alleListener.add(listener);
    }

    /**
     * Methode zum Entfernen eines Listener.
     *
     * <p>
     * Implementierung des Observer Patterns (GOF).
     * </p>
     *
     * @param listener Listener zum Entfernen
     * @author Markus Bilz
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        alleListener.remove(listener);
    }

    /**
     * Methode zum Benachrichtigen der Listener.
     *
     * <p>
     * Implementierung des Observer Patterns (GOF).
     * </p>
     *
     * @author Markus Bilz
     */
    private void notifyListeners() {
        for (InvalidationListener name : alleListener) {
            name.invalidated(this);
        }
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
        notifyListeners();
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

}
