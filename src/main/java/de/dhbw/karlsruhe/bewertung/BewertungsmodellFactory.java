package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.NoSuchElementException;

/**
 * Factory zur Erzeugung von Bewertungsmodellen.
 *
 * <p>
 *     Implementierung des Factory-Patterns (GOF).
 * </p>
 *
 * @author Markus Bilz
 */
public class BewertungsmodellFactory {
    /**
     * Factory Pattern zur Erzeugung von {@link Bewertungsmodell Bewertungsmodellen}.
     *
     * <p>
     * Die Fabrik ermittelt anhand der {@link WertpapierArt} das {@link Bewertungsmodell}.
     * Dies ist notwendig, da unterschiedliche Finanzanlagen bspw. Exchange Traded Funds und Floating Rate Notes
     * unterschiedliche Bewertungsmodelle erfordern. Die Fabrik kann beliebig erweitert werden.
     * </p>
     *
     * <p>
     * Fachliche Grundlage ist das {@code Fachkonzept Bewertung von Finanzanlagen und Verbuchung von Kapitalerträgen}.
     * </p>
     *
     * @param wertpapierArt WertpapierArt bspw. Floating Rate Note
     * @return {@link Bewertungsmodell} bspw. für Floating Rate Notes
     * @throws NoSuchElementException WertpapierArt ist nicht definiert.
     *
     * @author Markus Bilz
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
