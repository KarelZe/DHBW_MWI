package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.helper.NumberHelper;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Implementierung der Klasse orientiert sich an // https://stackoverflow.com/questions/47511132/javafx-custom-listview
 */
public class AnleihePeriodeCell extends ListCell<Kurs> {

    @FXML
    private Label lblName;
    @FXML
    private TextField txtSpread;
    @FXML
    private TextField txtKursInsolvenz;


    // TODO: Error handling?

    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
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

    @Override
    public void updateItem(Kurs kurs, boolean empty) {
        super.updateItem(kurs, empty);

        if (kurs != null) {
            lblName.setText(kurs.getWertpapier().getName());
            // Spread ist nullable in DB -> Konvertiere in %
            txtSpread.setText(kurs.getSpread() == null ? "0.00d" : String.valueOf(kurs.getSpread() * 100.00d));
            txtKursInsolvenz.setText(String.valueOf(kurs.getKursValue()));
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @FXML
    private void initialize() {
        // Konvertiere BP in %
        txtSpread.textProperty().addListener((observable, oldValue, newValue) -> getItem().setSpread(NumberHelper.parseDouble(newValue, 0) / 100));
        txtKursInsolvenz.textProperty().addListener((observable, oldValue, newValue) -> getItem().setKursValue(NumberHelper.parseDouble(newValue, 0)));
    }
}
