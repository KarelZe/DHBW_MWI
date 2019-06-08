package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class Verkaufbuchung implements Buchungsart {
    /**
     * @param periode      Periode in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgrosse Bezugsgroesse z. B. Nominalvolumen oder Saldo
     * @return
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgrosse) {
        // TODO: Jan & Raphael implementieren
        return null;
    }
}
