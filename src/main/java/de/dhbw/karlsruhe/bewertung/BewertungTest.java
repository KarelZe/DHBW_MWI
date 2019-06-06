package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

public class BewertungTest {

    private void bewertePeriode() {
        BewertungsmodellFactory bewertungsmodellFactory = new BewertungsmodellFactory();
        Bewertungsmodell bewertungsmodell = bewertungsmodellFactory.create(WertpapierArt.WERTPAPIER_AKTIE);
        Kurs kurs = bewertungsmodell.bewerte(new Periode(), new Wertpapier());

        System.out.println(kurs);
    }
}
