package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.PasswortCell;
import de.dhbw.karlsruhe.model.TeilnehmerViewModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung einer Table mit eigener {@code PasswortCell} bereit.
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 *
 * @author Markus Bilz
 * @see <a href="http://oracle.com">https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories</a>.
 */
public class PasswortCellFactory implements Callback<TableColumn<TeilnehmerViewModel, Void>, TableCell<TeilnehmerViewModel, Void>> {
    /**
     * Methode zur Erzeugung neuer Cells einer Table.
     *
     * @param param ListView, für das {@code PasswortCell} erzeugt wird
     * @return PasswortCell
     */
    public TableCell<TeilnehmerViewModel, Void> call(final TableColumn<TeilnehmerViewModel, Void> param) {
        return new PasswortCell();
    }
}
