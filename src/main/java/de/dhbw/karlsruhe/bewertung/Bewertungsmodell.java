package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Gemeinsame Schnittstelle aller Bewertungsmodelle.
 *
 * <p>Bewertungsmodelle werden in der Anwendung benötigt, um den Marktwert und damit die
 * rechnerischen Kurse von Finanzanlagen wie Anleihen und ETFs zu bestimmen.</p>
 *
 * <p>Implementierung folgt dem Factory-Patterns (GOF).</p>
 *
 * @author Markus Bilz
 */
public interface Bewertungsmodell {

    /**
     * Berechnet den Kurs eines {@link Wertpapier Wertpapiers} für eine {@link Periode}.
     *
     * <p>
     * Fachliche Grundlage ist das {@code Fachkonzept Bewertung von Finanzanlagen und Verbuchung
     * von Kapitalerträgen}.
     * </p>
     *
     * <p>
     * Implementierung folgt dem Factory Patterns (GOF).
     * </p>
     *
     * @param periode    Periode, für die eine Bewertung erfolgen soll.
     * @param wertpapier Wertpapier, das zu bewerten ist.
     * @return Kurs des Wertpapiers
     *
     * @author Markus Bilz
     */
    double bewerte(Periode periode, Wertpapier wertpapier);


}
