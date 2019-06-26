package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.bewertung.Periodenabschluss;
import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.controller.factory.AktienPeriodeCellFactory;
import de.dhbw.karlsruhe.controller.factory.AnleihePeriodeCellFactory;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Diese Klasse erzeugt einen Tab mit Perioden.
 *
 * @author Markus Bilz
 */
public class PeriodeTab extends Tab {

    @FXML
    public VBox vboxPeriode;
    @FXML
    public ListView<Kurs> lstVwAktie;
    @FXML
    public ListView<Kurs> lstVwAnleihe;
    @FXML
    public Button btnSpeichern;
    @FXML
    public Button btnAbschliessen;

    private ObservableList<Kurs> anleiheObserverableList = FXCollections.observableArrayList();
    private ArrayList<Kurs> anleiheInitial = new ArrayList<>();
    private ObservableList<Kurs> aktieObserverableList = FXCollections.observableArrayList();
    private ArrayList<Kurs> aktieInitial = new ArrayList<>();
    private KursRepository model;
    private Periode periode;

    /**
     * Konstruktor für die Erzeugung eines {@code PeriodeTab}. Der Konstruktor lädt die verbundene FXML und
     * initialisiert die enthaltenen UI-Elemente für einen späteren Zugriff.
     *
     * @param text    Reitername des Tabs
     * @param periode Periode zur Anzeige im Tab
     */
    public PeriodeTab(String text, Periode periode) {
        super();
        this.periode = periode;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tab_periode.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setText(text);
    }

    /**
     * Diese Methode ist Bestandteil des Lifecycles von JavaFX und initialisiert die Listener von UI-Elementen des Tabs
     * für die spätere Verwendung. Sie erzeugt für alle Wertpapiere einer Periode dynamisch Einträge zur Pflege.
     */
    @FXML
    private void initialize() {
        setContent(vboxPeriode);
        btnSpeichern.setOnAction(event -> doSpeichern());
        btnAbschliessen.setOnAction(event -> doAbschliessen());

        // Frage alle Wertpapiere in DB ab und filtere nach Typ
        model = KursRepository.getInstanz();
        ArrayList<Kurs> kurs = new ArrayList<>(model.findByPeriodenId(periode.getId()));
        aktieInitial = new ArrayList<>();
        anleiheInitial = new ArrayList<>();
        kurs.stream().filter(k -> k.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).forEach(k -> aktieInitial.add(k));
        kurs.stream().filter(k -> k.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).forEach(k -> anleiheInitial.add(k));

        aktieObserverableList.addAll(aktieInitial);
        lstVwAktie.setItems(aktieObserverableList);
        lstVwAktie.setCellFactory(new AktienPeriodeCellFactory());

        anleiheObserverableList.addAll(anleiheInitial);
        lstVwAnleihe.setItems(anleiheObserverableList);
        lstVwAnleihe.setCellFactory(new AnleihePeriodeCellFactory());
    }

    /**
     * Diese Methode implementiert eine Speicherfunktionalität für die Kurse der Periode.
     */
    private void doSpeichern() {
        ArrayList<Kurs> aktieNachAenderung = new ArrayList<>(aktieObserverableList);
        model.save(aktieNachAenderung);
        ArrayList<Kurs> anleiheNachAenderung = new ArrayList<>(anleiheObserverableList);
        model.save(anleiheNachAenderung);
    }

    /**
     * Diese Methode erlaubt die Verbuchung und die Bewertung der Periode.
     */
    private void doAbschliessen() {

        Periodenabschluss periodenabschluss = new Periodenabschluss();
        periodenabschluss.periodeAbschliessen(periode);

        ScreenController.myPeriodeControllerHandle.changePage();
    }
}
