package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ColorHelper;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class UnternehmenCell extends ListCell<Unternehmen> {

    private TextField txtUnternehmensname;
    private ColorPicker clrFarbe;
    private GridPane pane;

    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
     */
    UnternehmenCell() {
        super();

        Button btnLoeschen = new Button("-");
        btnLoeschen.setOnAction(event -> getListView().getItems().remove(getItem()));

        clrFarbe = new ColorPicker();
        clrFarbe.valueProperty().addListener((observable, oldValue, newValue) -> getItem().setFarbe(ColorHelper.color2String(newValue)));

        txtUnternehmensname = new TextField();
        txtUnternehmensname.textProperty().addListener((observable, oldValue, newValue) -> getItem().setName(newValue));

        pane = new GridPane();
        pane.add(txtUnternehmensname, 0, 0);
        pane.add(clrFarbe, 1, 0);
        pane.add(btnLoeschen, 2, 0);
    }

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

        if (unternehmen != null) {
            txtUnternehmensname.setText(unternehmen.getName());
            clrFarbe.setValue(ColorHelper.string2Color(unternehmen.getFarbe()));
            setText(null);
            setGraphic(pane);
        } else {
            setText(null);
            setGraphic(null);
        }
    }

}
