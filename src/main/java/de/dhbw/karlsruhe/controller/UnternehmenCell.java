package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;

public class UnternehmenCell extends ListCell<Unternehmen> {


    @Override
    protected void updateItem(Unternehmen item, boolean empty) {
        super.updateItem(item, empty);
    }

    public void doHinzufuegen(ActionEvent actionEvent) {
        System.out.println("Hinzufuegen ListView Item");
    }
}
