package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.TeilnehmerPrintModel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.io.IOException;
import java.util.Optional;

public class HistorieCell extends TableCell<TeilnehmerPrintModel, Void> {
    @FXML
    private Button btnHistorie;

    public HistorieCell() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_historie.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(btnHistorie);
        }
    }

    @FXML
    private void initialize() {
        btnHistorie.setOnAction((ActionEvent event) -> { //wird bei Button click ausgeführt
            Optional<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findById(getTableView().getItems().get(getIndex()).getId());
            teilnehmer.ifPresentOrElse(t -> {
                //Ruft über den instanzierten Controllerr von ScreenController mit der ID die Historie auf
                ScreenController.myPrintControllerHandle.showHistoriewithID(getTableView().getItems().get(getIndex()).getId());
            },() -> {
                Alert messageBox = new Alert(Alert.AlertType.ERROR);
                messageBox.setHeaderText("Es ist ein Fehler aufgetreten");
                messageBox.showAndWait();
            });
        });
    }


}

