package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.controller.factory.AktienCellFactory;
import de.dhbw.karlsruhe.controller.factory.AnleiheCellFactory;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Diese Klasse implementiert den Controller für die Anlage von Wertpapieren.
 *
 * @author Markus Bilz
 */
public class WertpapierAnlegenController implements ControlledScreen {

    @FXML
    public Button btnSpeichern;
    public Label lblAktie;
    public VBox vboxUnternehmen;
    public ListView<Wertpapier> lstVwAktie;
    public Label lblAnleihe;
    public ListView<Wertpapier> lstVwAnleihe;
    private ObservableList<Wertpapier> anleiheObserverableList = FXCollections.observableArrayList();
    private ArrayList<Wertpapier> anleiheInitial = new ArrayList<>();
    private ObservableList<Wertpapier> aktieObserverableList = FXCollections.observableArrayList();
    private ArrayList<Wertpapier> aktieInitial = new ArrayList<>();
    private WertpapierRepository model;
    private ScreenController screenController;

    /**
     * Methode zur Speicherung der (geänderten) Wertpapiere der ListView.
     *
     * @param event Event des aufrufenden Buttons
     */
    @FXML
    void doSpeichern(ActionEvent event) {

        ArrayList<Wertpapier> aktieNachAenderung = new ArrayList<>(aktieObserverableList);
        ArrayList<Wertpapier> anleiheNachAenderung = new ArrayList<>(anleiheObserverableList);

        // Überprüfe Eingaben auf Gültigkeit
        boolean ungueltigesUnternehmen = false;
        boolean ungueltigerName = false;
        ArrayList<Wertpapier> wertpapiereAlle = new ArrayList<>(aktieNachAenderung);
        wertpapiereAlle.addAll(anleiheNachAenderung);

        for (Wertpapier w : wertpapiereAlle) {
            if (w.getName().isBlank()) ungueltigerName = true;
            if (w.getUnternehmen() == null) ungueltigesUnternehmen = true;
        }

        if (ungueltigerName || ungueltigesUnternehmen) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ung\u00fcltige Eingabe");
            alert.setHeaderText(null);
            alert.setContentText("Es fehlen Eingaben");
            alert.showAndWait();
            return;
        }

        if (aktieNachAenderung.isEmpty() || anleiheNachAenderung.isEmpty()) { //keine Aktie und/oder Anleihe emittiert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wertpapier anlegen");
            alert.setHeaderText(null);
            alert.setContentText("Sie m\u00fcssen mindestens eine Aktie und eine Anleihe erfassen.");
            alert.showAndWait();
            return;
        }

        // Aktualisiere alle Wertpapier und füge sofern notwendig neue der Datenbank hinzu
        model.save(aktieNachAenderung);
        model.save(anleiheNachAenderung);

        /* Lösche nicht benötigte Wertpapiere aus Datenbank. Durchlaufe hierfür wertpapierNachAenderung.
         * contains() greift für einen Vergleich auf Gleichheit auf die equals() Methode der Klasse Wertpapier zurück.
         */
        ArrayList<Wertpapier> aktieZumLoeschen = aktieInitial.stream().filter(w -> !aktieNachAenderung.contains(w)).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Wertpapier> anleiheZumLoeschen = anleiheInitial.stream().filter(w -> !anleiheNachAenderung.contains(w)).collect(Collectors.toCollection(ArrayList::new));
        model.delete(aktieZumLoeschen);
        model.delete(anleiheZumLoeschen);

        /* Setze wertpapierIntial zurück, andernfalls würde bei erneuter Speicherung versucht werden, das
        Wertpapier erneut zu löschen.*/
        aktieInitial = aktieNachAenderung;
        anleiheInitial = anleiheNachAenderung;

        screenController.loadScreen(ScreensFramework.SCREEN_PERIODE_ANLEGEN, ScreensFramework.SCREEN_PERIODE_ANLEGEN_FILE);
        screenController.setScreen(ScreensFramework.SCREEN_PERIODE_ANLEGEN);
    }


    /**
     * Methode ist Bestandteil des Lifecycles von JavaFX und initialisiert die Listener von UI-Elementen für die
     * spätere Verwendung. Sie erzeugt dynamisch alle Einträge ein
     * @author Markus Bilz
     */
    @FXML
    private void initialize() {
        // Frage alle Wertpapiere in DB ab und filtere nach Typ
        model = WertpapierRepository.getInstanz();
        ArrayList<Wertpapier> wertpapiere = new ArrayList<>(model.findAll());
        aktieInitial = new ArrayList<>();
        anleiheInitial = new ArrayList<>();
        wertpapiere.stream().filter(w -> w.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).forEach(w -> aktieInitial.add(w));
        wertpapiere.stream().filter(w -> w.getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).forEach(w -> anleiheInitial.add(w));

        aktieObserverableList.addAll(aktieInitial);
        lstVwAktie.setItems(aktieObserverableList);
        lstVwAktie.setCellFactory(new AktienCellFactory());

        anleiheObserverableList.addAll(anleiheInitial);
        lstVwAnleihe.setItems(anleiheObserverableList);
        lstVwAnleihe.setCellFactory(new AnleiheCellFactory());
    }

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
     * Methode zum Hinzfügen einer neuen Anleiheposition in die ListView.
     * Das ListView, dem die Anleihe hinzugefügt wird, wird dann automatisch aktualisiert.
     * @author Markus Bilz
     */
    public void doHinzufuegenAnleihe() {
        WertpapierArt wpA = new WertpapierArt(WertpapierArt.WERTPAPIER_ANLEIHE, WertpapierArt.WERTPAPIER_ANLEIHE_NAME);
        Wertpapier wp = new Wertpapier();
        wp.setWertpapierArt(wpA);
        wp.setName("Anleihe");
        anleiheObserverableList.add(wp);
    }

    /**
     * Methode zum Hinzufügen einer neuen Aktienposition in die ListView.
     * Das ListView, dem die Aktie hinzugefügt wird, wird dann automatisch aktualisiert.
     * @author Markus Bilz
     */
    public void doHinzufuegenAktie() {
        WertpapierArt wpA = new WertpapierArt(WertpapierArt.WERTPAPIER_AKTIE, WertpapierArt.WERTPAPIER_AKTIE_NAME);
        Wertpapier wp = new Wertpapier();
        wp.setWertpapierArt(wpA);
        wp.setName("Aktie");
        aktieObserverableList.add(wp);
    }

}

