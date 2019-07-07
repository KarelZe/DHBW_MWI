package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.AnleiheCell;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung von ListViews mit eigenen {@link AnleiheCell AnleiheCells} bereit.
 *
 * <p>
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories">https://docs.oracle.com/</a>
 * </p>
 * @author Markus Bilz
 */
public class AnleiheCellFactory implements Callback<ListView<Wertpapier>, ListCell<Wertpapier>> {
    /**
     * Methode zur Erzeugung neuer Cells eines ListViews.
     *
     * @param param ListView, für das {@link AnleiheCell} erzeugt wird
     * @return AnleiheCell
     * @author Markus Bilz
     */
    @Override
    public ListCell<Wertpapier> call(ListView<Wertpapier> param) {
        return new AnleiheCell();
    }
}
