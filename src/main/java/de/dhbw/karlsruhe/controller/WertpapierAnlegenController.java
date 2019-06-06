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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    @FXML
    void doSpeichern(ActionEvent event) {

        // Aktualisiere alle Wertpapier und füge sofern notwendig neue der Datenbank hinzu
        ArrayList<Wertpapier> aktieNachAenderung = new ArrayList<>(aktieObserverableList);
        model.save(aktieNachAenderung);
        ArrayList<Wertpapier> anleiheNachAenderung = new ArrayList<>(anleiheObserverableList);
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
    }

    /**
     * Funktion initalisiert die ListView mit Wertpapieren, sofern vorhanden.
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


    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    public void doHinzufuegenAnleihe() {
        WertpapierArt wpA = new WertpapierArt(WertpapierArt.WERTPAPIER_ANLEIHE, WertpapierArt.WERTPAPIER_ANLEIHE_NAME);
        Wertpapier wp = new Wertpapier();
        wp.setWertpapierArt(wpA);
        anleiheObserverableList.add(wp);
    }

    public void doHinzufuegenAktie() {
        WertpapierArt wpA = new WertpapierArt(WertpapierArt.WERTPAPIER_AKTIE, WertpapierArt.WERTPAPIER_AKTIE_NAME);
        Wertpapier wp = new Wertpapier();
        wp.setWertpapierArt(wpA);
        aktieObserverableList.add(wp);
    }
}

