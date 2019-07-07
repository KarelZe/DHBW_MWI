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
 * Diese Klasse stellt eine Cell für Aktienkurse zur Überladung eines ListViews zur Verfügung.
 *
 * @author Markus Bilz
 */
public class AktienPeriodeCell extends ListCell<Kurs> {

    @FXML
    private Label lblName;
    @FXML
    private TextField txtKurs;


    /**
     * Konstruktor für die Erzeugung einer {@link AktienPeriodeCell}. Der Konstruktor lädt die verbundene FXML und
     * initialisiert die enthaltenen UI-Elemente für einen späteren Zugriff.
     * @author Markus Bilz
     */
    public AktienPeriodeCell() {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_aktie_periode.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Diese Funktion aktualisiert eine Zeile einer ListView mit dem Inhalt des {@link Kurs}.
     * Sie wird durch die UI-Steuerung automatisch aufgerufen, sofern sich beispielsweise das
     * Kursobjekt verändert oder anderweitig das UI aktualsiert werden muss.
     * Sie soll ausschließlich automatisch durch das System aufgerufen werden.
     * Sofern das Kursobjekt {@code null} ist, wird ausschließlich eine leere Zeile angezeigt.
     *
     * @param kurs  Kurs, das in der Zeile angezeigt wird.
     * @param empty boolean, ob Zeile leer ist.
     * @author Markus Bilz
     */
    @Override
    public void updateItem(Kurs kurs, boolean empty) {
        super.updateItem(kurs, empty);
        System.out.println(kurs);
        if (kurs != null) {
            lblName.setText(kurs.getWertpapier().getName());
            txtKurs.setText(TextFormatHandler.CURRENCY_DECIMAL_FORMAT.format(kurs.getKurs()));
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
     * @author Markus Bilz
     */
    @FXML
    private void initialize() {
        txtKurs.setTextFormatter(TextFormatHandler.currencyFormatter());
        txtKurs.textProperty().addListener((observable, oldValue, newValue) -> getItem().setKurs(TextFormatHandler.getCurrencyFieldValue(newValue)));
    }
}
