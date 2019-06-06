package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.UnternehmenCell;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UnternehmenCellFactory implements Callback<ListView<Unternehmen>, ListCell<Unternehmen>> {

    @Override
    public ListCell<Unternehmen> call(ListView<Unternehmen> param) {
        return new UnternehmenCell();
    }
}
