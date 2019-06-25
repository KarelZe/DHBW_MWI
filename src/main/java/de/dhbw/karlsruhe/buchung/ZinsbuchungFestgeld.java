package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;


/**
 * Konkrete Implementierung der Buchungsart für Zinsgutschriften aus Festgeldern.
 *
 * @author Markus Bilz
 */
public class ZinsbuchungFestgeld implements Buchungsart {
    /**
     * Methode zur Erzeugung von Zinsbuchungen aus Festgeldern.
     * Die Verbuchung von Zinsgutschriften erfolgt gemäß Fachkonzept Bewertung und Verbuchung
     * von Kapitelerträgen Kapitel 5.2 beschrieben.
     * @param periode      Periode, in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer, auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse z. B. Nominalvolumen
     * @return Zinsbuchung von Festgeld
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setTeilnehmer(teilnehmer);
        buchung.setWertpapier(wertpapier);
        double betrag = bezugsgroesse * periode.getKapitalmarktzinssatz();
        buchung.setVolumen(betrag);
        buchung.setVeraenderungZahlungsmittelkonto(+betrag);
        return buchung;
    }
}
