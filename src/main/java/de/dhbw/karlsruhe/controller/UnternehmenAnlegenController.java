package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.Model;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

    private final int MAX_ANZAHL_UNTERNEHMEN = 10;
    private int anzahlUnternehmen = 1;
    @FXML
    private VBox vboxUnternehmenAlle;

    @FXML
    private Label lblUnternehmen;

    @FXML
    private HBox hboxUnternehmenEinzel;

    @FXML
    private TextField txtUnternehmenName, txtStartkapital;

    @FXML
    private ColorPicker clrUnternehmen;

    @FXML
    private Button btnSpeichern;

    @FXML
    private String unternehmensname, startkapital;

    // Dynamische Felder
    private HBox[] hboxUnternehmenEinzelDyn = new HBox[MAX_ANZAHL_UNTERNEHMEN];
    private TextField[] txtUnternehmenNameDyn = new TextField[MAX_ANZAHL_UNTERNEHMEN];
    private TextField[] txtStartkapitalDyn = new TextField[MAX_ANZAHL_UNTERNEHMEN];
    private ColorPicker[] clrUnternehmenDyn = new ColorPicker[MAX_ANZAHL_UNTERNEHMEN];
    private Button[] btnUnternehmenDyn = new Button[MAX_ANZAHL_UNTERNEHMEN];
    private Model model;
    private ScreensController controller;

    private ArrayList<Unternehmen> unternehmen;

    @FXML
    void doHinzufuegen(ActionEvent event) {
        if (anzahlUnternehmen < MAX_ANZAHL_UNTERNEHMEN) {
            int index = vboxUnternehmenAlle.getChildren().size();
            // füge Elemente vor Speichern Button hinzu
            index -= 1;

            hboxUnternehmenEinzelDyn[anzahlUnternehmen] = new HBox();
            txtUnternehmenNameDyn[anzahlUnternehmen] = new TextField();
            txtUnternehmenNameDyn[anzahlUnternehmen].setPromptText(unternehmensname);
            txtStartkapitalDyn[anzahlUnternehmen] = new TextField();
            txtStartkapitalDyn[anzahlUnternehmen].setPromptText(startkapital);
            clrUnternehmenDyn[anzahlUnternehmen] = new ColorPicker();
            btnUnternehmenDyn[anzahlUnternehmen] = new Button("-");

            int margin = 5;
            hboxUnternehmenEinzelDyn[anzahlUnternehmen].getChildren().addAll(txtUnternehmenNameDyn[anzahlUnternehmen], txtStartkapitalDyn[anzahlUnternehmen], clrUnternehmenDyn[anzahlUnternehmen], btnUnternehmenDyn[anzahlUnternehmen]);
            HBox.setMargin(txtUnternehmenNameDyn[anzahlUnternehmen], new Insets(margin, margin, margin, margin));
            HBox.setMargin(txtStartkapitalDyn[anzahlUnternehmen], new Insets(margin, margin, margin, margin));
            HBox.setMargin(clrUnternehmenDyn[anzahlUnternehmen], new Insets(margin, margin, margin, margin));
            HBox.setMargin(btnUnternehmenDyn[anzahlUnternehmen], new Insets(margin, margin, margin, margin));

            vboxUnternehmenAlle.getChildren().add(index, hboxUnternehmenEinzelDyn[anzahlUnternehmen]);
            anzahlUnternehmen++;

            System.out.println("hinzufügen");
        }
    }

    @FXML
    void doSpeichern(ActionEvent event) {


        //model.setUnternehmen(unternehmen);
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

