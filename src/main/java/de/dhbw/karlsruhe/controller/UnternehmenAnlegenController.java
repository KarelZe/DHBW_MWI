package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class UnternehmenAnlegenController implements ControlledScreen {

    @FXML
    public Button btnSpeichern;
    public ListView<Unternehmen> lstVwUnternehmen;
    private ObservableList<Unternehmen> unternehmenObserverableList = FXCollections.observableArrayList();
    private ArrayList<Unternehmen> unternehmenInitial = new ArrayList<>();

    private UnternehmenRepository model;

    @FXML
    void doSpeichern(ActionEvent event) {

        // Aktualisiere alle Unternehmen und füge sofern notwendig neue der Datenbank hinzu
        ArrayList<Unternehmen> unternehmenNachAenderung = new ArrayList<Unternehmen>(unternehmenObserverableList);
        model.save(unternehmenNachAenderung);

        /* Lösche nicht benötigte Unternehmen aus Datenbank. Durchlaufe hierfür unternehmenNachAenderung.
         * contains() greift für einen Vergleich auf Gleichheit auf die equals() Methode der Klasse Unternehmen zurück.*/
        ArrayList<Unternehmen> unternehmenZumLoeschen = new ArrayList<>();
        for (Unternehmen u : unternehmenInitial)
            if (!unternehmenNachAenderung.contains(u)) {
                unternehmenZumLoeschen.add(u);
            }
        model.delete(unternehmenZumLoeschen);

        /* Setze unternehmenIntial zurück, andernfalls würde bei erneuter Speicherung versucht werden, das
        Unternehmen erneut zu löschen.*/
        unternehmenInitial = unternehmenNachAenderung;
    }

    /**
     * Funktion initalisiert die ListView mit Unternehmen, sofern vorhanden.
     */
    @FXML
    private void initialize() {
        model = UnternehmenRepository.getInstanz();
        unternehmenInitial = new ArrayList<>(model.findAllSpielbar());
        unternehmenObserverableList.addAll(unternehmenInitial);
        lstVwUnternehmen.setItems(unternehmenObserverableList);
        lstVwUnternehmen.setCellFactory(unternehmenListView -> new UnternehmenCell());
    }



    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    /**
     * Methode fügt der ObsverableList, welche die Unternehmensobjekte für die ListView enthält, weitere Unternehmen
     * hinzu.
     *
     * @param actionEvent ActionEvent des aufrufenden Buttons
     */
    public void doHinzufuegen(ActionEvent actionEvent) {
        unternehmenObserverableList.add(new Unternehmen());
    }
}

