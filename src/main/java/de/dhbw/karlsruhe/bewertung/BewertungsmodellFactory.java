package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.NoSuchElementException;

class BewertungsmodellFactory {
    /**
     * Implementierung des Factory Patterns
     *
     * @param wertpapierArt Wertpapier Art bspw. Aktie
     * @return Bewertungsmodell bspw. für Floating Rate Notes
     */
    Bewertungsmodell create(final long wertpapierArt) {
        if (wertpapierArt == WertpapierArt.WERTPAPIER_AKTIE)
            return new Aktienmodell();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_ANLEIHE)
            return new FloatingRateNoteModell();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_ETF)
            return new ExchangeTradedFundModell();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_FESTGELD)
            return new FestgeldModell();
        else
            throw new NoSuchElementException("Für diese Wertpapier Art ist keine Bepreisung möglich.");
    }
}
