package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.AktiePeriodeCell;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class AktienPeriodeCellFactory implements Callback<ListView<Kurs>, ListCell<Kurs>> {

    @Override
    public ListCell<Kurs> call(ListView<Kurs> param) {
        return new AktiePeriodeCell();
    }
}
