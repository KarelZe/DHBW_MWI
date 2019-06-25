package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.AnleihePeriodeCell;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung von ListViews mit eigenen {@code AnleihePeriodeCell} bereit.
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 *
 * @author Markus Bilz
 * @see <a href="http://oracle.com">https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories</a>.
 */
public class AnleihePeriodeCellFactory implements Callback<ListView<Kurs>, ListCell<Kurs>> {
    /**
     * Methode zur Erzeugung neuer Cells eines ListViews.
     *
     * @param param ListView, für das {@code AnleihePeriodeCell} erzeugt wird
     * @return AnleihePeriodeCell
     */
    @Override
    public ListCell<Kurs> call(ListView<Kurs> param) {
        return new AnleihePeriodeCell();
    }
}
