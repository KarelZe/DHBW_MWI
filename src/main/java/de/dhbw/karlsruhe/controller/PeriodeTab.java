package de.dhbw.karlsruhe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.IOException;

public class PeriodeTab extends Tab {
    PeriodeTab() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tab_periode.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSpeichern(ActionEvent event) {
    }
}
