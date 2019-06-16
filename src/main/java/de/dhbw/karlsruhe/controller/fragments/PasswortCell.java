package de.dhbw.karlsruhe.controller.fragments;

import de.dhbw.karlsruhe.helper.ConstantsHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.TeilnehmerViewModel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.io.IOException;
import java.util.Optional;

public class PasswortCell extends TableCell<TeilnehmerViewModel, Void> {
    @FXML
    private Button btnPasswort;

    public PasswortCell() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cell_passwort.fxml"));
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
            setGraphic(btnPasswort);
        }
    }

    @FXML
    private void initialize() {
        btnPasswort.setOnAction((ActionEvent event) -> { //wird bei Button click ausgeführt
            Optional<Teilnehmer> teilnehmer = TeilnehmerRepository.getInstanz().findById(getTableView().getItems().get(getIndex()).getId());
            teilnehmer.ifPresentOrElse(t -> {
                        t.setPasswort(EncryptionHelper.getStringAsMD5(ConstantsHelper.DEFAULT_PASSWORT));
                        TeilnehmerRepository.getInstanz().save(t);
                        Alert messageBox = new Alert(Alert.AlertType.INFORMATION);
                        messageBox.setHeaderText("Das Passwort wurde auf \"anika\" zur\u00fcckgesetzt.");
                        messageBox.showAndWait();
                    },
                    () -> {
                        Alert messageBox = new Alert(Alert.AlertType.ERROR);
                        messageBox.setHeaderText("Es ist ein Fehler aufgetreten");
                        messageBox.showAndWait();
                    }
            );
        });
    }
}

