package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class Aktie implements Bewertungsmodell {
    @Override
    public Kurs bewerte(Periode periode, Wertpapier wertpapier) {
        return null;
    }
}
