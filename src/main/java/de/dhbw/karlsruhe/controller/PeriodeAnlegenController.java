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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class PeriodeAnlegenController implements ControlledScreen {

    private ScreenController screenController;

    private PeriodenRepository periodenRepository = PeriodenRepository.getInstanz();

    private WertpapierRepository wertpapierRepository = WertpapierRepository.getInstanz();

    private KursRepository kursRepository = KursRepository.getInstanz();

    @FXML
    private TextField txtZins, txtOrder;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void doPeriodeAnlegen(ActionEvent event) {
        double ordergebuehr, kapitalzins;
        try {
            ordergebuehr = Double.parseDouble(txtOrder.getText());
            kapitalzins = Double.parseDouble(txtZins.getText());
        }catch (NumberFormatException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Periode anlegen");
            alert.setContentText("Bitte geben Sie Zahlen ein.");
            alert.showAndWait();
            return;
        }
        Periode periode = new Periode(AktuelleSpieldaten.getSpiel(),  ordergebuehr, kapitalzins);
        periodenRepository.save(periode);

        List<Wertpapier> wertpapiere = wertpapierRepository.findAll();
        List<Kurs> kurse = wertpapiere.stream().map(wertpapier -> new Kurs(periode, wertpapier)).collect(Collectors.toList());
        kursRepository.save(kurse);

        screenController.setScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL);
    }

}
