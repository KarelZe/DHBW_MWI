package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.AktienPeriodeCell;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung von ListViews mit eigenen {@link AktienPeriodeCell AktienPeriodeCells} bereit.
 *
 * <p>
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories">https://docs.oracle.com/</a>
 * </p>
 *
 * @author Markus Bilz
 *  */
public class AktienPeriodeCellFactory implements Callback<ListView<Kurs>, ListCell<Kurs>> {
    /**
     * Methode zur Erzeugung neuer Cells eines ListViews.
     *
     * @param param ListView, für das {@link AktienPeriodeCell} erzeugt wird
     * @return AktienPeriodeCell
     * @author Markus Bilz
     */
    @Override
    public ListCell<Kurs> call(ListView<Kurs> param) {
        return new AktienPeriodeCell();
    }
}
