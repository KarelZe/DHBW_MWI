package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Konkrete Implementierung der Buchungsart für Zinserträge aus Anleihen.
 *
 * @author Markus Bilz
 */

public class ZinsbuchungAnleihe implements Buchungsart {
    /**
     * Methode zur Verbuchung von Zinsen aus Anleihen mit Gutschrift auf Zahlungsmittelkonto.
     * Die Verbuchung von Zinsgutschriften erfolgt gemäß Fachkonzept Bewertung und Verbuchung
     * von Kapitelerträgen Kapitel 5.3 beschrieben.
     * @param periode      Periode, in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer, auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse, z. B. Nominalvolumen oder Saldo.
     * @return Zinsbuchung von Anleihe
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setTeilnehmer(teilnehmer);
        buchung.setWertpapier(wertpapier);
        // Berechne aus Nominalvolumen * (Emissionsspread + Kapitalmarktzins).
        double zinsgutschrift = bezugsgroesse * (wertpapier.getEmissionszins() + periode.getKapitalmarktzinssatz());
        buchung.setVolumen(zinsgutschrift);
        buchung.setVeraenderungZahlungsmittelkonto(+zinsgutschrift);
        return buchung;
    }
}
