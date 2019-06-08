package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.TransaktionsArt;

import java.util.NoSuchElementException;

public class BuchungsFactory {

    /**
     * Implementierung des Factory Patterns.
     *
     * @param transaktionsArt Transaktionsart der Buchung
     * @return Buchungsart abhängig der Transaktionsart
     * @throws NoSuchElementException Exception, falls TransaktionsArt nicht implementiert ist.
     */
    public Buchungsart create(final long transaktionsArt) throws NoSuchElementException {

        if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT)
            return new Zinsbuchung();
        else if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_KAUFEN)
            return new Kaufbuchung();
        else if (transaktionsArt == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN)
            return new Verkaufbuchung();
        else
            throw new NoSuchElementException("Für diese Transaktions Art ist keine Buchung möglich.");
    }
}
