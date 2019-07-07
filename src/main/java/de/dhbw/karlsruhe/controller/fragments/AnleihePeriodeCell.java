package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.handler.TextFormatHandler;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Diese Klasse stellt eine Cell für Anleihenkurse zur Überladung eines ListViews zur Verfügung.
 *
 * @author Markus Bilz
 */
public class AnleihePeriodeCell extends ListCell<Kurs> {

    @FXML
    private Label lblName;
    @FXML
    private TextField txtSpread;
    @FXML
    private TextField txtKursManuell;


    /**
     * Konstruktor für die Erzeugung einer {@code AnleihePeriodeCell}. Der Konstruktor lädt die verbundene FXML und
     * initialisiert die enthaltenen UI-Elemente für einen späteren Zugriff.
     */
    public AnleihePeriodeCell() {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_anleihe_periode.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Diese Funktion aktualisiert eine Zeile einer ListView mit dem Inhalt des {@code Kurs}.
     * Sie wird durch die UI-Steuerung automatisch aufgerufen, sofern sich beispielsweise das
     * Kursobjekt verändert oder anderweitig das UI aktualisiert werden muss.
     * Sie soll ausschließlich automatisch durch das System aufgerufen werden.
     * Sofern das Kursobjekt {@code null} ist, wird ausschließlich eine leere Zeile angezeigt.
     *
     * @param kurs  Kurs, das in der Zeile angezeigt wird.
     * @param empty boolean, ob Zeile leer ist.
     */
    @Override
    public void updateItem(Kurs kurs, boolean empty) {
        super.updateItem(kurs, empty);

        if (kurs != null) {
            lblName.setText(kurs.getWertpapier().getName());
            txtSpread.setText(kurs.getSpread() == null ? TextFormatHandler.PERCENTAGE_DECIMAL_FORMAT.format(0.0) : TextFormatHandler.PERCENTAGE_DECIMAL_FORMAT.format(kurs.getSpread()));
            txtKursManuell.setText(kurs.getManuellerKurs() == null ? TextFormatHandler.PERCENTAGE_DECIMAL_FORMAT.format(0.0) : TextFormatHandler.PERCENTAGE_DECIMAL_FORMAT.format(kurs.getManuellerKurs() * 0.01d));
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
        txtSpread.setTextFormatter(TextFormatHandler.percentageFormatter());
        txtKursManuell.setTextFormatter(TextFormatHandler.percentageFormatter());
        txtSpread.textProperty().addListener((observable, oldValue, newValue) -> getItem().setSpread(TextFormatHandler.getPercentageFieldValue(newValue)));
        txtKursManuell.textProperty().addListener((observable, oldValue, newValue) -> getItem().setManuellerKurs(TextFormatHandler.getPercentageFieldValue(newValue) * 100.0d));
    }
}
