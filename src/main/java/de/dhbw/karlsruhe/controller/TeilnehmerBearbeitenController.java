package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.JPA.Rolle;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class TeilnehmerBearbeitenController implements ControlledScreen {

    @FXML
    private TextField vornameFeld, nachnameFeld, passwortFeld, passwortBestaetigenFeld;

    @FXML
    private ComboBox<Unternehmen> unternehmenComboBox;

    private Teilnehmer testTeilnehmer=new Teilnehmer("test", "test", "vorTest", "nachTest", new Unternehmen(), new Rolle());
    private Teilnehmer teilnehmer=testTeilnehmer;

    private UnternehmenRepository model;
    private ScreenController screenController;


    @FXML
    private void aktualisieren(ActionEvent event) {

        System.out.println(teilnehmer);

        String vorname =vornameFeld.getText().trim();
        String nachname=nachnameFeld.getText().trim();
        String passwort1=passwortFeld.getText();
        String passwort2=passwortBestaetigenFeld.getText();

        // TODO: ist redundant zur Registrierugn?
        //Name prüfen
        if((vorname.trim().length()==0)||(nachname.trim().length()==0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ungeeignete Eingaben für Vor- und Nachname");
            alert.setContentText("Bitte geben Sie als Vor- und Nachnamen Namen mit mindestens einem Zeichen ein. Dies darf kein Leerzeichen sein");
            alert.showAndWait();
            return;
        }

        if((passwort1.length()>0)||(passwort2.length()>0)){

            //Passwortlänge prüfen
            if (passwort1.length()<5){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Passwort zu kurz");
                alert.setContentText("Bitte geben Sie ein Passwort mit mindestens 5 Zeichen ein.");
                alert.showAndWait();
                return;
            }

            //Passwortübereinstimmung prüfen
            if(!passwort1.equals(passwort2)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Passwort korrigieren");
                alert.setContentText("Bitte geben Sie in beiden Passwortfeldern das gleiche Passwort ein.");
                alert.showAndWait();
                return;
            }

            teilnehmer.setPasswort(EncryptionHelper.getStringAsMD5(passwort1));
        }



        teilnehmer.setVorname(vorname);
        teilnehmer.setNachname(nachname);

        //TeilnehmerRepository.persistTeilnehmer(teilnehmer);

        System.out.println(teilnehmer);

    }

    @FXML
    private void initialize(){
        vornameFeld.setText(teilnehmer.getVorname());
        nachnameFeld.setText(teilnehmer.getNachname());
        ArrayList<Unternehmen> unternehmen = UnternehmenRepository.getAlleUnternehmen();
        ObservableList<Unternehmen> unternehmenList = FXCollections.observableArrayList(unternehmen);
        unternehmenComboBox.setItems(unternehmenList);
        unternehmenComboBox.setConverter(new ConverterHelper().getUnternehmensConverter());
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }
}