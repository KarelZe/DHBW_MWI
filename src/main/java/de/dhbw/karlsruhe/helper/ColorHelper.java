package de.dhbw.karlsruhe.helper;

import javafx.scene.paint.Color;

public class ColorHelper {

    public static Color string2Color(String s) {
        // TODO: Color Object 2 HexString das gegensätzliche hiervon String.format("0x%02x%02x%02x%02x" , r, g, b, o);
        return new Color(0.5, 0.5, 0.5, 0.5);
    }

    public static String color2String(Color color) {
        return color.toString();

    }
}
