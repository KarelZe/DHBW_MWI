package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public interface Buchungsart {
    /**
     * Implmentierung des Factory Patterns und Refactoring Patterns. Siehe hierzu:
     * https://refactoring.guru/replace-constructor-with-factory-method#python
     *
     * @param periode    Periode in der die Transaktion erfolgt
     * @param teilnehmer Teilnehmer auf dessen Namen die Buchung erfolgt
     * @param wertpapier Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse z. B. Nominalvolumen oder Saldo
     * @return buchung Buchungsobjekt mit allen relevanten Daten
     */
    Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse);
}
