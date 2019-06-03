package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.List;
import java.util.stream.Collectors;

public class PeriodeAnlegenController implements ControlledScreen {

    private ScreenController screenController;

    private PeriodenRepository periodenRepository = PeriodenRepository.getInstanz();

    private WertpapierRepository wertpapierRepository = WertpapierRepository.getInstanz();

    private KursRepository kursRepository = KursRepository.getInstanz();

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void doPeriodeAnlegen(ActionEvent event) {
        //ToDo Carlos: TF einlesen usw.
        Periode periode = new Periode(AktuelleSpieldaten.getSpiel(), 0, 0);
        periodenRepository.save(periode);

        List<Wertpapier> wertpapiere = wertpapierRepository.findAll();
        List<Kurs> kurse = wertpapiere.stream().map(wertpapier -> new Kurs(periode, wertpapier)).collect(Collectors.toList());
        kursRepository.save(kurse);
    }

}
