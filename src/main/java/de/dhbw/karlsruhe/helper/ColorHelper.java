package de.dhbw.karlsruhe.helper;

import javafx.scene.paint.Color;

/**
 * Diese Klasse stellt Bequemlichkeitsmethoden zur Konvertierung von Color-Objekte in String-Objekte und
 * umgekehrt bereit. Strings werden für die Speicherung in der Datenbank o. Ä. benötigt.
 *
 * @author Markus Bilz
 */
public class ColorHelper {

    /**
     * Konvertiert einen {@link String} in ein {@link Color}-Objekt.
     * @param farbe Farbe zur Konvertierung
     * @return {@link Color} Objekt der Farbe, sofern keine Konvertierung möglich ist, {@link Color} Objekt der Farbe Schwarz
     * @author Markus Bilz
     */
    public static Color string2Color(String farbe) {
        System.out.println(farbe);
        String farbeGekuerzt = farbe.substring(2);
        String[] farbkomponenten = farbeGekuerzt.split("(?<=\\G.{" + 2 + "})");
        int r = 0, g = 0, b = 0, o = 0;
        try {
            r = Integer.valueOf(farbkomponenten[0], 16);
            g = Integer.valueOf(farbkomponenten[1], 16);
            b = Integer.valueOf(farbkomponenten[2], 16);
            o = Integer.valueOf(farbkomponenten[3], 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Color(r / 255.0, g / 255.0, b / 255.0, o / 255.0);
    }

    /**
     * Konvertiert ein {@link Color}-Objekt in einen {@link String}. Die String-Repräsentation hat folgenden
     * Aufbau {@code 0x000000ff}.
     *
     * @param farbe Farbe zur Konvertierung
     * @return Farbe als {@link String}
     * @author Markus Bilz
     */
    public static String color2String(Color farbe) {
        return farbe.toString();

    }
}
