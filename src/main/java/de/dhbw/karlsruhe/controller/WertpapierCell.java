package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class WertpapierCell extends ListCell<Wertpapier> {

    private GridPane pane;

    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
     */
    WertpapierCell() {
        super();
        Button btnLoeschen = new Button("-");
        btnLoeschen.setOnAction(event -> getListView().getItems().remove(getItem()));

        TextField txtName = new TextField();
        txtName.setPromptText("Bezeichnung des Wertpapiers");

        ComboBox<Unternehmen> cmbUnternehmen = new ComboBox<>();
        ArrayList<Unternehmen> unternehmen = new ArrayList<>(UnternehmenRepository.getInstanz().findAllSpielbar());
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);
        cmbUnternehmen.setConverter(new ConverterHelper().getUnternehmensConverter());

        //String bezeichnung = getItem().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE ? "Aktie " : "Anleihe";

        String bezeichnung = "Aktie";
        //lblName.textProperty().bind(Bindings.concat(bezeichnung,cmbUnternehmen.valueProperty()));

        cmbUnternehmen.valueProperty().addListener((observable, oldValue, newValue) -> getItem().setUnternehmen(newValue));

        pane = new GridPane();
        pane.add(txtName, 0, 0);
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
