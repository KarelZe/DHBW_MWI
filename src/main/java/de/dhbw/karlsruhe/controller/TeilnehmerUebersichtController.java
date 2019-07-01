package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.BenutzerRepository;
import de.dhbw.karlsruhe.model.BenutzerViewModel;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;

public class TeilnehmerUebersichtController implements ControlledScreen {
    @FXML
    TableView<BenutzerViewModel> tvTeilnehmer;
    @FXML
    TableColumn<BenutzerViewModel, String> tblColVorname;
    @FXML
    TableColumn<BenutzerViewModel, String> tblColNachname;
    @FXML
    TableColumn<BenutzerViewModel, Long> tblColId;
    @FXML
    TableColumn<BenutzerViewModel, Long> tblColPasswort;

    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    @FXML
    private void initialize() {
        List<Benutzer> benutzer = BenutzerRepository.getInstanz().findAll();
        List<BenutzerViewModel> benutzerViewModel = benutzer.stream().map(BenutzerViewModel::new).collect(Collectors.toList());
        ObservableList<BenutzerViewModel> observableList = FXCollections.observableArrayList(benutzerViewModel);
        tvTeilnehmer.setItems(observableList);
    }

}
