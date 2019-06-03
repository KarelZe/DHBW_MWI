package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.*;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.List;

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
        Periode periode = new Periode();
        periode.setSpiel(AktuelleSpieldaten.getSpiel());
        periodenRepository.save(periode);

        insertKursObjekte(periode);

    }

    private void insertKursObjekte(Periode periode) {
        List<Wertpapier> alleWertpapiere = wertpapierRepository.findAll();
        for(Wertpapier aktuellesWertpapier : alleWertpapiere) {
            Kurs kurs = new Kurs();
            kurs.setPeriode(periode);
            kurs.setWertpapier(aktuellesWertpapier);
            kurs.setKurs(0L);
            kurs.setSpread(Double.valueOf(0));
            kursRepository.save(kurs);
        }
    }
}
