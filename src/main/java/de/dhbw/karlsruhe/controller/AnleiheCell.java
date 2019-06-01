package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementierung der Klasse orientiert sich an // https://stackoverflow.com/questions/47511132/javafx-custom-listview
 */
public class AnleiheCell extends ListCell<Wertpapier> {


    @FXML
    private ComboBox<Unternehmen> cmbUnternehmen;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmissionsspread;
    @FXML
    private Button btnLoeschen;

    // TODO: Error handling?

    /**
     * Konstruktor für die Erzeugung einer Zeile. Die Initalisierung der Listener erfolgt aus Performanzgründen im
     * Konstruktor. Siehe hierzu https://stackoverflow.com/a/36436734 und
     * https://stackoverflow.com/a/31988574.
     */
    AnleiheCell() {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_anleihe.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Wertpapier wertpapier, boolean empty) {
        super.updateItem(wertpapier, empty);

        if (wertpapier != null) {
            cmbUnternehmen.getSelectionModel().select(wertpapier.getUnternehmen());
            txtName.setText(wertpapier.getName());
            txtEmissionsspread.setText(String.valueOf(wertpapier.getEmissionszins()));
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @FXML
    private void initialize() {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> getItem().setName(newValue));
        txtEmissionsspread.textProperty().addListener((observable, oldValue, newValue) -> getItem().setEmissionszins(Double.valueOf(newValue)));

        ArrayList<Unternehmen> unternehmen = new ArrayList<>(UnternehmenRepository.getInstanz().findAllSpielbar());
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);
        cmbUnternehmen.setConverter(new ConverterHelper().getUnternehmensConverter());

        cmbUnternehmen.valueProperty().addListener((observable, oldValue, newValue) -> getItem().setUnternehmen(newValue));
        btnLoeschen.setOnAction(event -> getListView().getItems().remove(getItem()));
    }
}
