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

    /**
     * Diese Funktion aktualisiert eine Zeile einer ListView mit dem Inhalt des Unternehmens-Parameter.
     * Sie durch die UI-Steuerung automatisch aufgerufen, sofern sich beispielsweise das
     * Unternehmensobjekt verändert oder anderweitig das UI upgedatet werden muss.
     * Sie soll nicht durch den Programmierer aufgerufen werden.
     * Die Implmentierung wurde adaptiert von https://www.turais.de/how-to-custom-listview-cell-in-javafx/.
     *
     * @param unternehmen Unternehmen, das in der Zeile angezeigt wird.
     * @param empty       boolean, ob Zeile leer ist.
     */
    @Override
    protected void updateItem(Unternehmen unternehmen, boolean empty) {
        super.updateItem(unternehmen, empty);

        // Setze einen leeren Text und eine leere Grafik, sofern die Zeile leer ist oder das Unternehmensobjekt null.
        if (empty || unternehmen == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Lade FXML der Zelle, sofern nicht bereits vorhanden.
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_unternehmen.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Initialisiere UI Elemente aus FXML.
            btnLoeschen.setOnAction(e -> getListView().getItems().remove(unternehmen));

            txtUnternehmensname.setText(unternehmen.getName());
            txtUnternehmensname.textProperty().addListener((observable, oldValue, newValue) -> unternehmen.setName(newValue));

            clrFarbe.setValue(ColorHelper.string2Color(unternehmen.getFarbe()));
            clrFarbe.valueProperty().addListener((observable, oldValue, newValue) -> {
                unternehmen.setFarbe(ColorHelper.color2String(newValue));
            });
            // Setze Zeileninhalt
            setText(null);
            setGraphic(grdUnternehmen);
        }

    }
}
