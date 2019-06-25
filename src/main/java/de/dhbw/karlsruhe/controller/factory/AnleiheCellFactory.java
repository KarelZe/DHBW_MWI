package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.AnleiheCell;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung von ListViews mit eigenen {@code AnleiheCell} bereit.
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 *
 * @author Markus Bilz
 * @see <a href="http://oracle.com">https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories</a>.
 */
public class AnleiheCellFactory implements Callback<ListView<Wertpapier>, ListCell<Wertpapier>> {
    /**
     * Methode zur Erzeugung neuer Cells eines ListViews.
     *
     * @param param ListView, für das {@code AnleiheCell} erzeugt wird
     * @return AnleiheCell
     */
    @Override
    public ListCell<Wertpapier> call(ListView<Wertpapier> param) {
        return new AnleiheCell();
    }
}
