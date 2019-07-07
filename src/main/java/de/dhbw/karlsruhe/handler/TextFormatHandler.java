package de.dhbw.karlsruhe.handler;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasse mit Formatiereren zur Verwendung im UI.
 *
 * <p>
 * Implementierung ist adaptiert von <a href="https://github.com/shanakaperera/CourtWelfareLoanSystem/blob/master/src/com/court/controller/LoanCalculatorFxmlController.java">github.com</a>.
 * </p>
 *
 * @author Markus Bilz
 */
public class TextFormatHandler {

    private static final double STANDARD_WERT = 0.00d;
    private static final String WAEHRUNGS_SYMBOL = "\u20ac"; // Währung €
    private static final String PROZENT_SYMBOL = "%";
    private static final DecimalFormatSymbols DEUTSCHLAND = new DecimalFormatSymbols(Locale.GERMANY);
    public static final DecimalFormat CURRENCY_DECIMAL_FORMAT
            = new DecimalFormat("###,##0.00" + WAEHRUNGS_SYMBOL, DEUTSCHLAND);

    public static final DecimalFormat PERCENTAGE_DECIMAL_FORMAT = new DecimalFormat("##0.00" + PROZENT_SYMBOL, DEUTSCHLAND);

    public static TextFormatter<Double> currencyFormatter() {
        /**
         * Gibt die Instanz eines {@link TextFormatter} zurück zur Formatierung von Währungsbetragen als
         * formatierte {@link String Strings}.
         * @return TextFormatter
         * @author Markus Bilz
         */
        return new TextFormatter<>(new StringConverter<>() {
            @Override
            public String toString(Double value) {
                return CURRENCY_DECIMAL_FORMAT.format(value);
            }

            @Override
            public Double fromString(String string) {
                try {
                    return CURRENCY_DECIMAL_FORMAT.parse(string).doubleValue();
                } catch (ParseException e) {
                    return Double.NaN;
                }
            }
        }, STANDARD_WERT,
                change -> {
                    try {
                        CURRENCY_DECIMAL_FORMAT.parse(change.getControlNewText());
                        return change;
                    } catch (ParseException e) {
                        return null;
                    }
                }
        );
    }

    /**
     * Methode zur Konvertierung einess Währungsbetrags eines {@link TextField TextFields} in einen {@link Double}.
     * @param textField zu konvertierendes {@link TextField}
     * @return {@link Double} z. B. {@code 1.0}; im Fehlerfall {@code 0.0}
     * @author Markus Bilz
     */
    public static Double getCurrencyFieldValue(TextField textField) {
        try {
            return CURRENCY_DECIMAL_FORMAT.parse(textField.getText()).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(TextFormatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    /**
     * Methode zur Konvertierung des Inhalts eines Währungsbetrags als {@link String} in einen {@link Double}.
     * @param value Währungsbetrag zur Konvertierung
     * @return {@link Double} z. B. {@code 1.0}; im Fehlerfall {@code 0.0}
     * @author Markus Bilz
     */
    public static Double getCurrencyFieldValue(String value) {
        try {
            return CURRENCY_DECIMAL_FORMAT.parse(value).doubleValue();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Methode zur Konvertierung des Inhalts einer Prozenzahl als {@link String} in einen {@link Double}.
     * @param value Prozentzahl zur Konvertierung
     * @return {@link Double} z. B. {@code 1.0}; im Fehlerfall {@code 0.0}
     * @author Markus Bilz
     */
    public static Double getPercentageFieldValue(String value) {
        try {
            return PERCENTAGE_DECIMAL_FORMAT.parse(value).doubleValue();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Gibt die Instanz eines {@link TextFormatter} zurück zur Konvertierung von Prozentzahlen als
     * {@link String Strings} in {@link Double} und umgekehrt.
     * @return TextFormatter
     * @author Markus Bilz
     */
    public static TextFormatter<Double> percentageFormatter() {
        return new TextFormatter<>(new StringConverter<>() {
            @Override
            public String toString(Double value) {
                return PERCENTAGE_DECIMAL_FORMAT.format(value);
            }

            @Override
            public Double fromString(String string) {
                try {
                    return PERCENTAGE_DECIMAL_FORMAT.parse(string).doubleValue();
                } catch (ParseException e) {
                    return Double.NaN;
                }
            }
        }, STANDARD_WERT, change -> {
            try {
                PERCENTAGE_DECIMAL_FORMAT.parse(change.getControlNewText());
                return change;
            } catch (ParseException e) {
                return null;
            }
        });
    }

    /**
     * Methode zur Konvertierung einer Prozentzahl eines {@link TextField TextFields} in einen {@link Double}.
     * @param textField zu konvertierendes {@link TextField}
     * @return {@link Double} z. B. {@code 1.0}; im Fehlerfall {@code 0.0}
     * @author Markus Bilz
     */
    public static Double getPercentageFieldValue(TextField textField) {
        try {
            return PERCENTAGE_DECIMAL_FORMAT.parse(textField.getText()).doubleValue() * 100;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }
}