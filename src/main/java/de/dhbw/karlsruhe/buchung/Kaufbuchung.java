package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Kaufbuchung implements Buchungsart {
    /**
     * @param periode      Periode in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse z. B. Nominalvolumen oder Saldo
     * @return
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setTeilnehmer(teilnehmer);
        buchung.setWertpapier(wertpapier);
        TransaktionsArt transaktionsArt = new TransaktionsArt();
        transaktionsArt.setBeschreibung(TransaktionsArt.TRANSAKTIONSART_KAUFEN_NAME);
        buchung.setTransaktionsArt(transaktionsArt);


        List<Kurs> kurseInPeriode = KursRepository.getInstanz().findByPeriodenId(periode.getId());
        Optional<Kurs> wertPapierKurs = kurseInPeriode.stream().filter(k -> k.getWertpapier().getId() == wertpapier.getId()).findFirst(); //Annahme ist, dass es nur ein Wertpapier gibt, das auf dieses Kriterium zutrifft.
        double kurs = wertPapierKurs.get().getKurs();
        buchung.setOrdergebuehr(periode.getOrdergebuehr());
        buchung.setStueckzahl((long) bezugsgroesse);

        long wertpapierArtId = wertpapier.getWertpapierArt().getId();
        if (wertpapierArtId == WertpapierArt.WERTPAPIER_AKTIE) {
            //do Kaufbuchung für Aktie

        } else if (wertpapierArtId == WertpapierArt.WERTPAPIER_ANLEIHE) {
            //do Kaufbuchung für Anleihe
        } else if (wertpapierArtId == WertpapierArt.WERTPAPIER_ETF) {
            //do Kaufbuchung für ETF
        } else if (wertpapierArtId == WertpapierArt.WERTPAPIER_FESTGELD) {
            //do Kaufbuchung für Festgeld
        } else
            throw new NoSuchElementException("Für diese Wertpapier-Art ist keine Buchung möglich.");
        return buchung;
    }

    private double getSaldoZahlungsmittelkontoFromTeilnehmer(Teilnehmer teilnehmer) {
        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        return buchungRepository.findLastBuchungFromTeilnehmerByTeilnehmerId(teilnehmer.getId()).getSaldoZahlungsmittelkonto();
    }
}
