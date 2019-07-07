package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.PasswortCell;
import de.dhbw.karlsruhe.model.BenutzerViewModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Diese Klasse stellt Methoden für für die Überladung einer Table mit eigener {@link PasswortCell PasswortCells} bereit.
 *
 * <p>
 * Für Implementierung von CellFactories und eigenen Cell-Implementierungen siehe:
 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#cell-factories">https://docs.oracle.com/</a>
 * </p>
 * @author Markus Bilz
 */
public class PasswortCellFactory implements Callback<TableColumn<BenutzerViewModel, Void>, TableCell<BenutzerViewModel, Void>> {
    /**
     * Methode zur Erzeugung neuer Cells einer Table.
     *
     * @param param ListView, für das {@link PasswortCell} erzeugt wird
     * @return PasswortCell
     * @author Markus Bilz
     */
    public TableCell<BenutzerViewModel, Void> call(final TableColumn<BenutzerViewModel, Void> param) {
        return new PasswortCell();
    }
}
