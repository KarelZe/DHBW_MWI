package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.NoSuchElementException;

/**
 * Factory zur Erzeugung von Bewertungsmodellen. Implementierung des Factory-Patterns (GOF).
 *
 * @author Markus Bilz
 */
class BewertungsmodellFactory {
    /**
     * Fabrik Pattern zur Erzeugung von von Bewertungsmodellen
     * @param wertpapierArt WertpapierArt bspw. Floating Rate Note
     * @return Bewertungsmodell bspw. für Floating Rate Notes
     * @throws NoSuchElementException WertpapierArt ist nicht definiert.
     */
    Bewertungsmodell create(final long wertpapierArt) throws NoSuchElementException {
        if (wertpapierArt == WertpapierArt.WERTPAPIER_ANLEIHE)
            return new FloatingRateNoteModell();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_ETF)
            return new ExchangeTradedFundModell();
        else
            throw new NoSuchElementException("Für diese Wertpapier Art ist keine Bepreisung möglich.");
    }
}
