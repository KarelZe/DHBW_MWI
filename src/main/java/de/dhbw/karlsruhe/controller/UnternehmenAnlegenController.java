package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.Model;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UnternehmenAnlegenController implements ControlledScreen {

    private final int MAX_ANZAHL_UNTERNEHMEN = 10;
    private int anzahlUnternehmen = 1;
    @FXML
    private VBox vboxUnternehmen;

    @FXML
    private HBox hBoxUnternehmenStatisch;

    @FXML
    private String unternehmensname, startkapital;

    private ArrayList<HBox> hBoxUnternehmenDynamisch = new ArrayList<>();

    private Model model;
    private ScreensController controller;


    @FXML
    void doHinzufuegen(ActionEvent event) {
        if (anzahlUnternehmen < MAX_ANZAHL_UNTERNEHMEN) {
            // Füge Elemente vor Speichern Button hinzu
            int index = vboxUnternehmen.getChildren().size();
            index -= 1;

            // Erzeuge neue HBox mit Konfiguration der statischen HBox
            HBox hBox = new HBox();
            TextField txtUnternehmensname = new TextField();
            txtUnternehmensname.setPromptText(unternehmensname);
            TextField txtStartkapital = new TextField();
            // TODO: Eingabe Startkapital begrenzen
            txtStartkapital.setPromptText(startkapital);
            ColorPicker clrFarbe = new ColorPicker();
            Button btnLoeschen = new Button("-");

            btnLoeschen.setOnAction(e -> {
                Button aufrufer = (Button) e.getSource();
                HBox aufruferHbox = (HBox) aufrufer.getParent();
                // Lösche HBox aus ArrayList und entferne Parent Node
                hBoxUnternehmenDynamisch.remove(aufruferHbox);
                anzahlUnternehmen--;
                vboxUnternehmen.getChildren().remove(aufruferHbox);
                System.out.println("loeschen");
            });

            int margin = 5;
            Insets inset = new Insets(margin, margin, margin, margin);
            hBox.getChildren().addAll(txtUnternehmensname, txtStartkapital, clrFarbe, btnLoeschen);
            HBox.setMargin(txtUnternehmensname, inset);
            HBox.setMargin(clrFarbe, inset);
            HBox.setMargin(txtStartkapital, inset);
            HBox.setMargin(btnLoeschen, inset);

            hBoxUnternehmenDynamisch.add(hBox);
            vboxUnternehmen.getChildren().add(index, hBox);
            anzahlUnternehmen++;

            System.out.println("hinzufuegen");
        }
    }

    @FXML
    void doSpeichern(ActionEvent event) {

        ArrayList<Unternehmen> unternehmen = new ArrayList<>();

        // Füge statische HBox und dynamische HBox zusammen
        ArrayList<HBox> hBoxUnternehmen = hBoxUnternehmenDynamisch;
        if (!hBoxUnternehmen.contains(hBoxUnternehmenStatisch))
            hBoxUnternehmen.add(hBoxUnternehmenStatisch);

        // HBox 2 Unternehmen
        for (HBox h : hBoxUnternehmen) {
            TextField txtUnternehmensname = (TextField) h.getChildren().get(0);
            TextField txtStartkapital = (TextField) h.getChildren().get(1);
            ColorPicker cpFarbe = (ColorPicker) h.getChildren().get(2);
            String unternehmensname = txtUnternehmensname.getText();
            String startkapital = txtStartkapital.getText();
            // Gibt Farbe als RGB Repräsentation zurück
            String farbe = cpFarbe.getValue().toString();
            Unternehmen u = new Unternehmen(unternehmensname, farbe);
            unternehmen.add(u);
        }
        //TODO: Fehlerbehandlung, dann speichern aktivieren

        //model.setUnternehmen(unternehmen);

        for (Unternehmen u : unternehmen)
            System.out.println(u);
        System.out.println("speichern");


    }

    // Zur Erklärung https://stackoverflow.com/a/51392331
    // Zur Erklärung https://javabeginners.de/Frameworks/JavaFX/FXML.php
    @FXML
    private void initialize() {
        model = Model.getInstanz();
        ArrayList<Unternehmen> unternehmen = model.getUnternehmen();
        // TODO: Hier auch Daten laden, wenn Administration geöffnet wird
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }
}

