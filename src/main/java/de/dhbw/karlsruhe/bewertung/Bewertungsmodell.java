package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public interface Bewertungsmodell {


    Kurs bewerte(Periode periode, Wertpapier wertpapier);


}
