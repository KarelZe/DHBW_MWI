package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.TeilnehmerViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TeilnehmerUebersichtController implements ControlledScreen, Initializable {
    private static final String STANDARDPASSWORT = "Anika";
    @FXML
    TableView<TeilnehmerViewModel> TV_Teilnehmeruebersicht;
    @FXML
    TableColumn<TeilnehmerViewModel, String> TV_COL_Vorname;
    @FXML
    TableColumn<TeilnehmerViewModel, String> TV_COL_Nachname;
    @FXML
    TableColumn<TeilnehmerViewModel, Long> TV_COL_ID;
    private ObservableList<TeilnehmerViewModel> teilnehmerViewModel = FXCollections.observableArrayList(getAlleTeilnehmerViewModel());

    @Override
    public void setScreenParent(ScreenController screenPage) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TV_COL_ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TV_COL_Vorname.setCellValueFactory(new PropertyValueFactory<>("Vorname"));
        TV_COL_Nachname.setCellValueFactory(new PropertyValueFactory<>("Nachname"));
        addPasswortZuruecksetzenButtonToTable();
        TV_Teilnehmeruebersicht.setItems(teilnehmerViewModel);
    }

    private List<TeilnehmerViewModel> getAlleTeilnehmerViewModel() {
        List<Teilnehmer> alleTeilnehmer = TeilnehmerRepository.getAlleTeilnehmer();
        List<TeilnehmerViewModel> teilnehmerViewModel = new ArrayList<>();
        for (Teilnehmer teilnehmer : alleTeilnehmer) {
            teilnehmerViewModel.add(new TeilnehmerViewModel(teilnehmer.getId(), teilnehmer.getVorname(), teilnehmer.getNachname()));
        }
        return teilnehmerViewModel;
    }

    private void addPasswortZuruecksetzenButtonToTable() {
        TableColumn colBtn = new TableColumn("");

        Callback<TableColumn<TeilnehmerViewModel, Void>, TableCell<TeilnehmerViewModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<TeilnehmerViewModel, Void> call(final TableColumn<TeilnehmerViewModel, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Passwort zur\u00fccksetzen");

                    {
                        btn.setOnAction((ActionEvent event) -> { //wird bei Button click ausgeführt
                            Teilnehmer selektierterTeilnehmer = TeilnehmerRepository.getTeilnehmerById(getTableView().getItems().get(getIndex()).getId());
                            Alert messageBox;
                            if (selektierterTeilnehmer != null) {
                                selektierterTeilnehmer.setPasswort(EncryptionHelper.getStringAsMD5(STANDARDPASSWORT));
                                TeilnehmerRepository.persistTeilnehmer(selektierterTeilnehmer);
                                messageBox = new Alert(Alert.AlertType.INFORMATION);
                                messageBox.setHeaderText("Das Passwort wurde auf \"Anika\" zur\u00fcckgesetzt.");
                                messageBox.showAndWait();
                            } else {
                                messageBox = new Alert(Alert.AlertType.ERROR);
                                messageBox.setHeaderText("Es ist ein Fehler aufgetreten");
                                messageBox.showAndWait();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        colBtn.setCellFactory(cellFactory);
        TV_Teilnehmeruebersicht.getColumns().add(colBtn);

    }
}
