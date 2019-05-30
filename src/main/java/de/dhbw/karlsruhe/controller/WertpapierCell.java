package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.JPA.Wertpapier;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class WertpapierCell extends ListCell<Wertpapier> {

    private GridPane pane;
    private Label lblName;
    private ComboBox<Unternehmen> cmbUnternehmen;
    private Button btnLoeschen;
    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
     */
    WertpapierCell() {
        super();
        btnLoeschen = new Button("-");
        btnLoeschen.setOnAction(event -> getListView().getItems().remove(getItem()));

        lblName = new Label();

        cmbUnternehmen = new ComboBox<>();
        ArrayList<Unternehmen> unternehmen = new ArrayList<>(UnternehmenRepository.getInstanz().findAllSpielbar());
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);
        cmbUnternehmen.setConverter(new ConverterHelper().getUnternehmensConverter());

        //String bezeichnung = getItem().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE ? "Aktie " : "Anleihe";

        String bezeichnung = "Aktie";
        //lblName.textProperty().bind(Bindings.concat(bezeichnung,cmbUnternehmen.valueProperty()));

        cmbUnternehmen.valueProperty().addListener((observable, oldValue, newValue) -> getItem().setUnternehmen(newValue));

        pane = new GridPane();
        pane.add(lblName, 0, 0);
        pane.add(cmbUnternehmen, 1, 0);
        pane.add(btnLoeschen, 2, 0);
    }

    @Override
    protected void updateItem(Wertpapier wertpapier, boolean empty) {
        super.updateItem(wertpapier, empty);

        if (wertpapier != null) {
            setText(null);
            setGraphic(pane);
        } else {
            setText(null);
            setGraphic(null);
        }
    }

}
