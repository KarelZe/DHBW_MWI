package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.controller.factory.UnternehmenCellFactory;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Diese Klasse implementiert den Controller für die Anlage von Unternehmen.
 *
 * @author Markus Bilz
 */

public class UnternehmenAnlegenController implements ControlledScreen {

    @FXML
    public Button btnSpeichern;
    public ListView<Unternehmen> lstVwUnternehmen;
    private ObservableList<Unternehmen> unternehmenObserverableList;
    private ArrayList<Unternehmen> unternehmenInitial;

    private UnternehmenRepository model;
    private ScreenController screenController;

    /**
     * Methode zur Speicherung der (geänderten) Unternehmen der ListView.
     *
     * @param event Event des aufrufenden Buttons
     */
    @FXML
    void doSpeichern(ActionEvent event) {

        // Aktualisiere alle Unternehmen und füge sofern notwendig neue der Datenbank hinzu
        ArrayList<Unternehmen> unternehmenNachAenderung = new ArrayList<>(unternehmenObserverableList);
        model.save(unternehmenNachAenderung);

        /* Lösche nicht benötigte Unternehmen aus Datenbank. Durchlaufe hierfür unternehmenNachAenderung.
         * contains() greift für einen Vergleich auf Gleichheit auf die equals() Methode der Klasse Unternehmen zurück.*/
        ArrayList<Unternehmen> unternehmenZumLoeschen = unternehmenInitial.stream().filter(u -> !unternehmenNachAenderung.contains(u)).collect(Collectors.toCollection(ArrayList::new));
        model.delete(unternehmenZumLoeschen);

        /* Setze unternehmenIntial zurück, andernfalls würde bei erneuter Speicherung versucht werden, das
        Unternehmen erneut zu löschen.*/
        unternehmenInitial = unternehmenNachAenderung;
    }

    /**
     * Methode zum Wechsel der aktuellen Scene zur Scene um Wertpapiere anzulegen.
     * @param event Event des aufrufenden Buttons
     */
    @FXML
    void doWertpapierAnlegen(ActionEvent event) {
        doSpeichern(event);
        screenController.loadScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN, ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_WERTPAPIER_ANLEGEN);
    }

    /**
     * Methode zur Initialisierung der ListView mit Unternehmen, sofern vorhanden.
     */
    @FXML
    private void initialize() {
        model = UnternehmenRepository.getInstanz();
        unternehmenInitial = new ArrayList<>(model.findByUnternehmenArt(Unternehmen.UNTERNEHMEN_TEILNEHMER));
        unternehmenObserverableList = FXCollections.observableArrayList(unternehmenInitial);
        lstVwUnternehmen.setItems(unternehmenObserverableList);
        lstVwUnternehmen.setCellFactory(new UnternehmenCellFactory());
    }

    /**
     * Konkrete Implementierung für den Zugriff auf den Controller des übergeordneten Screens
     * @param screenPage Controller des Screens
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    /**
     * Methode zur Anlage neuer Unternehmen.
     */
    public void doHinzufuegen() {
        unternehmenObserverableList.add(new Unternehmen());
    }
}

