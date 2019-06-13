package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.TransaktionsArtRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.Optional;

public class Startkapital implements Buchungsart {
    /**
     * @param periode      Periode in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse z. B. Nominalvolumen oder Saldo
     * @return
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {

        Buchung startkapital = new Buchung();
        Optional<TransaktionsArt> optional = TransaktionsArtRepository.getInstanz().findById(TransaktionsArt.TRANSAKTIONSART_STARTKAPITAL);

        if(optional.isPresent()) {
            startkapital.setTransaktionsArt(optional.get());
        }
        startkapital.setOrdergebuehr(0);
        startkapital.setPeriode(null);
        startkapital.setStueckzahl(1);
        startkapital.setTeilnehmer(teilnehmer);

        startkapital.setVeraenderungDepot(0);
        startkapital.setVeraenderungFestgeld(0);
        startkapital.setVeraenderungZahlungsmittelkonto(AktuelleSpieldaten.getSpiel().getStartkapital());
        startkapital.setVolumen(AktuelleSpieldaten.getSpiel().getStartkapital());
        startkapital.setWertpapier(null);

        return startkapital;
    }
}
