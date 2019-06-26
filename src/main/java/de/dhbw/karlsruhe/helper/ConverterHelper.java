package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
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
     *  Konverter für Unternehmensobjekte.
     */
    private StringConverter<Unternehmen> unternehmensConverter = new StringConverter<>() {
        /**
         * Methode zur Umwandlung von Unternehmensobjekten in Strings.
         * @param unternehmen Unternehmensobjekt zur Konvertierung.
         * @return String mit Aufbau {@code Unternehmen 1 [id: 0]}; im Fehlerfall {@code ''}
         */
        @Override
        public String toString(Unternehmen unternehmen) {
            return unternehmen != null ? unternehmen.getName() + " [id: " + unternehmen.getId() + "]" : "";
        }

        /**
         * Methode zur Umwandlung von Strings in Unternehmensobjekte.
         * @param unternehmen String zur Konvertierung.
         * @return {@code null}
         */
        @Override
        public Unternehmen fromString(String unternehmen) {
            return null;
        }
    };
    /**
     * Konverter für Spieleobjekte.
     */
    private StringConverter<Spiel> spieleConverter = new StringConverter<>() {

        /**
         * Methode zur Umwandlung von Spieleobjekten in Strings
         * @param spiel Spielobjekt zur Konvertierung.
         * @return String mit Aufbau {@code Spiel 1 (erstellt am 01.01.1990) (aktiv)}; im Fehlerfall {@code ''}
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
         * @param spiel String zur Konvertierung.
         * @return {@code null}
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
         * @param periode Periode zur Konvertierung
         * @return String mit Aufbau {@code [id: 1]}; im Fehlerfall {@code ''}
         */
        @Override
        public String toString(Periode periode) {
            return periode != null ? " [id: " + periode.getId() + "]" : "";
        }

        /**
         * Methode zur Umwandlung von Strings in Periodenobjekte.
         * @param periode String zur Konvertierung.
         * @return {@code null}
         */
        @Override
        public Periode fromString(String periode) {
            return null;
        }
    };


    //+++++++++++++++Wertpapier++++++++++++++++++++++
    private StringConverter<Wertpapier> wertpapierConverter = new StringConverter<>() {

        @Override
        public String toString(Wertpapier wertpapier) {
            return wertpapier != null ? wertpapier.getName() + " (" + wertpapier.getUnternehmen().getName() + " - " + wertpapier.getWertpapierArt().getName() + ")"
                    + " | Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), wertpapier.getId()).orElseThrow(NoSuchElementException::new).getKursValue()) + "\u20ac" : "";
        }

        @Override
        public Wertpapier fromString(String id) {
            return null;
        }
    };


    //+++++++++++++++PortfolioPosition++++++++++++++++++++++
    private StringConverter<Portfolioposition> portfoliopositionConverter = new StringConverter<>() {

        @Override
        public String toString(Portfolioposition portfolioposition) {
            return portfolioposition != null ? portfolioposition.getWertpapier().getName() + " (" + portfolioposition.getWertpapier().getUnternehmen().getName() + " - " + portfolioposition.getWertpapier().getWertpapierArt().getName() + ")"
                    + " | Kurs: " + String.format("%.2f", KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKursValue()) + "\u20ac"
                    + " | Positionsgr\u00f6\u00dfe: " + String.format("%.2f", portfolioposition.getBezugsgroesse()) + "\u20ac"
                    + " (" + PortfolioFassade.getInstanz().getCountOfPositionen(AktuelleSpieldaten.getInstanz().getTeilnehmer().getId(), findAktuellePeriode().getId(), portfolioposition.getWertpapier().getId())
                    + " Stk.)" : "";
        }

        @Override
        public Portfolioposition fromString(String id) {
            return null;
        }
    };

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