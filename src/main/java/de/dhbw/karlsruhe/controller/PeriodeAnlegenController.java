package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.handler.TextFormatHandler;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Controller für das Anlegen einer Periode
 * @author Jan Carlos Riecken
 */
public class PeriodeAnlegenController implements ControlledScreen {
    @FXML
    private Button btnAnlegen;
    private ScreenController screenController;

    private PeriodenRepository periodenRepository = PeriodenRepository.getInstanz();

    private WertpapierRepository wertpapierRepository = WertpapierRepository.getInstanz();

    private KursRepository kursRepository = KursRepository.getInstanz();

    @FXML
    private TextField txtKapitalmarktzins, txtOrdergebuehr;

    private NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.GERMANY);

    /**
     * Konkrete Implementierung für den Zugriff auf den Controller des übergeordneten Screens
     *
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    /**
     * Event-Handler für Periode anlegen
     * @param event Event
     * @author Jan Carlos Riecken
     */
    @FXML
    private void doPeriodeAnlegen(ActionEvent event) {
        double ordergebuehr, kapitalmarktzins;
        // Lese Eingabewerte aus und konvertiere in 5 % -> 0.05
        kapitalmarktzins = TextFormatHandler.getPercentageFieldValue(txtKapitalmarktzins);
        ordergebuehr = TextFormatHandler.getPercentageFieldValue(txtOrdergebuehr);
        kapitalmarktzins *= 0.01d;
        ordergebuehr *= 0.01d;
        System.out.println("{" + ordergebuehr + "," + kapitalmarktzins + "}");

        if (ordergebuehr < 0 || kapitalmarktzins < 0 || ordergebuehr > 0.5 || kapitalmarktzins > 0.5) {
            StringBuilder fehlermeldung = new StringBuilder();
            if (ordergebuehr < 0 || ordergebuehr > 0.5)
                fehlermeldung.append("Die Ordergeb\u00fchr muss zwischen - 50 % und + 50 % liegen.\n");
            if (kapitalmarktzins < 0 || kapitalmarktzins > 0.5)
                fehlermeldung.append("Der Kapitalmarktzins muss zwischen - 50 % und + 50 % liegen.\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ung\u00fcltige Eingabe");
            alert.setContentText(fehlermeldung.toString());
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        Periode periode = new Periode(AktuelleSpieldaten.getInstanz().getSpiel(), ordergebuehr, kapitalmarktzins);
        periode.setIst_aktiv(Periode.PERIODE_AKTIV);
        periodenRepository.save(periode);

        //Kurs-Objekt von Aktien und Anleihen erzeugen, sodass dieser in der nachfolgenden Übersicht angezeigt werden
        List<Wertpapier> wertpapiere = wertpapierRepository.findAll();

        List<Kurs> kurse = wertpapiere.stream().map(wertpapier -> new Kurs(periode, wertpapier)).collect(Collectors.toList());
        // Spread ist nullable Initialisiere Spread bei Anleihen mit 0.
        kurse.stream().filter(k -> k.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).forEach(kurs -> kurs.setSpread(0.0d));

        kursRepository.save(kurse);
        screenController.loadScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL, ScreensFramework.SCREEN_PERIODEN_DETAIL_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_PERIODEN_DETAIL);
    }

    /**
     * Initialisierung
     * @author Jan Carlos Riecken
     */
    @FXML
    private void initialize() {
        BooleanBinding booleanBind = Bindings.or(txtKapitalmarktzins.textProperty().isEmpty(),
                txtOrdergebuehr.textProperty().isEmpty());
        btnAnlegen.disableProperty().bind(booleanBind);
        // Formatiere Eingabewerte
        txtOrdergebuehr.setTextFormatter(TextFormatHandler.percentageFormatter());
        txtKapitalmarktzins.setTextFormatter(TextFormatHandler.percentageFormatter());
    }
}
