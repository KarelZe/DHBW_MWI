package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Factory zur Erzeugung von Bewertungsmodellen. Implementierung des Factory-Patterns (GOF).
 *
 * @author Markus Bilz
 */
public interface Bewertungsmodell {

    /**
     * Implementierung des Factory Patterns (GOF).
     * Methode dient zur Berechnung eines Kurses für eine Periode.
     * Fachliche Grundlage ist das Fachkonzept Bewertung von Finanzanlagen und Verbuchung von Kapitalerträgen
     *
     * @param periode    Periode, für die eine Bewertung erfolgen soll.
     * @param wertpapier Wertpapier, das zu bewerten ist.
     * @return Kurs des Wertpapiers
     */
    double bewerte(Periode periode, Wertpapier wertpapier);


}
