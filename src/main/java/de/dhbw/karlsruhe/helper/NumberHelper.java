package de.dhbw.karlsruhe.helper;

/**
 * Diese Klasse stellt Bequemlichkeitsmethoden zur Konvertierung von Strings in Zahlen bereit.
 *
 * @author Markus Bilz
 */

public class NumberHelper {
    /**
     * Methode zur Konvertierung eines String in einen double
     *
     * @param zahl         Zahl zur Konvertierung
     * @param standardwert Standardwert im Fehlerfall
     * @return double der Zahl, im Fehlerfall den Standardwert
     */
    public static double parseDouble(String zahl, double standardwert) {
        try {
            return Double.parseDouble(zahl);
        } catch (NumberFormatException e) {
            return standardwert;
        }
    }
}
