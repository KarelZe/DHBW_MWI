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
import javafx.scene.control.Alert;
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

        System.out.println(aktieInitial);

        aktieObserverableList.addAll(aktieInitial);
        lstVwAktie.setItems(aktieObserverableList);
        lstVwAktie.setCellFactory(new AktienPeriodeCellFactory());

        anleiheObserverableList.addAll(anleiheInitial);
        lstVwAnleihe.setItems(anleiheObserverableList);
        lstVwAnleihe.setCellFactory(new AnleihePeriodeCellFactory());

        btnSpeichern.setVisible(true);
        btnAbschliessen.setVisible(false);
    }

    /**
     * Diese Methode implementiert eine Speicherfunktionalität für die Kurse der Periode.
     */
    private void doSpeichern() {
        ArrayList<Kurs> aktieNachAenderung = new ArrayList<>(aktieObserverableList);
        ArrayList<Kurs> anleiheNachAenderung = new ArrayList<>(anleiheObserverableList);


        /* Überprüfe Eingaben auf Gültigkeit. Aktienkurse sind stücknotiert (€) / Anleihekurse sind prozentnotiert (%),
        daher Unterscheidung.*/
        boolean ungueltigerAktienkurs = false;
        boolean ungueltigerManuellerKurs = false;

        for (Kurs k : aktieNachAenderung) {
            if (k.getKurs() < 0) ungueltigerAktienkurs = true;
        }
        for (Kurs k : anleiheNachAenderung) {
            if (k.getManuellerKurs() != null && k.getManuellerKurs() < 0) ungueltigerManuellerKurs = true;
        }

        if (ungueltigerManuellerKurs || ungueltigerAktienkurs) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ung\u00fcltige Eingabe");
            alert.setHeaderText(null);
            if (ungueltigerAktienkurs && ungueltigerManuellerKurs)
                alert.setContentText("Ein Aktienkurs muss >= 0 \u20ac sein und \nein Anleihekurs muss >= 0 % sein.");
            else if (ungueltigerManuellerKurs)
                alert.setContentText("Ein Anleihekurs muss >= 0 % sein.");
            else
                alert.setContentText("Ein Aktienkurs muss >= 0 \u20ac sein.");
           alert.showAndWait();
            return;
        }

        model.save(aktieNachAenderung);
        model.save(anleiheNachAenderung);

        btnSpeichern.setVisible(true);
        btnAbschliessen.setVisible(true);
    }

    /**
     * Diese Methode erlaubt die Verbuchung und die Bewertung der Periode.
     */
    private void doAbschliessen() {

        Periodenabschluss periodenabschluss = new Periodenabschluss();
        periodenabschluss.periodeAbschliessen(periode);

        ScreenController.myPeriodeControllerHandle.changePage();

        btnSpeichern.setVisible(false);
        btnAbschliessen.setVisible(false);
    }
}
