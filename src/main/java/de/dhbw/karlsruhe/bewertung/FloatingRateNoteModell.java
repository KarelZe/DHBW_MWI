package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

import java.util.ArrayList;

public class FloatingRateNoteModell implements Bewertungsmodell {
    @Override

    public double bewerte(Periode periode, Wertpapier wertpapier) {

        int gespieltePerioden = PeriodenRepository.getInstanz().findAllBySpieleId(periode.getSpiel().getId()).size() - 1;
        int restlaufzeit = 4;

        // Aktueller Spread des Emittenten
        //Optional<Kurs> kursOptional = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periode.getId(), wertpapier.getId());
        double constantSpread = 0.0;
        //if (kursOptional.isPresent())
        //    constantSpread = kursOptional.get().getSpread();

        double spread = wertpapier.getEmissionszins();
        double spotRate = periode.getKapitalmarktzinssatz();

        // Konstruiere risikobehaftet Zinskurve; Starte bei Index 1
        ArrayList<Double> discountRates = new ArrayList<>();
        discountRates.add(0.0d);
        for (int i = 1; i <= restlaufzeit; i++) discountRates.add(spotRate + constantSpread);

        // Konstruiere Cashflow; Starte bei Index 1
        ArrayList<Double> cashflowFix = new ArrayList<>();
        cashflowFix.add(0.0d);

        // Gem. Alexander (2008, S. 11)
        for (int i = 1; i <= restlaufzeit; i++) {
            cashflowFix.add(i <= restlaufzeit - 1 ? 100.0d * spread : 100.0d * (1 + spread));
        }

        // Bewertung Festzinsanleihe
        double presentValueStraightBond = 0.0d;
        for (int i = 1; i <= restlaufzeit; i++) {
            double discountedCashflow = cashflowFix.get(i) / Math.pow(discountRates.get(i), i);
            presentValueStraightBond += discountedCashflow;
        }

        // Bewertung Nullkuponanleihe
        double presentValueZeroBond = 100.0d / Math.pow(1.0d + discountRates.get(restlaufzeit), restlaufzeit);

        // Gem. Alexander (2008, S. 32)
        return presentValueStraightBond - presentValueZeroBond + 100.00d;
    }


}
