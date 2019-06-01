package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.TeilnehmerViewModel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeilnehmerUebersichtController implements ControlledScreen, Initializable {
    @FXML
    TableView<TeilnehmerViewModel> tvTeilnehmer;
    @FXML
    TableColumn<TeilnehmerViewModel, String> tblColVorname;
    @FXML
    TableColumn<TeilnehmerViewModel, String> tblColNachname;
    @FXML
    TableColumn<TeilnehmerViewModel, Long> tblColId;
    @FXML
    TableColumn<TeilnehmerViewModel, Long> tblColPasswort;

    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findAll();
        List<TeilnehmerViewModel> teilnehmerViewModel = teilnehmer.stream().map(TeilnehmerViewModel::new).collect(Collectors.toList());
        ObservableList<TeilnehmerViewModel> observableList = FXCollections.observableArrayList(teilnehmerViewModel);
        tvTeilnehmer.setItems(observableList);
    }

}
