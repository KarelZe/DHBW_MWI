package de.dhbw.karlsruhe.handler;

import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

/**
 * TODO: Markus Sauber kommentieren und anpassen auf deutsches Format
 * Adaptiert von
 * https://github.com/shanakaperera/CourtWelfareLoanSystem/blob/master/src/com/court/controller/LoanCalculatorFxmlController.java
 */
public class TextFormatHandler {

    private static final double DEFAULT_VALUE = 0.00d;
    private static final String CURRENCY_SYMBOL = "Rs"; // LKR Currency
    private static final String PRECENTAGE_SYMBOL = "%";
    private static final DecimalFormatSymbols GERMANY = new DecimalFormatSymbols(Locale.GERMANY );
    public static final DecimalFormat CURRENCY_DECIMAL_FORMAT
            = new DecimalFormat(CURRENCY_SYMBOL + "###,##0.00", GERMANY);

    public static final DecimalFormat PRECENTAGE_DECIMAL_FORMAT = new DecimalFormat("#0.00" + PRECENTAGE_SYMBOL, GERMANY);

    public static TextFormatter<Double> currencyFormatter() {

        return new TextFormatter<Double>(new StringConverter<Double>() {
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

    public static Double getCurrencyFieldValue(TextField textField) {
        try {
            return CURRENCY_DECIMAL_FORMAT.parse(textField.getText()).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(TextFormatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    public static Double getCurrencyFieldValue(String value) {
        try {
            return CURRENCY_DECIMAL_FORMAT.parse(value).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(TextFormatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    public static TextFormatter<Double> percentageFormatter() {
        return new TextFormatter<Double>(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                return PRECENTAGE_DECIMAL_FORMAT.format(value);
            }

            @Override
            public Double fromString(String string) {
                try {
                    return PRECENTAGE_DECIMAL_FORMAT.parse(string).doubleValue();
                } catch (ParseException e) {
                    return Double.NaN;
                }
            }
        }, DEFAULT_VALUE, change -> {
            try {
                PRECENTAGE_DECIMAL_FORMAT.parse(change.getControlNewText());
                return change;
            } catch (ParseException e) {
                return null;
            }
        });
    }

    public static Double getPercentageFieldValue(TextField textField) {
        try {
            return PRECENTAGE_DECIMAL_FORMAT.parse(textField.getText()).doubleValue() * 100;
        } catch (ParseException ex) {
            Logger.getLogger(TextFormatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }


    public static TextFormatter<String> numbersOnlyFieldFormatter() {
        return new TextFormatter<>(change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        });
    }

    public static LocalDate NowDate() {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static Long roundAmount(double amount) {
        return Math.round(amount);
    }
}