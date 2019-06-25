package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.TransaktionsArtRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.Optional;


public class Startkapital implements Buchungsart {

    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {

        Buchung startkapital = new Buchung();

        Optional<TransaktionsArt> optional = TransaktionsArtRepository.getInstanz().findById(TransaktionsArt.TRANSAKTIONSART_STARTKAPITAL);

        optional.ifPresent(startkapital::setTransaktionsArt);
        startkapital.setOrdergebuehr(0);
        startkapital.setPeriode(periode);
        startkapital.setStueckzahl(1);
        startkapital.setTeilnehmer(teilnehmer);
        startkapital.setVeraenderungDepot(0);
        startkapital.setVeraenderungFestgeld(0);
        startkapital.setVeraenderungZahlungsmittelkonto(AktuelleSpieldaten.getSpiel().getStartkapital());
        startkapital.setVolumen(AktuelleSpieldaten.getSpiel().getStartkapital());
        startkapital.setWertpapier(wertpapier);

        return startkapital;
    }
}
