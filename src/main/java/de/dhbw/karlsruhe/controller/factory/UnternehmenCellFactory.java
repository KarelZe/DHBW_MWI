package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.UnternehmenCell;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung von ListViews mit eigenen {@code UnternehmenCell} bereit.
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 *
 * @author Markus Bilz
 * @see <a href="http://oracle.com">https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories</a>.
 */
public class UnternehmenCellFactory implements Callback<ListView<Unternehmen>, ListCell<Unternehmen>> {
    /**
     * Methode zur Erzeugung neuer Cells einer Table.
     *
     * @param param ListView, für das {@code UnternehmenCell} erzeugt wird
     * @return UnternehmenCell
     */
    @Override
    public ListCell<Unternehmen> call(ListView<Unternehmen> param) {
        return new UnternehmenCell();
    }
}
