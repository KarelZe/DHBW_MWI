package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.helper.ColorHelper;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class UnternehmenCell extends ListCell<Unternehmen> {

    @FXML
    private TextField txtName;
    @FXML
    private ColorPicker clrFarbe;
    @FXML
    private Button btnLoeschen;

    /**
     * Konstruktor für die Erzeugung einer {@code UnternehmenCell}. Der Konstruktor lädt die verbundene FXML und
     * initialisiert die enthaltenen UI-Elemente für einen späteren Zugriff.
     */
    public UnternehmenCell() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_unternehmen.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Diese Funktion aktualisiert eine Zeile einer ListView mit dem Inhalt des {@code Unternehmen}.
     * Sie wird durch die UI-Steuerung automatisch aufgerufen, sofern sich beispielsweise das
     * Unternehmensobjekt verändert oder anderweitig das UI atkualisiert werden muss.
     * Sie soll nicht durch den Programmierer aufgerufen werden und wird automatisch durch das System aufgerufen.
     * Sofern das Unternehmensobjekt {@code null} ist, wird ausschließlich eine leere Zeile angezeigt.
     *
     * @param unternehmen Unternehmen, das in der Zeile angezeigt wird.
     * @param empty       boolean, ob Zeile leer ist.
     */
    @Override
    protected void updateItem(Unternehmen unternehmen, boolean empty) {
        super.updateItem(unternehmen, empty);

        if (unternehmen != null) {
            txtName.setText(unternehmen.getName());
            clrFarbe.setValue(ColorHelper.string2Color(unternehmen.getFarbe()));
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    /**
     * Diese Methode ist Bestandteil des Lifecycles von JavaFX und initialisiert die Listener von UI-Elementen der Cell
     * für die spätere Verwendung.
     */
    @FXML
    private void initialize() {
        btnLoeschen.setOnAction(event -> getListView().getItems().remove(getItem()));
        clrFarbe.valueProperty().addListener((observable, oldValue, newValue) -> getItem().setFarbe(ColorHelper.color2String(newValue)));
        txtName.textProperty().addListener((observable, oldValue, newValue) -> getItem().setName(newValue));
    }
}
