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
    public ListView lstVwUnternehmen;
    ObservableList unternehmenObserverableList = FXCollections.observableArrayList();
    private ArrayList<Unternehmen> unternehmenInitial = new ArrayList<>();

    @FXML
    void doSpeichern(ActionEvent event) {

        ArrayList<Unternehmen> unternehmenNachAenderung = new ArrayList<Unternehmen>(unternehmenObserverableList);
        UnternehmenRepository.persistUnternehmen(unternehmenNachAenderung);

        // Lösche nicht benötigte Unternehmen aus Datenbank
        ArrayList<Unternehmen> unternehmenZumLoeschen = new ArrayList<>();
        for (Unternehmen u : unternehmenInitial)
            if (!unternehmenNachAenderung.contains(u)) {
                unternehmenZumLoeschen.add(u);
            }
        UnternehmenRepository.deleteUnternehmen(unternehmenZumLoeschen);
        // Setze unternehmenIntial zurück
        unternehmenInitial = unternehmenNachAenderung;
        System.out.println("Speichern");
    }

    @FXML
    private void initialize() {
        unternehmenInitial = UnternehmenRepository.getAlleSpielbarenUnternehmen();
        unternehmenObserverableList.addAll(unternehmenInitial);
        lstVwUnternehmen.setItems(unternehmenObserverableList);
        lstVwUnternehmen.setCellFactory(studentListView -> new UnternehmenCell());
    }



    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    public void doHinzufuegen(ActionEvent actionEvent) {
        unternehmenObserverableList.add(new Unternehmen());
        System.out.println("Hinzufügen.");
    }
}

