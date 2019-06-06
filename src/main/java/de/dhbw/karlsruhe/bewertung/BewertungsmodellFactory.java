package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.NoSuchElementException;

public class BewertungsmodellFactory {

    Bewertungsmodell create(final long wertpapierArt) {
        if (wertpapierArt == WertpapierArt.WERTPAPIER_AKTIE)
            return new Aktie();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_ANLEIHE)
            return new FloatingRateNote();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_ETF)
            return new ExchangeTradedFund();
        else if (wertpapierArt == WertpapierArt.WERTPAPIER_FESTGELD)
            return new Festgeld();
        else
            throw new NoSuchElementException("Für diese Wertpapier Art ist keine Bepreisung möglich.");
    }
}
