package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UnternehmenAnlegenController implements ControlledScreen {

    @FXML
    public Button btnSpeichern;
    @FXML
    public Label lblUnternehmen;
    @FXML
    private VBox vboxUnternehmen;


    private ArrayList<UnternehmenHBox> hBoxUnternehmenDynamisch;

    private ArrayList<Unternehmen> unternehmenInitial;

    public UnternehmenAnlegenController() {
        hBoxUnternehmenDynamisch = new ArrayList<>();
    }

    @FXML
    void doHinzufuegen(ActionEvent event) {
        // Füge Elemente vor Speichern Button hinzu
        UnternehmenHBox hBox = new UnternehmenHBox();
        vboxUnternehmen.getChildren().add(vboxUnternehmen.getChildren().size() - 1, hBox);
        System.out.println("hinzufuegen");
    }

    @FXML
    void doSpeichern(ActionEvent event) {

        // Erzeuge ArrayList aus dynamischen Feldern
        ArrayList<Unternehmen> unternehmenNachAenderung = new ArrayList<>();
        for (Node node : vboxUnternehmen.getChildren()) {
            if (node instanceof UnternehmenHBox) {
                unternehmenNachAenderung.add(((UnternehmenHBox) node).getUnternehmen());
            }
        }

        UnternehmenRepository.persistUnternehmen(unternehmenNachAenderung);

        // Lösche nicht benötigte Unternehmen aus Datenbank
        System.out.println(unternehmenNachAenderung);
        ArrayList<Unternehmen> unternehmenZumLoeschen = new ArrayList<>();
        for (Unternehmen u : unternehmenInitial)
            if (!unternehmenNachAenderung.contains(u)) {
                unternehmenZumLoeschen.add(u);
            }
        System.out.println(unternehmenInitial);
        System.out.println(unternehmenNachAenderung);
        System.out.println(unternehmenZumLoeschen);

        UnternehmenRepository.deleteUnternehmen(unternehmenZumLoeschen);

        // Setze initale Feldliste auf neuen Stand zurück.
        unternehmenInitial = unternehmenNachAenderung;
        System.out.println("speichern");
    }

    @FXML
    private void initialize() {
        // Füge zusätzliche Zeilen als dynamische Zeilen hinzu
        unternehmenInitial = UnternehmenRepository.getAlleUnternehmen();
        if (!unternehmenInitial.isEmpty()) {
            for (Unternehmen unternehmen : unternehmenInitial) {
                UnternehmenHBox hBox = new UnternehmenHBox(unternehmen);
                hBoxUnternehmenDynamisch.add(hBox);
                vboxUnternehmen.getChildren().add(2, hBox);
            }
        }
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
    }


}

