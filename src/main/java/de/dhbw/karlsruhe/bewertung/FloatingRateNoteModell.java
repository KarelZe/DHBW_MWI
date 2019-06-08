package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class FloatingRateNoteModell implements Bewertungsmodell {
    @Override
    public double bewerte(Periode periode, Wertpapier wertpapier) {
        // TODO: Richtige Implementierung vornehmen.
        return 77.00d;
    }
}
