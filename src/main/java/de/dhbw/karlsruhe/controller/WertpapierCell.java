package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Wertpapier;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

public class WertpapierCell extends ListCell<Wertpapier> {

    private GridPane pane;

    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
     */
    WertpapierCell() {
        super();

        pane = new GridPane();
    }

    @Override
    protected void updateItem(Wertpapier wertpapier, boolean empty) {
        super.updateItem(wertpapier, empty);

        if (wertpapier != null) {
            setText(null);
            setGraphic(pane);
        } else {
            setText(null);
            setGraphic(null);
        }
    }

}
