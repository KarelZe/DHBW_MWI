package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.*;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Diese Klasse stellt Methoden zur Darstellung diverser Objekte an der Oberfläche der Anwendung zur Verfügung.
 *
 * @author Markus Bilz, Christian Fix, Raphael Winkler
 */

public class ConverterHelper {

    /**
     * Konverter für Unternehmensobjekte.
     *
     */
    private StringConverter<Unternehmen> unternehmensConverter = new StringConverter<>() {
        /**
         * Methode zur Umwandlung von Unternehmensobjekten in Strings.
         * @param unternehmen Unternehmensobjekt zur Konvertierung.
         * @return {@link String} mit Aufbau {@code Unternehmen 1 [id: 0]}; im Fehlerfall {@code ''}
         * @author Markus Bilz, Christian Fix
         */
        @Override
        public String toString(Unternehmen unternehmen) {
            return unternehmen != null ? unternehmen.getName() + " [id: " + unternehmen.getId() + "]" : "";
        }

        /**
         * Methode zur Umwandlung von Strings in Unternehmensobjekte.
         * @param unternehmen {@link String} zur Konvertierung.
         * @return {@code null}
         * @author Markus Bilz, Christian Fix
         */
        @Override
        public Unternehmen fromString(String unternehmen) {
            return null;
        }
    };
    /**
     * Konverter für Spieleobjekte.
     *
     */
    private StringConverter<Spiel> spieleConverter = new StringConverter<>() {

        /**
         * Methode zur Umwandlung von Spieleobjekten in Strings
         * @param spiel Spielobjekt zur Konvertierung.
         * @return String mit Aufbau {@code Spiel 1 (erstellt am 01.01.1990) (aktiv)}; im Fehlerfall {@code ''}
         * @author Christian Fix
         */
        @Override
        public String toString(Spiel spiel) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            if (spiel != null) {
                StringBuilder sbAnzeige = new StringBuilder("Spiel " + spiel.getId());
                if (spiel.getErstellungsdatum() != null) {
                    sbAnzeige.append(" (erstellt am ");
                    sbAnzeige.append(simpleDateFormat.format(spiel.getErstellungsdatum()));
                    sbAnzeige.append(")");
                }
                if (spiel.getIst_aktiv() == Spiel.SPIEL_AKTIV) {
                    sbAnzeige.append(" (aktiv)");
                }
                return sbAnzeige.toString();
            } else {
                return "";
            }
        }

