package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class AktienCellFactory implements Callback<ListView<Wertpapier>, ListCell<Wertpapier>> {

    @Override
    public ListCell<Wertpapier> call(ListView<Wertpapier> param) {
        return new AktienCell();
    }
}
