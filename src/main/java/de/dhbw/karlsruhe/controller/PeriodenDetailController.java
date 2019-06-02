package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class PeriodenDetailController implements ControlledScreen {


    public TabPane tbPerioden;
    private ScreenController screenController;
    private ArrayList<Kurs> kurseIntial;
    private ArrayList<Periode> perioden;
    private KursRepository kurseModel;
    private PeriodenRepository periodenModel;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }


    @FXML
    private void initialize() {
        periodenModel = PeriodenRepository.getInstanz();
        kurseModel = KursRepository.getInstanz();
        perioden = new ArrayList<>(periodenModel.findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()));
        IntStream.rangeClosed(0, perioden.size()).forEach(p -> tbPerioden.getTabs().add(new PeriodeTab()));
    }
}
