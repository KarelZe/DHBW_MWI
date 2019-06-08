package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.List;
import java.util.stream.Collectors;

public class Periodenabschluss {

    /**
     * Diese Methode bewertet die Wertpapiere einer Periode. Die Bewertung der Wertpapiere muss in einer stringenten
     * Reihenfolge erfolgen, um zu gewährleisten, dass Indizes nach darin enthaltenen Wertpapieren (insbesondere ETF) bewertet werden.
     * Es wird daher eine Bewertung in folgender Reihenfolge vorgenommen Aktie, Anleihe, Festgeld dann ETF.
     *
     * @param periode zu bewertende Periode
     */
    public void bewertePeriode(Periode periode) {

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



}
