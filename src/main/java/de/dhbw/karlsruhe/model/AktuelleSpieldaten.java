package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Spiel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;

/**
 * Diese Klasse stellt Methoden zur Abfrage des aktuellen Spiels und des eingeloggten Teilnehmers zur Verfügung.
 * Sie repräsentiert eine Art Session, welche die aktuelle Konfiguration des Spiels enthält. Die Klasse
 * muss muss für den Zugriff von Rollen- oder Spielbasierten Zugriffen verwendet werden.
 *
 * @author Christian Fix
 */
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
