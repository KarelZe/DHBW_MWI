package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.controller.fragments.PeriodeTab;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Diese Klasse ist der Controller für die Pflege der Perioden.
 *
 * @author Markus Bilz
 */

public class PeriodenDetailController implements ControlledScreen {


    public TabPane tbPerioden;
    private ArrayList<Periode> perioden;
    private ScreenController screenController;

    /**
     * Konkrete Implementierung für den Zugriff auf den Controller des übergeordneten Screens
     *
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    /**
     * Methode ist Bestandteil des Lifecycles von JavaFX und initialisiert UI-Elementen für die
     * spätere Verwendung. Sie erzeugt dynamisch alle sichtbaren Tabs für die Perioden eines Spiels.
     * Dabei werden alle Perioden mit Ausnahme einer Periode, die bei Initialisierung des Spiels zur Verbuchung des
     * Startkapitals erzeugt wird, angelegt.
     */
    @FXML
    private void initialize() {
        PeriodenRepository periodenModel = PeriodenRepository.getInstanz();
        perioden = new ArrayList<>(periodenModel.findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()));
        IntStream.rangeClosed(1, perioden.size()).forEach(p -> {
            Periode periode = perioden.get(p - 1);
            //Null wird ausgelassen, da es sich hier um eine Periode handelt, die mit Start des Siels erzeugt wird.
            // TODO: Prüfen, ob das nicht einfacher geht, indem Range bei zwei beginnt?
            if(p!=1){
                tbPerioden.getTabs().add(new PeriodeTab("Periode " + (p-1), periode));
            }
        });
    }

    /**
     * Methode für den Wechsel von aktuellem Screen auf Login Screen.
     */
    public void changePage(){
        screenController.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }
}
