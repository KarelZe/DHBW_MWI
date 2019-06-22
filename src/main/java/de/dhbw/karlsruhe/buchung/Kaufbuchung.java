package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.TransaktionsArtRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.Optional;

public class Kaufbuchung implements Buchungsart {
    /**
     * @param periode      Periode in der die Transaktion erfolgt
     * @param teilnehmer   Teilnehmer auf dessen Namen die Buchung erfolgt
     * @param wertpapier   Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse z. B. Nominalvolumen oder Saldo
     * @return buchung mit Umsatzdaten oder leere Buchung
     */
    @Override
    public Buchung create(Periode periode, Teilnehmer teilnehmer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setTeilnehmer(teilnehmer);
        buchung.setWertpapier(wertpapier);
        buchung.setStueckzahl((long) bezugsgroesse);
        buchung.setOrdergebuehr(periode.getOrdergebuehr());

        // Frage Transaktions Art ab. Wird bei Spielanlage initialisiert.
        Optional<TransaktionsArt> transaktionsArt = TransaktionsArtRepository.getInstanz().findById(TransaktionsArt.TRANSAKTIONSART_KAUFEN);
        transaktionsArt.ifPresent(buchung::setTransaktionsArt);

        // Abfrage des Kurses aus Datenbank. Kurs ist einzigartig. Wird Kurs nicht gefunden ist er 0.
        Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());
        kursOptional.ifPresent(k -> buchung.setVolumen(k.getKursValue() * bezugsgroesse));

        // Erfassung der Saldenveränderung auf Konto. Auf Depot wird Gegenwert ex Gebühren gutgeschrieben.
        buchung.setVeraenderungDepot(+buchung.getVolumen());

        // Auf Zahlungsmittelkonto werden Gebühren als auch der Gegenwert aus der Buchung belastet.
        buchung.setVeraenderungZahlungsmittelkonto(-(buchung.getVolumen() * (buchung.getOrdergebuehr() / 100 + 1)));


        return buchung;
    }
}
