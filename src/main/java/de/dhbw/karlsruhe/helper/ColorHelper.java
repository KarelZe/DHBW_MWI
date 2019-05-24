package de.dhbw.karlsruhe.helper;

import javafx.scene.paint.Color;

public class ColorHelper {

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

    public static String color2String(Color color) {
        return color.toString();

    }
}
