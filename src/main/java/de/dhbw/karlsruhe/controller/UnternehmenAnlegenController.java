package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.ArrayList;

public class UnternehmenAnlegenController implements ControlledScreen {

    @FXML
    public Button btnSpeichern;
    public ListView lstVwUnternehmen;
    ObservableList unternehmenObserverableList = FXCollections.observableArrayList();
    private ArrayList<Unternehmen> unternehmenList = new ArrayList<>();
    private ArrayList<Unternehmen> unternehmenListInitial = new ArrayList<>();

    @FXML
    void doSpeichern(ActionEvent event) {

        UnternehmenRepository.persistUnternehmen(unternehmenList);

        // Lösche nicht benötigte Unternehmen aus Datenbank
        System.out.println(unternehmenList);
        ArrayList<Unternehmen> unternehmenZumLoeschen = new ArrayList<>();
        for (Unternehmen u : unternehmenListInitial)
            if (!unternehmenList.contains(u)) {
                unternehmenZumLoeschen.add(u);
            }
        System.out.println(unternehmenListInitial);
        System.out.println(unternehmenList);
        System.out.println(unternehmenZumLoeschen);
        UnternehmenRepository.deleteUnternehmen(unternehmenZumLoeschen);

        unternehmenListInitial = unternehmenList;
        System.out.println("Speichern");
    }

    @FXML
    private void initialize() {
        setListView();
    }

    private void setListView() {
        unternehmenList = UnternehmenRepository.getAlleUnternehmen();
        unternehmenListInitial = UnternehmenRepository.getAlleSpielbarenUnternehmen();
        System.out.println(unternehmenList);
        unternehmenObserverableList.setAll(unternehmenList);
        lstVwUnternehmen.setCellFactory((Callback<ListView, ListCell<Unternehmen>>) param -> new UnternehmenCell());
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    public void doHinzufuegen(ActionEvent actionEvent) {
        lstVwUnternehmen.getItems().add(new UnternehmenCell());
        System.out.println("Hinzufügen Unternehmen anlegen.");
    }
}

