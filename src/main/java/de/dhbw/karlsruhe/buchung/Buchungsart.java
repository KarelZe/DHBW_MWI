package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Implementierung des Factory Patterns (GOF) und Refactoring Patterns.
 * Für Refactoring-Pattern siehe:
 *
 * @see <a href="refactoring.guru">https://refactoring.guru/replace-constructor-with-factory-method#python</a>
 */
public interface Buchungsart {
    /**
     * Implementierung des Factory Patterns (GOF).
     * @param periode    Periode, in der die Transaktion erfolgt
     * @param teilnehmer Teilnehmer, auf dessen Namen die Buchung erfolgt
     * @param wertpapier Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse, z. B. Nominalvolumen oder Anzahl
     * @return buchung Buchungsobjekt mit Buchungsdaten
     */
    Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse);
}
