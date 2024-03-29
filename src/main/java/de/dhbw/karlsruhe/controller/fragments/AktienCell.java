package de.dhbw.karlsruhe.controller.fragments;

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
 * Diese Klasse stellt eine Cell für Aktien zur Überladung eines ListViews zur Verfügung.
 *
 * @author Markus Bilz
 */

public class AktienCell extends ListCell<Wertpapier> {

    @FXML
    private ComboBox<Unternehmen> cmbUnternehmen;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnLoeschen;

    /**
     * Konstruktor für die Erzeugung einer {@link AktienCell}. Der Konstruktor lädt die verbundene FXML und
     * initialisiert die enthaltenen UI-Elemente für einen späteren Zugriff.
     * @author Markus Bilz
     */
    public AktienCell() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_aktie.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Diese Funktion aktualisiert eine Zeile einer ListView mit dem Inhalt des {@link Wertpapier}.
     * Sie wird durch die UI-Steuerung automatisch aufgerufen, sofern sich beispielsweise das
     * Wertpapierobjekt verändert oder anderweitig das UI aktualisiert werden muss.
     * Sie soll ausschließlich automatisch durch das System aufgerufen werden.
     * Sofern das Wertpapierobjekt {@code null} ist, wird ausschließlich eine leere Zeile angezeigt.
     *
     * @param wertpapier Wertpapier, das in der Zeile angezeigt wird.
     * @param empty      boolean, ob Zeile leer ist.
     * @author Markus Bilz
     */
    @Override
    public void updateItem(Wertpapier wertpapier, boolean empty) {
        super.updateItem(wertpapier, empty);

        if (wertpapier != null) {
            cmbUnternehmen.getSelectionModel().select(wertpapier.getUnternehmen());
            txtName.setText(wertpapier.getName());
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

        ArrayList<Unternehmen> unternehmen = new ArrayList<>(UnternehmenRepository.getInstanz().findByUnternehmenArt(Unternehmen.UNTERNEHMEN_TEILNEHMER));
        ObservableList<Unternehmen> unternehmenComboBox = FXCollections.observableArrayList(unternehmen);
        cmbUnternehmen.setItems(unternehmenComboBox);
        cmbUnternehmen.setConverter(new ConverterHelper().getUnternehmensConverter());
        cmbUnternehmen.valueProperty().addListener((observable, oldValue, newValue) -> getItem().setUnternehmen(newValue));

        btnLoeschen.setOnAction(event -> getListView().getItems().remove(getIndex()));
        txtName.textProperty().addListener((observable, oldValue, newValue) -> getItem().setName(newValue));

    }
}
