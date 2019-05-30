package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.JPA.Wertpapier;
import de.dhbw.karlsruhe.model.JPA.WertpapierArt;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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
        ArrayList<Wertpapier> aktieNachAenderung = new ArrayList<Wertpapier>(aktieObserverableList);
        model.save(aktieNachAenderung);
        ArrayList<Wertpapier> anleiheNachAenderung = new ArrayList<Wertpapier>(aktieObserverableList);
        model.save(anleiheNachAenderung);

        /* Lösche nicht benötigte Wertpapiere aus Datenbank. Durchlaufe hierfür wertpapierNachAenderung.
         * contains() greift für einen Vergleich auf Gleichheit auf die equals() Methode der Klasse Wertpapier zurück.
         */
        ArrayList<Wertpapier> aktieZumLoeschen = new ArrayList<>();
        for (final Wertpapier w : anleiheInitial)
            if (!aktieNachAenderung.contains(w)) {
                aktieZumLoeschen.add(w);
            }
        model.delete(aktieZumLoeschen);

        ArrayList<Wertpapier> anleiheZumLoeschen = new ArrayList<>();
        for (Wertpapier w : anleiheInitial) {
            if (!anleiheNachAenderung.contains(w)) {
                anleiheZumLoeschen.add(w);
            }
        }
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

        model = WertpapierRepository.getInstanz();
        aktieInitial = new ArrayList<>();
        anleiheInitial = new ArrayList<>();

        aktieObserverableList.addAll(aktieInitial);
        lstVwAktie.setItems(aktieObserverableList);
        lstVwAktie.setCellFactory(aktieListView -> new WertpapierCell());

        anleiheObserverableList.addAll(anleiheInitial);
        lstVwAnleihe.setItems(anleiheObserverableList);
        lstVwAnleihe.setCellFactory(anleiheListView -> new WertpapierCell());
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    // TODO: Typ festlegen
    public void doHinzufuegenAnleihe(ActionEvent actionEvent) {
        WertpapierArt wpA = new WertpapierArt();
        Unternehmen u = new Unternehmen();
        Wertpapier wp = new Wertpapier();
        //wp.setUnternehmen(u);
        wp.setWertpapierArt(wpA);
        anleiheObserverableList.add(wp);
    }

    public void doHinzufuegenAktie(ActionEvent actionEvent) {
        WertpapierArt wpA = new WertpapierArt();
        Unternehmen u = new Unternehmen();
        Wertpapier wp = new Wertpapier();
        //wp.setUnternehmen(u);
        wp.setWertpapierArt(wpA);
        System.out.println(wp);
        aktieObserverableList.add(wp);
        System.out.println(aktieObserverableList);
    }
}

