package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Spiel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;

public class AktuelleSpieldaten {
    private static Teilnehmer teilnehmer;
    private static Spiel spiel;


    public static Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public static void setTeilnehmer(Teilnehmer teilnehmer) {
        AktuelleSpieldaten.teilnehmer = teilnehmer;
    }

    public static Spiel getSpiel() {
        return spiel;
    }

    public static void setSpiel(Spiel spiel) {
        AktuelleSpieldaten.spiel = spiel;
    }
}
