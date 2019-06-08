package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.buchung.BuchungsFactory;
import de.dhbw.karlsruhe.buchung.Buchungsart;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.jpa.*;

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
     * Diese Methode bewertet die Wertpapiere einer Periode. Die Bewertung der Wertpapiere muss in einer stringenten
     * Reihenfolge erfolgen, um zu gewährleisten, dass Indizes nach darin enthaltenen Wertpapieren (insbesondere ETF) bewertet werden.
     * Es wird daher eine Bewertung in folgender Reihenfolge vorgenommen Aktie, Anleihe, Festgeld dann ETF.
     * Implementierung des Factory Patterns.
     * @param periode zu bewertende Periode
     */
    private void periodeBewerten(Periode periode){
        // Frage Wertpapiere ab und vertausche abhängige und unabhängige Anlagen, so dass unabhängige Anlagen zuerst bewertet werden.
        KursRepository kursRepository = KursRepository.getInstanz();
        List<Kurs> kurse = kursRepository.findByPeriodenId(periode.getId());
        List<Kurs> kurseUnabhaenging = kurse.stream().filter(kurs -> kurs.getWertpapier().getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_ETF).collect(Collectors.toList());
        kurse.removeAll(kurseUnabhaenging);
        kurseUnabhaenging.addAll(kurse);

        BewertungsmodellFactory bewertungsmodellFactory = new BewertungsmodellFactory();
        for (Kurs kurs : kurse) {
            Wertpapier wertpapier = kurs.getWertpapier();
            Bewertungsmodell bewertungsmodell = bewertungsmodellFactory.create(wertpapier.getWertpapierArt().getId());
            double bewertungskurs = bewertungsmodell.bewerte(periode, wertpapier);
            kurs.setKurs(bewertungskurs);
        }
        kursRepository.save(kurse);
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
        Buchungsart buchungsart = buchungsFactory.create(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT);
    }



}
