package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ColorHelper;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class UnternehmenCell extends ListCell<Unternehmen> {

    @FXML
    public GridPane grdUnternehmen;
    @FXML
    private ColorPicker clrFarbe;
    @FXML
    private TextField txtUnternehmensname;
    @FXML
    private Button btnLoeschen;
    private FXMLLoader loader;

    // adaptiert von https://www.turais.de/how-to-custom-listview-cell-in-javafx/
    @Override
    protected void updateItem(Unternehmen unternehmen, boolean empty) {
        super.updateItem(unternehmen, empty);

        if (empty || unternehmen == null) {
            // setze einen leeren Eintrag
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_unternehmen.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            btnLoeschen.setOnAction(e -> getListView().getItems().remove(unternehmen));

            txtUnternehmensname.setText(unternehmen.getName());
            txtUnternehmensname.textProperty().addListener((observable, oldValue, newValue) -> unternehmen.setName(newValue));

            clrFarbe.setValue(ColorHelper.string2Color(unternehmen.getFarbe()));
            clrFarbe.valueProperty().addListener((observable, oldValue, newValue) -> {
                String farbe = ColorHelper.color2String(newValue);
                unternehmen.setFarbe(farbe);
            });
            setText(null);
            setGraphic(grdUnternehmen);
        }

    }
}
