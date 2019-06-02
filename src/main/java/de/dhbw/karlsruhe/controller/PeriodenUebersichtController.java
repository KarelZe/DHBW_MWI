package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PeriodenUebersichtController implements ControlledScreen {


    public TextField txtAnzahlPerioden;
    private ScreenController screenController;
    private ArrayList<Periode> periodenIntial;
    private int anzahlInitial;
    private PeriodenRepository model;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    public void doSpeichern(ActionEvent event) {
        // TODO: Error Handling
        String anzahlPerioden = txtAnzahlPerioden.getText();
        int anzahlNachAenderung = Integer.valueOf(anzahlPerioden);

        // Lösche, sofern die Zahl der Perioden verringert wird. Ansonsten füge neue Periden ein.
        if (anzahlNachAenderung < anzahlInitial) {
            ArrayList<Periode> periodenZumLoeschen = new ArrayList<>(periodenIntial.subList(anzahlNachAenderung, anzahlInitial));
            model.delete(periodenZumLoeschen);
        } else {
            List<Periode> periodenZumEinfuegen = IntStream.range(anzahlInitial, anzahlNachAenderung).mapToObj(i -> new Periode(AktuelleSpieldaten.getSpiel(), 0, 0)).collect(Collectors.toCollection(ArrayList::new));
            PeriodenRepository.getInstanz().save(periodenZumEinfuegen);
        }
        // Frage neue Perioden aus DB ab.
        periodenIntial = new ArrayList<>(model.findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()));
        anzahlInitial = periodenIntial.size();

    }

    @FXML
    private void initialize() {
        model = PeriodenRepository.getInstanz();
        periodenIntial = new ArrayList<>(model.findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()));
        anzahlInitial = periodenIntial.size();
        txtAnzahlPerioden.setText(String.valueOf(anzahlInitial));
    }
}
