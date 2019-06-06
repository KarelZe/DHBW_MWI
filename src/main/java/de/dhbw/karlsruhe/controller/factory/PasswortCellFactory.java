package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.PasswortCell;
import de.dhbw.karlsruhe.model.TeilnehmerViewModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PasswortCellFactory implements Callback<TableColumn<TeilnehmerViewModel, Void>, TableCell<TeilnehmerViewModel, Void>> {

    public TableCell<TeilnehmerViewModel, Void> call(final TableColumn<TeilnehmerViewModel, Void> param) {
        return new PasswortCell();
    }
}
