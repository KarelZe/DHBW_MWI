package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Gemeinsame Schnittstelle aller Buchungen.
 *
 * <p>
 * Implementierung des Factory Patterns (GOF) und des Refactoring Patterns.
 * Für Refactoring-Pattern siehe: <a href="https://refactoring.guru/replace-constructor-with-factory-method#python">https://refactoring.guru/</a>
 * </p>
 *
  */
public interface Buchungsart {
    /**
     * Erzeugt eine Buchung für eine {@link Periode} abhängig des {@link Wertpapier Wertpapiers}.
     *
     * <p>
     * Implementierung des Factory Patterns (GOF).
     * </p>
     *
     * @param periode       {@link Periode}, in der die Transaktion erfolgt
     * @param benutzer      {@link Benutzer}, auf dessen Namen die Buchung erfolgt
     * @param wertpapier    {@link Wertpapier}, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse, z. B. Nominalvolumen oder Anzahl
     * @return buchung {@link Buchung} mit Buchungsdaten z. B. Volumen
     * @author Markus Bilz
     */
    Buchung create(Periode periode, Benutzer benutzer, Wertpapier wertpapier, double bezugsgroesse);
}
