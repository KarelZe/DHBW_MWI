package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.TransaktionsArtRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.Optional;
/**
 * Konkrete Implementierung der Buchungsart für den Verkauf von Wertpapieren.
 *
 * @author Raphael Winkler
 */
public class Verkaufbuchung implements Buchungsart {
    /**
     * Methode zur Erzeugung von Verkaufbuchungen für Aktien, Anleihen, ETFs und Festgelder.
     *
     * @param periode       Periode, in der die Transaktion erfolgt
     * @param benutzer      Benutzer, auf dessen Namen die Buchung erfolgt
     * @param wertpapier    Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse, z. B. Nominalvolumen.
     * @return Kaufbuchung des Wertpapiers
     * @author Raphael Winkler
     */
    @Override
    public Buchung create(Periode periode, Benutzer benutzer, Wertpapier wertpapier, double bezugsgroesse) {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setBenutzer(benutzer);
        buchung.setWertpapier(wertpapier);
        buchung.setStueckzahl((long) bezugsgroesse);
        buchung.setOrdergebuehr(periode.getOrdergebuehr());

        // Frage Transaktions Art ab. Wird bei Spielanlage initialisiert.
        Optional<TransaktionsArt> transaktionsArt = TransaktionsArtRepository.getInstanz().findById(TransaktionsArt.TRANSAKTIONSART_VERKAUFEN);
        transaktionsArt.ifPresent(buchung::setTransaktionsArt);
        long wertpapierArtId = wertpapier.getWertpapierArt().getId();
        if (wertpapierArtId == WertpapierArt.WERTPAPIER_AKTIE || wertpapierArtId == WertpapierArt.WERTPAPIER_ANLEIHE || wertpapierArtId == WertpapierArt.WERTPAPIER_ETF) {

            // Abfrage des Kurses aus Datenbank. Kurs ist einzigartig. Wird Kurs nicht gefunden ist er 0.
            Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());
            kursOptional.ifPresent(k -> buchung.setVolumen(-(k.getKurs() * bezugsgroesse)));

            // Erfassung der Saldenveränderung auf Konto. Auf Depot wird Gegenwert ex Gebühren gutgeschrieben.
            buchung.setVeraenderungDepot(buchung.getVolumen());

            // Auf Zahlungsmittelkonto werden Gebühren als auch der Gegenwert aus der Buchung belastet.
            buchung.setVeraenderungZahlungsmittelkonto(+(-buchung.getVolumen() * (1 - (buchung.getOrdergebuehr()))));

            return buchung;
        } else if (wertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD) {
            Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());
            kursOptional.ifPresent(k -> buchung.setVolumen(-(k.getKurs() * bezugsgroesse)));
            buchung.setVeraenderungFestgeld(buchung.getVolumen());
            buchung.setVeraenderungZahlungsmittelkonto(+(-buchung.getVolumen() * (1 - (buchung.getOrdergebuehr()))));
            return buchung;

        } else {
            throw new UnsupportedOperationException("Für diese Wertpapierart ist keine Verkaufsbuchung erlaubt.");
        }
    }
}
