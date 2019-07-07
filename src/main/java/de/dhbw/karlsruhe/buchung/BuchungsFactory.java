package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.TransaktionsArt;

import java.util.NoSuchElementException;

/**
 * Factory zur Erzeugung von Buchungen. Implementierung des Factory Patterns (GOF).
 *
 * @author Markus Bilz
 */
public class BuchungsFactory {

    /**
     * Fabrikmethode zur Erzeugung einer Buchung abhängig der TransaktionsArt.
     *
     * @param transaktionsArt TransaktionsArt der Buchung
     * @return Buchungsart abhängig der TransaktionArt
     * @throws NoSuchElementException Exception, falls TransaktionsArt nicht implementiert ist.
     */
    public Buchungsart create(final long transaktionsArt) throws NoSuchElementException {

        if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER)
            return new ZinsbuchungAnleihe();
        if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_FESTGELD)
            return new ZinsbuchungFestgeld();
        else if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_KAUFEN)
            return new Kaufbuchung();
        else if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN)
            return new Verkaufbuchung();
        else if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_STARTKAPITAL)
            return new Startkapital();
        else
            throw new NoSuchElementException("Für diese Transaktions Art ist keine Buchung möglich.");
    }
}
