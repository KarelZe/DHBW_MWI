package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ColorHelper;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class UnternehmenHBox extends HBox {
    private Unternehmen unternehmen;
    private TextField txtUnternehmensname;
    private TextField txtStartkapital;
    private ColorPicker clrFarbe;
    private Button btnLoeschen;

    UnternehmenHBox(Unternehmen unternehmen) {
        this();
        this.unternehmen = unternehmen;
        System.out.println(this.unternehmen);
        if (unternehmen != null) {

            txtStartkapital.setText("100");
            txtUnternehmensname.setText(unternehmen.getName());
            clrFarbe.setValue(ColorHelper.string2Color(unternehmen.getFarbe()));
        }
    }

    UnternehmenHBox() {
        this.txtUnternehmensname = new TextField();
        // TODO:Datennodell anpassen
        txtUnternehmensname.setPromptText("Unternehmensname");
        this.txtStartkapital = new TextField();
        // TODO: Eingabe Startkapital begrenzen
        txtStartkapital.setPromptText("Startkapital");
        this.clrFarbe = new ColorPicker();
        this.btnLoeschen = new Button("-");
        this.unternehmen = new Unternehmen();

        btnLoeschen.setOnAction(e -> {
            // Lösche HBox
            VBox parent = (VBox) this.getParent();
            parent.getChildren().remove(this);
            System.out.println("loeschen");
        });

        txtUnternehmensname.textProperty().addListener((observable, oldValue, newValue) -> unternehmen.setName(newValue));

        clrFarbe.valueProperty().addListener((observable, oldValue, newValue) -> {
            String farbe = ColorHelper.color2String(newValue);
            unternehmen.setFarbe(farbe);
        });

        int margin = 5;
        Insets inset = new Insets(margin, margin, margin, margin);
        this.getChildren().addAll(txtUnternehmensname, txtStartkapital, clrFarbe, btnLoeschen);
        HBox.setMargin(txtUnternehmensname, inset);
        HBox.setMargin(clrFarbe, inset);
        HBox.setMargin(txtStartkapital, inset);
        HBox.setMargin(btnLoeschen, inset);
    }

    public Unternehmen getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
    }
}
