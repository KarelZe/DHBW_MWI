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

    private ArrayList<HBox> hboxUnternehmenDyn = new ArrayList<>();

    private Model model;
    private ScreensController controller;

    private ArrayList<Unternehmen> unternehmen;

    @FXML
    void doHinzufuegen(ActionEvent event) {
        if (anzahlUnternehmen < MAX_ANZAHL_UNTERNEHMEN) {
            int index = vboxUnternehmenAlle.getChildren().size();
            // füge Elemente vor Speichern Button hinzu
            index -= 1;


            HBox tmpHbox = new HBox();
            TextField tmpUnternehmensname = new TextField();
            tmpUnternehmensname.setPromptText(unternehmensname);
            TextField tmpStartkapital = new TextField();
            tmpStartkapital.setPromptText(startkapital);
            ColorPicker tmpFarbe = new ColorPicker();
            Button tmpLoeschen = new Button("-");

            tmpLoeschen.setOnAction(e -> {
                // TODO: Parent Node löschen
                System.out.println("löschen");
            });


            int margin = 5;
            Insets inset = new Insets(margin, margin, margin, margin);
            tmpHbox.getChildren().addAll(tmpUnternehmensname, tmpStartkapital, tmpFarbe, tmpLoeschen);
            HBox.setMargin(tmpUnternehmensname, inset);
            HBox.setMargin(tmpFarbe, inset);
            HBox.setMargin(tmpStartkapital, inset);
            HBox.setMargin(tmpLoeschen, inset);

            hboxUnternehmenDyn.add(tmpHbox);
            vboxUnternehmenAlle.getChildren().add(index, tmpHbox);
            anzahlUnternehmen++;

            System.out.println("hinzufügen");
        }
    }

    @FXML
    void doSpeichern(ActionEvent event) {

        // füge statische HBox und dynamische HBox zusammen
        ArrayList<HBox> hBoxes = hboxUnternehmenDyn;
        hBoxes.add(hboxUnternehmenEinzel);
        // TODO: HBoxes 2 Unternehmen
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