        /**
         * Methode zur Umwandlung von Strings in Spielobjekte.
         * @param spiel {@link String} zur Konvertierung.
         * @return {@code null}
         * @author Christian Fix
         */
        @Override
        public Spiel fromString(String spiel) {
            return null;
        }
    };

    /**
     * Konverter für Periodenobjekte.
     */
    private StringConverter<Periode> periodenConverter = new StringConverter<>() {
        /**
         * Methode zur Umwandlung von Perioden in Strings.
         * @param periode {@link Periode} zur Konvertierung
         * @return {@link String} mit Aufbau {@code [id: 1]}; im Fehlerfall {@code ''}
         * @author Raphael Winkler
         */
        @Override
        public String toString(Periode periode) {
            return periode != null ? " [id: " + periode.getId() + "]" : "";
        }

        /**
         * Methode zur Umwandlung von Strings in Periodenobjekte.
         * @param periode {@link String} zur Konvertierung.
         * @return {@code null}
         * @author Raphael Winkler
         */
        @Override
        public Periode fromString(String periode) {
            return null;
        }
    };


    /**
     * Konverter für Wertpapierobjekte.
     */
    private StringConverter<Wertpapier> wertpapierConverter = new StringConverter<>() {
        /**
         * Methode zur Umwandlung von Wertpapieren in Strings.
         * @param wertpapier {@link Wertpapier} zur Konvertierung
         * @return {@link String} mit Aufbau {@code Aktien 1 ( Unternehmen 1 - Aktie ) | Kurs: 100.00 €}; im Fehlerfall {@code ''}
         * @author Raphael Winkler
         */
        @Override
        public String toString(Wertpapier wertpapier) {
            if (wertpapier == null) {
                return "";
            } else if (wertpapier.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE) {
                return wertpapier.getName() + " (" + wertpapier.getUnternehmen().getName() + " - " + wertpapier.getWertpapierArt().getName() + ")"
                        + " | Nennwert : 1000\u20ac, " + " Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), wertpapier.getId()).orElseThrow(NoSuchElementException::new).getKurs()) + "%";
            } else {
                return wertpapier.getName() + " (" + wertpapier.getUnternehmen().getName() + " - " + wertpapier.getWertpapierArt().getName() + ")"
                        + " | Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), wertpapier.getId()).orElseThrow(NoSuchElementException::new).getKurs()) + "\u20ac";
            }
        }
        /**
         * Methode zur Umwandlung von Strings in Periodenobjekte.
         * @param id {link String} zur Konvertierung.
         * @return {@code null}
         * @author Raphael Winkler
         */
        @Override
        public Wertpapier fromString(String id) {
            return null;
        }
    };


    /**
     * Konverter für Portfoliopositionen.
     */
    private StringConverter<Portfolioposition> portfoliopositionConverter = new StringConverter<>() {
        /**
         * Methode zur Umwandlung von Wertpapieren in Strings.
         * @param portfolioposition {@link Portfolioposition} zur Konvertierung
         * @return {@link String} mit Aufbau {@code Aktien 1 )Unternehmen 1 - Aktie) | Kurs: 100.00 €}; im Fehlerfall {@code ''}
         * @author Raphael Winkler
         */
        @Override
        public String toString(Portfolioposition portfolioposition) {
            if (portfolioposition == null) {
                return "";
            } else if (portfolioposition.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE) {
                return portfolioposition.getWertpapier().getName() + " (" + portfolioposition.getWertpapier().getUnternehmen().getName() + " - " + portfolioposition.getWertpapier().getWertpapierArt().getName() + ")"
                        + " | Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs()) + "%"
                        + " | Nominalvolumen: " + String.format("%.2f", portfolioposition.getBezugsgroesse()) + "\u20ac"
                        + " (" + PortfolioFassade.getInstanz().getCountOfPositionen(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId())
                        + " Stk.)";
            } else if (portfolioposition.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE || portfolioposition.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF) {
                return portfolioposition.getWertpapier().getName() + " (" + portfolioposition.getWertpapier().getUnternehmen().getName() + " - " + portfolioposition.getWertpapier().getWertpapierArt().getName() + ")"
                        + " | Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs()) + "\u20ac"
                        + " | Positionssaldo: " + String.format("%.2f",
                        (portfolioposition.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs())) + "\u20ac"
                        + " (" + PortfolioFassade.getInstanz().getCountOfPositionen(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId())
                        + " Stk.)";
            } else {
                return portfolioposition.getWertpapier().getName() + " (" + portfolioposition.getWertpapier().getUnternehmen().getName() + " - " + portfolioposition.getWertpapier().getWertpapierArt().getName() + ")"
                        + " | Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs()) + "\u20ac"
                        + " | Positionssaldo: " + String.format("%.2f", portfolioposition.getBezugsgroesse()) + "\u20ac"
                        + " (" + PortfolioFassade.getInstanz().getCountOfPositionen(AktuelleSpieldaten.getInstanz().getBenutzer().getId(), findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId())
                        + " Stk.)";
            }
        }
        /**
         * Methode zur Umwandlung von Strings in Portfoliopositionen.
         * @param id {link String} zur Konvertierung.
         * @return {@code null}
         * @author Raphael Winkler
         */
        @Override
        public Portfolioposition fromString(String id) {
            return null;
        }
    };

    /**
     * Hilfsmethode zur Abfrage der aktuellen Periode.
     *
     * <p>
     * Da Perioden fortlaufend erzeugt werden, ist die aktuelle Periode die Periode mit der höchsten Id.
     * </p>
     *
     * @return aktuelle Periode
     * @throws NoSuchElementException Exception, dass Periode nicht gefunden wurde.
     * @author Raphael Winkler
     */
    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);
    }

    public StringConverter<Portfolioposition> getPortfoliopositionConverter() {
        return portfoliopositionConverter;
    }

    public void setPortfoliopositionConverter(StringConverter<Portfolioposition> portfoliopositionConverter) {
        this.portfoliopositionConverter = portfoliopositionConverter;
    }

    public StringConverter<Wertpapier> getWertpapierConverter() {
        return wertpapierConverter;
    }

    public void setWertpapierConverter(StringConverter<Wertpapier> wertpapierConverter) {
        this.wertpapierConverter = wertpapierConverter;
    }

    public StringConverter<Unternehmen> getUnternehmensConverter() {
        return unternehmensConverter;
    }

    public void setUnternehmensConverter(StringConverter<Unternehmen> unternehmensConverter) {
        this.unternehmensConverter = unternehmensConverter;
    }

    public StringConverter<Spiel> getSpieleConverter() {
        return spieleConverter;
    }

    public void setSpieleConverter(StringConverter<Spiel> spieleConverter) {
        this.spieleConverter = spieleConverter;
    }

    public StringConverter<Periode> getPeriodenConverter() {
        return periodenConverter;
    }

    public void setPeriodenConverter(StringConverter<Periode> periodenConverter) {
        this.periodenConverter = periodenConverter;
    }
}