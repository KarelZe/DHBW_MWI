package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class ZinsbuchungWertpapier implements Buchungsart {
    /**
     * Methode zur Verbuchung von Zinsen aus Wertpapier mit Zinsgutschrift auf Zahlungsmittelkonto
     *
     * @param periode      Periode in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse z. B. Nominalvolumen oder Saldo
     * @return Zinsbuchung
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setTeilnehmer(teilnehmer);
        buchung.setWertpapier(wertpapier);
        double ausschuettungAufZahlungsmittelkonto = bezugsgroesse * wertpapier.getEmissionszins();
        buchung.setVolumen(ausschuettungAufZahlungsmittelkonto);
        buchung.setVeraenderungZahlungsmittelkonto(+ausschuettungAufZahlungsmittelkonto);
        return buchung;
    }
}
