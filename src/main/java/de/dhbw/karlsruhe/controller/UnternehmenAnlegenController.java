package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ColorHelper;
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
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class UnternehmenAnlegenController implements ControlledScreen {

    private final int MAX_ANZAHL_UNTERNEHMEN = 10;
    @FXML
    public Button btnSpeichern;
    @FXML
    public Label lblUnternehmen;
    @FXML
    private VBox vboxUnternehmen;

    // statische Unternehmen
    @FXML
    private TextField txtUnternehmensnameStatisch, txtStartkapitalStatisch;

    @FXML
    private ColorPicker clrFarbeStatisch;

    @FXML
    private HBox hBoxUnternehmenStatisch;

    @FXML
    private String unternehmensname, startkapital;

    private ArrayList<HBox> hBoxUnternehmenDynamisch = new ArrayList<>();

    private Model model;
    private ScreensController controller;

    private ArrayList<Unternehmen> unternehmenAlle;

    @FXML
    void doHinzufuegen(ActionEvent event) {
        if (hBoxUnternehmenDynamisch.size() < MAX_ANZAHL_UNTERNEHMEN) {
            // Unternehmen -> HBox
            HBox hBox = unternehmen2hbox(null);
            // Füge Elemente vor Speichern Button hinzu
            int index = vboxUnternehmen.getChildren().size() - 1;
            hBoxUnternehmenDynamisch.add(hBox);
            vboxUnternehmen.getChildren().add(index, hBox);
            System.out.println("hinzufuegen");
        }
    }

    @FXML
    void doSpeichern(ActionEvent event) {

        // Füge statische HBox und dynamische HBox zusammen
        ArrayList<HBox> hBoxUnternehmen = hBoxUnternehmenDynamisch;
        if (!hBoxUnternehmen.contains(hBoxUnternehmenStatisch))
            hBoxUnternehmen.add(hBoxUnternehmenStatisch);

        // FIXME: Das kann zu Problemen wegen veränderter IDs führen?
        unternehmenAlle = new ArrayList<>();

        // HBox -> Unternehmen
        for (HBox h : hBoxUnternehmen) {
            Unternehmen u = hbox2unternehmen(h);
            unternehmenAlle.add(u);
        }

        model.setUnternehmen(unternehmenAlle);

        for (Unternehmen u : unternehmenAlle)
            System.out.println(u);
        System.out.println("speichern");
    }

    @FXML
    private void initialize() {
        model = Model.getInstanz();
        unternehmenAlle = model.getUnternehmen();
        if (!unternehmenAlle.isEmpty()) {
            // Füge erste Zeile als statische Zeile hinzu
            Unternehmen unternehmenStatisch = unternehmenAlle.get(0);
            txtUnternehmensnameStatisch.setText(unternehmenStatisch.getName());
            // FIXME: @ Ändere Datenmodell -> Startkapital
            txtStartkapitalStatisch.setText("100");
            clrFarbeStatisch.setValue(ColorHelper.string2Color(unternehmenStatisch.getFarbe()));
            // Füge zusätzliche Zeilen als dynamische Zeilen hinzu
            IntStream.range(1, unternehmenAlle.size()).mapToObj(i -> unternehmen2hbox(unternehmenAlle.get(1))).forEach(hBox -> {
                hBoxUnternehmenDynamisch.add(hBox);
                vboxUnternehmen.getChildren().add(2, hBox);
            });
        }
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }


    private HBox unternehmen2hbox(Unternehmen unternehmen) {
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
            hBoxUnternehmenDynamisch.remove(aufruferHbox);
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

        if (unternehmen != null) {
            txtStartkapital.setText("100");
            txtUnternehmensname.setText(unternehmen.getName());
            clrFarbe.setValue(ColorHelper.string2Color(unternehmen.getFarbe()));
        }
        return hBox;
    }

    private Unternehmen hbox2unternehmen(HBox hBox) {
        // lese Nodes aus
        TextField txtUnternehmensname = (TextField) hBox.getChildren().get(0);
        TextField txtStartkapital = (TextField) hBox.getChildren().get(1);
        ColorPicker cpFarbe = (ColorPicker) hBox.getChildren().get(2);

        // Speichere Ergebnisse zwischen
        String unternehmensname = txtUnternehmensname.getText();
        String startkapital = txtStartkapital.getText();
        Color farbe = cpFarbe.getValue();
        return new Unternehmen(unternehmensname, ColorHelper.color2String(farbe));
    }


}

