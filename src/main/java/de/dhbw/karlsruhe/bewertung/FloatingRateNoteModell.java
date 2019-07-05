package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Konkrete Implementierung des Bewertungsmodells für die Bewertung von Floating Rate Notes (FRN).
 *
 * @author Markus Bilz
 */
public class FloatingRateNoteModell implements Bewertungsmodell {

    /**
     * Diese Methode implementiert die Bewertung einer Floating Rate Note.
     * Die Implementierung erfolgt gem. Fachkonzept Bewertung von Finanzanlagen und Verbuchung von Kapitalerträgen.
     * @param periode    Periode, für die eine Bewertung erfolgen soll.
     * @param wertpapier Wertpapier, das zu bewerten ist.
     * @return Kurs der Floating Rate Note.
     */
    @Override
    public double bewerte(Periode periode, Wertpapier wertpapier) {

        int bisherGespieltePerioden = PeriodenRepository.getInstanz().findAllBySpieleId(periode.getSpiel().getId()).size() - 1;
        int restlaufzeit = 10 - bisherGespieltePerioden;

        Optional<Kurs> kursOptional =  KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(),wertpapier.getId());
        // Spread des Emittenten bei Emission
        double emissionsspread = wertpapier.getEmissionsspread();
        // Aktueller Spread des Emittenten zum Zeitpunkt der Bewertung
        double spread = kursOptional.orElse(new Kurs()).getSpread();
        double kapitalmarktzinssatz = periode.getKapitalmarktzinssatz();

        System.out.println("restlaufzeit: " + restlaufzeit);
        System.out.println("spread:" + spread);
        System.out.println("emissionsspread: " + emissionsspread);
        System.out.println("kapitalmarktzins: " + kapitalmarktzinssatz);

        // Konstruiere risikobehaftete Zinskurve; Starte bei Index 1
        ArrayList<Double> zinskurve = new ArrayList<>();
        zinskurve.add(0.0d);
        for (int i = 1; i <= restlaufzeit; i++) {
            zinskurve.add(kapitalmarktzinssatz + spread);
        }

        // Konstruiere Zahlungsstrom; Starte bei Index 1
        ArrayList<Double> zahlungsstromFest = new ArrayList<>();
        zahlungsstromFest.add(0.0d);

        // Gem. Alexander (2008, S. 11)
        for (int i = 1; i <= restlaufzeit; i++) {
            zahlungsstromFest.add(i <= restlaufzeit - 1 ? 100.0d * emissionsspread : 100.0d * (1.0d + emissionsspread));
        }
        // Bewertung Festzinsanleihe
        ArrayList<Double> zahlungsstromDiskontiert = new ArrayList<>();
        for (int i = 1; i <= restlaufzeit; i++) {
            double zahlung = zahlungsstromFest.get(i) / Math.pow(1+zinskurve.get(i), i);
            zahlungsstromDiskontiert.add(zahlung);
        }
        double barwertStandardAnleihe = zahlungsstromDiskontiert.stream().mapToDouble(z -> z).sum();

        System.out.println("Zahlungsstrom fest"+zahlungsstromDiskontiert);

        // Bewertung Nullkuponanleihe
        double barwertZeroBond = 100.0d / Math.pow(1.0d + zinskurve.get(restlaufzeit), restlaufzeit);
        // Bewertung variable Zahlung
        double variableZahlung = 100.00d * (1.0d + kapitalmarktzinssatz) / (1.0d + zinskurve.get(1));

        System.out.println("Zerobond" + barwertZeroBond);
        System.out.println("variabel" + variableZahlung);
        // Gem. Alexander (2008, S. 32)
        return (barwertStandardAnleihe - barwertZeroBond) + variableZahlung;
    }


}
