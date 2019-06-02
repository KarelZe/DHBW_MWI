package de.dhbw.karlsruhe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PeriodeTab extends Tab {

    @FXML
    private VBox vboxPeriode;

    PeriodeTab(String text) {
        super();
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

    @FXML
    private void initialize() {
        setContent(vboxPeriode);
    }

    public void doSpeichern(ActionEvent event) {
    }
}
