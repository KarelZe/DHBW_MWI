package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.jpa.Kurs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AktiePeriodeCell extends ListCell<Kurs> {

    @FXML
    private Label lblName;
    @FXML
    private TextField txtKurs;


    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
     */
    AktiePeriodeCell() {
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

    @Override
    public void updateItem(Kurs kurs, boolean empty) {
        super.updateItem(kurs, empty);

        if (kurs != null) {
            lblName.setText(kurs.getWertpapier().getName());
            txtKurs.setText(String.valueOf(kurs.getKurs()));
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @FXML
    private void initialize() {
        txtKurs.textProperty().addListener((observable, oldValue, newValue) -> {
                    // TODO: verbessertes Fehlerhandling
                    double kurs = 0;
                    try {
                        kurs = Double.valueOf(newValue);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    getItem().setKurs(kurs);
                }
        );
        //lblName.setText(getItem().getWertpapier().getName());
    }
}
