package de.dhbw.karlsruhe.buchung;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.TransaktionsArtRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.Optional;

/**
 * Konkrete Implementierung der Buchungsart für den Kauf von Wertpapieren.
 *
 * @author Raphael Winkler
 */
public class Kaufbuchung implements Buchungsart {
    /**
     * Methode zur Erzeugung von Kaufbuchungen für Aktien, Anleihen, ETFs und Festgelder.
     *
     * @param periode       Periode, in der die Transaktion erfolgt
     * @param benutzer      Benutzer, auf dessen Namen die Buchung erfolgt
     * @param wertpapier    Wertpapier, das in Buchung involviert ist.
     * @param bezugsgroesse Bezugsgroesse, z. B. Nominalvolumen.
     * @return Kaufbuchung des Wertpapiers
     * @author Raphael Winkler
     */
    @Override
    public Buchung create(Periode periode, Benutzer benutzer, Wertpapier wertpapier, double bezugsgroesse) throws UnsupportedOperationException {
        Buchung buchung = new Buchung();
        buchung.setPeriode(periode);
        buchung.setBenutzer(benutzer);
        buchung.setWertpapier(wertpapier);
        buchung.setStueckzahl((long) bezugsgroesse);
        buchung.setOrdergebuehr(periode.getOrdergebuehr());

        // Frage Transaktions Art ab. Wird bei Spielanlage initialisiert.
        Optional<TransaktionsArt> transaktionsArt = TransaktionsArtRepository.getInstanz().findById(TransaktionsArt.TRANSAKTIONSART_KAUFEN);
        transaktionsArt.ifPresent(buchung::setTransaktionsArt);
        long wertpapierArtId = wertpapier.getWertpapierArt().getId();
        if (wertpapierArtId == WertpapierArt.WERTPAPIER_AKTIE || wertpapierArtId == WertpapierArt.WERTPAPIER_ETF) {

            // Abfrage des Kurses aus Datenbank. Kurs ist einzigartig. Wird Kurs nicht gefunden ist er 0.
            Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());

            // Erfassung des Volumens
            buchung.setVolumen(bezugsgroesse); // aus technischen Gründen ist das gespeicherte Volumen = der Stückzahl.

            // Erfassung der Saldenveränderung auf Konto. Auf Depot wird Gegenwert ex Gebühren gutgeschrieben.
            kursOptional.ifPresent(k -> buchung.setVeraenderungDepot(k.getKurs() * bezugsgroesse));

            // Auf Zahlungsmittelkonto werden Gebühren als auch der Gegenwert aus der Buchung belastet.
            buchung.setVeraenderungZahlungsmittelkonto(-(buchung.getVeraenderungDepot() * (buchung.getOrdergebuehr() + 1)));

            return buchung;
        } else if (wertpapierArtId == WertpapierArt.WERTPAPIER_ANLEIHE) {

            // Abfrage des Kurses aus Datenbank. Kurs ist einzigartig. Wird Kurs nicht gefunden ist er 0.
            Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());

            // Erfassung des Volumens
            buchung.setVolumen(bezugsgroesse * 1000); /// aus technischen Gründen ist das gespeicherte Volumen = des Nominalvolumens. Nennwert der Anleihen = 1000

            // Erfassung der Saldenveränderung auf Konto. Auf Depot wird Gegenwert ex Gebühren gutgeschrieben.
            kursOptional.ifPresent(k -> buchung.setVeraenderungDepot((k.getKurs() / 100) * buchung.getVolumen()));

            // Auf Zahlungsmittelkonto werden Gebühren als auch der Gegenwert aus der Buchung belastet.
            buchung.setVeraenderungZahlungsmittelkonto(-(buchung.getVeraenderungDepot() * (buchung.getOrdergebuehr() + 1)));
            return buchung;
        } else if (wertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD) {
            // Abfrage des Kurses aus Datenbank. Kurs ist einzigartig. Wird Kurs nicht gefunden ist er 0.
            Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());
            // Erfassung des Volumens
            kursOptional.ifPresent(k -> buchung.setVolumen(k.getKurs() * bezugsgroesse));
            // Erfassung der Saldenveränderung auf Konto. Auf Festgeldbestand wird Gegenwert ex Gebühren gutgeschrieben.
            buchung.setVeraenderungFestgeld(+buchung.getVolumen());
            // Auf Zahlungsmittelkonto werden Gebühren als auch der Gegenwert aus der Buchung belastet.
            buchung.setVeraenderungZahlungsmittelkonto(-(buchung.getVolumen() * (buchung.getOrdergebuehr() + 1)));
            return buchung;

        } else {
            throw new UnsupportedOperationException("Für diese Wertpapierart ist keine Kaufbuchung erlaubt.");
        }

    }
}
