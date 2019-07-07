package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.model.BenutzerPrintModel;
import de.dhbw.karlsruhe.model.BenutzerRepository;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.io.IOException;
import java.util.Optional;

/**
 * Diese Klasse stellt eine Cell für die Historie zur Überladung eines ListViews zur Verfügung.
 */
public class HistorieCell extends TableCell<BenutzerPrintModel, Void> {

    @FXML
    private Button btnHistorie;

    /**
     * Konstruktor
     */
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

    /**
     * Aktualisiert einen Eintrag.
     * @param item Eintrag, der aktialisiert werden soll
     * @param empty Gibt an, ob Zelle einen Wert hat
     */
    @Override
    public void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(btnHistorie);
        }
    }

    /**
     * Initialisierung
     */
    @FXML
    private void initialize() {
        btnHistorie.setOnAction((ActionEvent event) -> { //wird bei Button click ausgeführt
            Optional<Benutzer> teilnehmer = BenutzerRepository.getInstanz().findById(getTableView().getItems().get(getIndex()).getId());
            teilnehmer.ifPresentOrElse(t -> {
                //Ruft über den instanzierten Controllerr von ScreenController mit der ID die Historie auf
                ScreenController.myPrintControllerHandle.showHistoriewithID(getTableView().getItems().get(getIndex()).getId());
            }, () -> {
                Alert messageBox = new Alert(Alert.AlertType.ERROR);
                messageBox.setHeaderText("Es ist ein Fehler aufgetreten");
                messageBox.showAndWait();
            });
        });
    }


}

