package de.dhbw.karlsruhe.helper;

public class NumberHelper {
    public static double parseDouble(String string, double defaultValue) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
