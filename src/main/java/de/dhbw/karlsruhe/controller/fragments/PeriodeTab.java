package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.bewertung.Periodenabschluss;
import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.controller.factory.AktienPeriodeCellFactory;
import de.dhbw.karlsruhe.controller.factory.AnleihePeriodeCellFactory;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
    public Button btnBewerten, btnVerbuchen;
    @FXML
    private Label lblAktie, lblAnleihe;

    private ObservableList<Kurs> anleiheObserverableList = FXCollections.observableArrayList();
    private ArrayList<Kurs> anleiheInitial = new ArrayList<>();
    private ObservableList<Kurs> aktieObserverableList = FXCollections.observableArrayList();
    private ArrayList<Kurs> aktieInitial = new ArrayList<>();
    private KursRepository model;
    private Periode periode;

    /**
     * Konstruktor für die Erzeugung eines {@link PeriodeTab}. Der Konstruktor lädt die verbundene FXML und
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
        btnBewerten.setOnAction(event -> doBewerten());
        btnVerbuchen.setOnAction(event -> doVerbuchen());

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

        // Sofern eine Periode abgeschlossen ist, kann sie nicht mehr bearbeitet werden
        if (periode.getIst_aktiv() == Periode.PERIODE_INAKTIV) {
            setContentDisabled();
        }
    }

    /**
     * Diese Methode implementiert eine Speicherfunktionalität für die Kurse der Periode.
     * @author Markus Bilz
     */
    private void doBewerten() {
        ArrayList<Kurs> aktieNachAenderung = new ArrayList<>(aktieObserverableList);
        ArrayList<Kurs> anleiheNachAenderung = new ArrayList<>(anleiheObserverableList);


        /* Überprüfe Eingaben auf Gültigkeit. Aktienkurse sind stücknotiert (€) / Anleihekurse sind prozentnotiert (%),
        daher Unterscheidung.*/
        boolean ungueltigerAktienkurs = false;
        boolean ungueltigerManuellerKurs = false;
        boolean ungueltigerSpread = false;

        for (Kurs k : aktieNachAenderung) {
            if (k.getKurs() < 0) ungueltigerAktienkurs = true;
        }
        for (Kurs k : anleiheNachAenderung) {
            if (k.getManuellerKurs() != null && k.getManuellerKurs() < 0) ungueltigerManuellerKurs = true;
        }

        for (Kurs k : anleiheNachAenderung) {
            if (k.getSpread() == null || k.getSpread() < -0.5 || k.getSpread() > 0.5) ungueltigerSpread = true;
        }

        if (ungueltigerAktienkurs || ungueltigerManuellerKurs || ungueltigerSpread) {
            StringBuilder fehlermeldung = new StringBuilder();
            if (ungueltigerAktienkurs)
                fehlermeldung.append("Ein Aktienkurs muss >= 0 \u20ac sein.\n");
            if (ungueltigerManuellerKurs)
                fehlermeldung.append("Ein Anleihekurs muss >= 0 % sein.\n");
            if (ungueltigerSpread)
                fehlermeldung.append("Ein Spread muss zwischen - 50 % und + 50 % liegen.\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ung\u00fcltige Eingabe");
            alert.setContentText(fehlermeldung.toString());
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        model.save(aktieNachAenderung);
        model.save(anleiheNachAenderung);

        Periodenabschluss periodenabschluss = new Periodenabschluss();
        periodenabschluss.periodeBewerten(periode);
    }

    /**
     * Diese Methode erlaubt die Verbuchung und die Bewertung der Periode.
     * @author Markus Bilz
     */
    private void doVerbuchen() {

        // Erzeuge Dialog für Nachfrage vor Bewertung
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Periode abschlie\u00dfen");
        alert.setContentText("Wollen Sie die Periode irreversibel abschlie\u00dfen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        }

        // Periode nach Speichern auf inaktiv setzen
        periode.setIst_aktiv(Periode.PERIODE_INAKTIV);
        PeriodenRepository.getInstanz().save(periode);
        setContentDisabled();
        // Periode bewerten und verbuchen
        Periodenabschluss periodenabschluss = new Periodenabschluss();
        periodenabschluss.periodeAbschliessen(periode);

        ScreenController.myPeriodeControllerHandle.changePage();

    }

    /**
     * Methode, um UI Elemente inaktiver Perioden zu deaktiveren / auszugrauen.
     * @author Markus Bilz
     */
    private void setContentDisabled() {
        vboxPeriode.setDisable(true);
        lblAktie.setDisable(true);
        lblAnleihe.setDisable(true);
        btnBewerten.setDisable(true);
        btnVerbuchen.setDisable(true);
        lstVwAktie.setDisable(true);
        lstVwAnleihe.setDisable(true);
    }
}
