package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.model.JPA.Spiel;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;

public class ConverterHelper {

    //+++++++++++++++UNTERNEHMEN++++++++++++++++++++++
    private StringConverter<Unternehmen> unternehmensConverter = new StringConverter<Unternehmen>() {
        @Override
        public String toString(Unternehmen unternehmen) {
            return unternehmen != null ? unternehmen.getName() + " [id: " + unternehmen.getId() + "]" : "";
        }

        @Override
        public Unternehmen fromString(String id) {
            return null;
        }
    };

    public StringConverter<Unternehmen> getUnternehmensConverter() {
        return unternehmensConverter;
    }

    public void setUnternehmensConverter(StringConverter<Unternehmen> unternehmensConverter) {
        this.unternehmensConverter = unternehmensConverter;
    }


    //+++++++++++++++SPIELE++++++++++++++++++++++
    private StringConverter<Spiel> spieleConverter = new StringConverter<Spiel>() {
        @Override
        public String toString(Spiel spiel) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            if(spiel != null) {
                StringBuilder sbAnzeige = new StringBuilder("Spiel " + spiel.getId());
                if (spiel.getErstellungsdatum() != null) {
                    sbAnzeige.append(" (erstellt am " + simpleDateFormat.format(spiel.getErstellungsdatum()) + ")");
                }
                if (spiel.getIst_aktiv() == Spiel.SPIEL_AKTIV) {
                    sbAnzeige.append(" (AKTIV)");
                }
                return sbAnzeige.toString();
            }
            else {
                return "NULL";
            }
        }

        @Override
        public Spiel fromString(String id) {
            return null;
        }
    };

    public StringConverter<Spiel> getSpieleConverter() {
        return spieleConverter;
    }

    public void setSpieleConverter(StringConverter<Spiel> spieleConverter) {
        this.spieleConverter = spieleConverter;
    }
}