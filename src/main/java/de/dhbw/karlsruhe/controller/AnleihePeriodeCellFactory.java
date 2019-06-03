package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class AnleihePeriodeCellFactory implements Callback<ListView<Kurs>, ListCell<Kurs>> {

    @Override
    public ListCell<Kurs> call(ListView<Kurs> param) {
        return new AnleihePeriodeCell();
    }
}
