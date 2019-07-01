package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.HistorieCell;
import de.dhbw.karlsruhe.model.BenutzerPrintModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung einer Table mit eigener {@code HistorieCell} bereit.
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 *
 * @author Markus Bilz
 * @see <a href="http://oracle.com">https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories</a>.
 */
public class HistorieCellFactory implements Callback<TableColumn<BenutzerPrintModel, Void>, TableCell<BenutzerPrintModel, Void>> {

    /**
     * Methode zur Erzeugung neuer Cells einer Table.
     *
     * @param param ListView, für das {@code HistorieCell} erzeugt wird
     * @return HistorieCell
     */
    @Override
    public TableCell<BenutzerPrintModel, Void> call(final TableColumn<BenutzerPrintModel, Void> param) {
        return new HistorieCell();
    }
}
