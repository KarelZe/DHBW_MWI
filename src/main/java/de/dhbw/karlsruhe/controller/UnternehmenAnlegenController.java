package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UnternehmenAnlegenController implements ControlledScreen {

    ArrayList<Double> startkapitale;
    ArrayList<String> unternehmensnamen;
    ArrayList<String> unternehmensfarben;


    @FXML
    private VBox vboxUnternehmenAlle;

    @FXML
    private Label lblUnternehmen;

    @FXML
    private HBox hboxUnternehmenEinzel;

    @FXML
    private TextField txtUnternehmenName;

    @FXML
    private TextField txtStartkapital;

    @FXML
    private ColorPicker clrUnternehmen;

    @FXML
    private Button btnSpeichern;

    private Model model;
    private ScreensController controller;

    @FXML
    void doHinzufuegen(ActionEvent event) {
        System.out.println("hinzufügen");
    }

    @FXML
    void doSpeichern(ActionEvent event) {

        //TODO: Fehlerbehandlung
        System.out.println("speichern");


    }

    // Zur Erklärung https://stackoverflow.com/a/51392331
    // Zur Erklärung https://javabeginners.de/Frameworks/JavaFX/FXML.php
    @FXML
    private void initialize() {
        model = Model.getInstanz();
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }
}

