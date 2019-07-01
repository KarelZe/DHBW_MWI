package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.HistorieViewModel;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;




public class TeilnehmerHistorieController implements ControlledScreen{
    @FXML
    TableView<HistorieViewModel> tvHistorie;
    @FXML
    TableColumn<HistorieViewModel, Long> tblColBuchungId;
    @FXML
    TableColumn<HistorieViewModel, String> tblColBuySell;
    @FXML
    TableColumn<HistorieViewModel, Double> tblColVolume;
    @FXML
    TableColumn<HistorieViewModel, String> tblColType;
    @FXML
    TableColumn<HistorieViewModel, Double> tblColUnternehmen;

    private ScreenController screenController;
    public static Long teilnehmerID;



    @FXML
    private void initialize() {
        // Wenn ein Spieler angemeldet ist,  setzt ihn als anzuzeigende TeilnehmerID
        if (AktuelleSpieldaten.getInstanz().getBenutzer() != null) {
            if (AktuelleSpieldaten.getInstanz().getBenutzer().getRolle().getId() == 1) {
                teilnehmerID = AktuelleSpieldaten.getInstanz().getBenutzer().getId();
            }//Sonst handelt es sich um den Spielleiter. Das Programm setzt vor dem Aufruf die teilnehmerID auf den gewählten Benutzer
        }

        List<Buchung> buchung = BuchungRepository.getInstanz().findByTeilnehmerId(teilnehmerID);
        List<HistorieViewModel> historieViewModel = buchung.stream().map(HistorieViewModel::new).collect(Collectors.toList());
        ObservableList<HistorieViewModel> observableList = FXCollections.observableArrayList(historieViewModel);
        tvHistorie.setItems(observableList);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }
}
