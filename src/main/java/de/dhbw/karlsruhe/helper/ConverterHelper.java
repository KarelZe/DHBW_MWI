package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import javafx.util.StringConverter;

public class ConverterHelper {


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
}