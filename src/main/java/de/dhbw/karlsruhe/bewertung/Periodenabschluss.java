package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.buchung.BuchungsFactory;
import de.dhbw.karlsruhe.buchung.Buchungsart;
import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.jpa.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Periodenabschluss {

    /**
     * Diese Methode führt einen Periodenabschluss durch. Dies umfasst einerseits die Bewertung aller Wertpapiere und
     * andererseits die Anlage aller Periodenabschluss-Transaktionen.
     *
     * @param periode abzuschließende Periode.
     */
    public void periodeAbschliessen(Periode periode) {
        periodeBewerten(periode);
        verbuchePeriode(periode);
    }

    /**
     * Diese Methode bewertet die Wertpapiere einer Periode. Es werden ausschließlich Wertpapierarten, die eine Bewertung
     * erfordern, bewertet. Das heißt, kein Festgeld und auch keine Aktien.
     * Implementierung des Factory Patterns.
     * @param periode zu bewertende Periode
     */
    private void periodeBewerten(Periode periode){
        KursRepository kursRepository = KursRepository.getInstanz();
        List<Kurs> kurse = kursRepository.findByPeriodenId(periode.getId());
        List<Kurs> kurseZuBewerten = kurse.stream().filter(kurs -> kurs.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF || kurs.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).collect(Collectors.toList());

        BewertungsmodellFactory bewertungsmodellFactory = new BewertungsmodellFactory();
        for (Kurs kurs : kurseZuBewerten) {
            Wertpapier wertpapier = kurs.getWertpapier();
            Bewertungsmodell bewertungsmodell = bewertungsmodellFactory.create(wertpapier.getWertpapierArt().getId());
            double bewertungskurs = bewertungsmodell.bewerte(periode, wertpapier);
            kurs.setKurs(bewertungskurs);
        }
        kursRepository.save(kurseZuBewerten);
    }

    /**
     * Diese Methode erstellt alle Transaktionen, die für den Periodenabschluss erforderlich sind.
     * Diese umfassen die Verbuchung von Zinsen auf Anleihen und Festgelder.
     *
     * @param periode zu verbuchende Periode
     */
    private void verbuchePeriode(Periode periode) {

        // TODO: Buchung für alle Teilnehmer durchführen
        BuchungsFactory buchungsFactory = new BuchungsFactory();
        ArrayList<Buchung> buchungen = new ArrayList<>();
        // TODO: filtere relevante WPs -> festgeld & Anleihe
        // for alle Teilnehmer
        // for alle Wertpapiere
        Buchungsart buchungsart = buchungsFactory.create(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER);
        Buchung buchung = buchungsart.create(periode, new Teilnehmer(), new Wertpapier());
        buchungen.add(buchung);
        BuchungRepository.getInstanz().save(buchungen);
    }



}
