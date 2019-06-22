package de.dhbw.karlsruhe.controller.factory;

import de.dhbw.karlsruhe.controller.fragments.HistorieCell;
import de.dhbw.karlsruhe.model.TeilnehmerPrintModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HistorieCellFactory implements Callback<TableColumn<TeilnehmerPrintModel, Void>, TableCell<TeilnehmerPrintModel, Void>> {

    @Override
    public TableCell<TeilnehmerPrintModel, Void> call( final TableColumn<TeilnehmerPrintModel, Void> param) {
        return new HistorieCell();
    }
}
