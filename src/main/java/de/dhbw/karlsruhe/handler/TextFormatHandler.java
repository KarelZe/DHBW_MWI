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
 * Implementierung von Formattern zur
 * TODO: Markus Sauber kommentieren und anpassen auf deutsches Format
 * Adaptiert von
 * https://github.com/shanakaperera/CourtWelfareLoanSystem/blob/master/src/com/court/controller/LoanCalculatorFxmlController.java
 */
public class TextFormatHandler {

    private static final double DEFAULT_VALUE = 0.00d;
    private static final String CURRENCY_SYMBOL = "\u20ac"; // Währung €
    private static final String PERCENTAGE_SYMBOL = "%";
    private static final DecimalFormatSymbols GERMANY = new DecimalFormatSymbols(Locale.GERMANY);
    public static final DecimalFormat CURRENCY_DECIMAL_FORMAT
            = new DecimalFormat("###,##0.00" + CURRENCY_SYMBOL, GERMANY);

    public static final DecimalFormat PERCENTAGE_DECIMAL_FORMAT = new DecimalFormat("##0.00" + PERCENTAGE_SYMBOL, GERMANY);

    public static TextFormatter<Double> currencyFormatter() {
        /**
         *
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
        }, DEFAULT_VALUE,
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
     *
     * @param textField
     * @return
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
     *
     * @param value
     * @return
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
     *
     * @param value
     * @return
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
     *
     * @return
     */
    public static TextFormatter<Double> percentageFormatter() {
        return new TextFormatter<Double>(new StringConverter<Double>() {
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
        }, DEFAULT_VALUE, change -> {
            try {
                PERCENTAGE_DECIMAL_FORMAT.parse(change.getControlNewText());
                return change;
            } catch (ParseException e) {
                return null;
            }
        });
    }

    /**
     * Methode zur Konvertierung des Inhalts eines {@link TextField TextFields} in einen {@link Double}.
     * @param textField zu konvertierendes {@link TextField}
     * @return {@link Double} z. B. {@code 1.0}; im Fehlerfall {@code 0.0}
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