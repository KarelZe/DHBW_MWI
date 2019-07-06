package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.TransaktionsArtRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.Optional;

/**
 * Konkrete Implementierung der Buchungsart für Zinserträge aus Anleihen.
 *
 * @author Markus Bilz
 */

public class ZinsbuchungAnleihe implements Buchungsart {
    /**
     * Methode zur Erzeugung von Zinsbuchungen aus Floating Rate Notes.
     * Die Verbuchung von Zinsgutschriften erfolgt gemäß Fachkonzept Bewertung und Verbuchung
     * von Kapitelerträgen Kapitel 5.3 beschrieben.
     * @param periode      Periode, in der die Transaktion erfolgt
     * @param benutzer   Benutzer, auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse, z. B. Nominalvolumen oder Saldo.
     * @return Zinsbuchung von Anleihe
     */
    @Override
    public Buchung create(Periode periode, Benutzer benutzer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setBenutzer(benutzer);
        buchung.setWertpapier(wertpapier);
        // Berechne aus Nominalvolumen * (Emissionsspread + Kapitalmarktzins).
        double zinsgutschrift = bezugsgroesse * (wertpapier.getEmissionsspread() + periode.getKapitalmarktzinssatz());
        // Zinsbuchung führt zu keiner Veränderung des Bestands an Anleihen
        buchung.setVolumen(0);
        buchung.setVeraenderungZahlungsmittelkonto(+zinsgutschrift);

        Optional<TransaktionsArt> transaktionsArt = TransaktionsArtRepository.getInstanz().findById(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER);
        transaktionsArt.ifPresent(buchung::setTransaktionsArt);

        return buchung;
    }
}
