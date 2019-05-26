package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Berechtigungsrolle;
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
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TeilnehmerUebersichtController implements ControlledScreen, Initializable {
    private ScreenController screenController;
    @FXML
    TableView<TeilnehmerViewModel> TV_Teilnehmeruebersicht;
    @FXML
    TableColumn<TeilnehmerViewModel, String> TV_COL_Vorname;
    @FXML
    TableColumn<TeilnehmerViewModel, String> TV_COL_Nachname;
    @FXML
    TableColumn<TeilnehmerViewModel, Long> TV_COL_ID;

    private static final String STANDARDPASSWORT = "Anika";

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TV_COL_ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TV_COL_Vorname.setCellValueFactory(new PropertyValueFactory<>("Vorname"));
        TV_COL_Nachname.setCellValueFactory(new PropertyValueFactory<>("Nachname"));
        addPasswortZuruecksetzenButtonToTable();
        TV_Teilnehmeruebersicht.setItems(teilnehmerViewModel);
    }

    private ObservableList<TeilnehmerViewModel> teilnehmerViewModel = FXCollections.observableArrayList(getAlleTeilnehmerViewModel());

    private List<TeilnehmerViewModel> getAlleTeilnehmerViewModel() {
        List<Teilnehmer> alleTeilnehmer = TeilnehmerRepository.getAlleTeilnehmer();
        List<TeilnehmerViewModel> teilnehmerViewModel = new ArrayList<>();
        for (Teilnehmer teilnehmer : alleTeilnehmer) {
            if (teilnehmer.getRolle().getId() == Long.valueOf(Berechtigungsrolle.TEILNEHMER.ordinal())) {//Teilnehmer ist Teilnehmer (und kein Seminarleiter)
                teilnehmerViewModel.add(new TeilnehmerViewModel(teilnehmer.getId(), teilnehmer.getVorname(), teilnehmer.getNachname()));
            }
        }
        return teilnehmerViewModel;
    }

    private void addPasswortZuruecksetzenButtonToTable() {
        TableColumn<TeilnehmerViewModel, Void> colBtn = new TableColumn("");

        Callback<TableColumn<TeilnehmerViewModel, Void>, TableCell<TeilnehmerViewModel, Void>> cellFactory = new Callback<TableColumn<TeilnehmerViewModel, Void>, TableCell<TeilnehmerViewModel, Void>>() {
            @Override
            public TableCell<TeilnehmerViewModel, Void> call(final TableColumn<TeilnehmerViewModel, Void> param) {
                final TableCell<TeilnehmerViewModel, Void> cell = new TableCell<TeilnehmerViewModel, Void>() {

                    private final Button btn = new Button("Passwort zur\u00fccksetzen");

                    {
                        btn.setOnAction((ActionEvent event) -> { //wird bei Button click ausgeführt
                            Teilnehmer selektierterTeilnehmer = TeilnehmerRepository.getTeilnehmerById(getTableView().getItems().get(getIndex()).getId()); //ToDo: null abfangen
                            selektierterTeilnehmer.setPasswort(EncryptionHelper.getStringAsMD5(STANDARDPASSWORT));
                            TeilnehmerRepository.persistTeilnehmer(selektierterTeilnehmer);
                            Alert messageBox = new Alert(Alert.AlertType.INFORMATION);
                            messageBox.setHeaderText("Das Passwort wurde auf \"Anika\" zur\u00fcckgesetzt.");
                            messageBox.showAndWait();

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
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        TV_Teilnehmeruebersicht.getColumns().add(colBtn);

    }
}
