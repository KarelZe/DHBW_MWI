package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class FestgeldModell implements Bewertungsmodell {
    /**
     * Implementierung der Bewertungsmodells für Festgelder. Festgelder sind im Datenmodell als Wertpapier implementiert.
     * Da Festgelder keinen Kurswertrisiken unterliegen, erfolgt eine Bewertung zu 100.00 %.
     *
     * @param periode    Periode, für die eine Bewertung erfolgen soll.
     * @param wertpapier Wertpapier, das zu bewerten ist.
     * @return Kurs des Festgelds
     */
    @Override
    public Kurs bewerte(Periode periode, Wertpapier wertpapier) {
        Kurs kurs = new Kurs(periode, wertpapier);
        kurs.setKurs(100.00d);
        return kurs;
    }
}
