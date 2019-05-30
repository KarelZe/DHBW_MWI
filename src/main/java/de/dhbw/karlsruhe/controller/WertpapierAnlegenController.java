package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Wertpapier;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class WertpapierAnlegenController implements ControlledScreen {

    @FXML
    public Button btnSpeichern;
    public ListView<Wertpapier> lstVwWertpapier;
    private ObservableList<Wertpapier> wertpapierObserverableList = FXCollections.observableArrayList();
    private ArrayList<Wertpapier> wertpapierInitial = new ArrayList<>();
    private WertpapierRepository model;

    @FXML
    void doSpeichern(ActionEvent event) {

        // Aktualisiere alle Unternehmen und füge sofern notwendig neue der Datenbank hinzu
        ArrayList<Wertpapier> wertpapierNachAenderung = new ArrayList<Wertpapier>(wertpapierObserverableList);
        model.save(wertpapierNachAenderung);

        /* Lösche nicht benötigte Unternehmen aus Datenbank. Durchlaufe hierfür unternehmenNachAenderung.
         * contains() greift für einen Vergleich auf Gleichheit auf die equals() Methode der Klasse Unternehmen zurück.
         */
        ArrayList<Wertpapier> wertpapierZumLoeschen = new ArrayList<>();
        for (final Wertpapier w : wertpapierInitial)
            if (!wertpapierNachAenderung.contains(w)) {
                wertpapierZumLoeschen.add(w);
            }
        model.delete(wertpapierZumLoeschen);

        /* Setze unternehmenIntial zurück, andernfalls würde bei erneuter Speicherung versucht werden, das
        Unternehmen erneut zu löschen.*/
        wertpapierInitial = wertpapierNachAenderung;
    }

    /**
     * Funktion initalisiert die ListView mit Unternehmen, sofern vorhanden.
     */
    @FXML
    private void initialize() {

        model = WertpapierRepository.getInstanz();
        wertpapierInitial = new ArrayList<>(model.findAll());
        wertpapierObserverableList.addAll(wertpapierInitial);
        lstVwWertpapier.setItems(wertpapierObserverableList);
        lstVwWertpapier.setCellFactory(studentListView -> new WertpapierCell());

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
        wertpapierObserverableList.add(new Wertpapier());
    }
}

