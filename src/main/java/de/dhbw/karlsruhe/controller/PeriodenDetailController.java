package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.controller.fragments.PeriodeTab;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class PeriodenDetailController implements ControlledScreen {


    public TabPane tbPerioden;
    private ArrayList<Periode> perioden;
    private ScreenController screenController;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }


    @FXML
    private void initialize() {
        PeriodenRepository periodenModel = PeriodenRepository.getInstanz();
        perioden = new ArrayList<>(periodenModel.findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()));
        IntStream.rangeClosed(1, perioden.size()).forEach(p -> {
            Periode periode = perioden.get(p-1);
            //Null wird ausgelassen, da es sich hier um die leere Periode handelt die irgendwie mit start des Spiels errstellt wird
            if(p!=1){
                tbPerioden.getTabs().add(new PeriodeTab("Periode " + (p-1), periode));
            }
        });
    }

    //wird über instanzierten PeriodenDetailControllerr der ScreenController Klasse aufgerufen
    public void changePage(){
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }
}
