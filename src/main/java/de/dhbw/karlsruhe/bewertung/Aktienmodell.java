package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class Aktienmodell implements Bewertungsmodell {
    @Override
    public double bewerte(Periode periode, Wertpapier wertpapier) {
        return 88.00d;
    }
}
