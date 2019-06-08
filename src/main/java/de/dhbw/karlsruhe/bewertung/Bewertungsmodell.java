package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public interface Bewertungsmodell {

    /**
     * Implementierung des Factory Patterns. Methode dient zur Berechnung eines Kurses für eine Periode.
     *
     * @param periode    Periode, für die eine Bewertung erfolgen soll.
     * @param wertpapier Wertpapier, das zu bewerten ist.
     * @return berechneter Kurs oder null.
     */
    double bewerte(Periode periode, Wertpapier wertpapier);


}
