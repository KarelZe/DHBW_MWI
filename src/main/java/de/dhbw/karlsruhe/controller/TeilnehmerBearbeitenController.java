/*package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Model;
import de.dhbw.karlsruhe.model.Teilnehmer;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class TeilnehmerBearbeitenController implements ControlledScreen {

    private TextField vornameFeld, nachnameFeld, passwortFeld, passwortBestaetigenFeld;
    private ComboBox<Unternehmen> unternehmenComboBox;
    private Teilnehmer teilnehmer;

    private Model model;
    private ScreensController controller;

    private void aktualisieren(ActionEvent event) {
       System.out.println("Läuft");

       String vorname =vornameFeld.getText().trim();
       String nachname=nachnameFeld.getText().trim();
       String passwort1=passwortFeld.getText();
       String passwort2=passwortBestaetigenFeld.getText();

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

    }

    private void standardbelegungFuellen(){
        vornameFeld.setText(teilnehmer.getVorname());
        nachnameFeld.setText(teilnehmer.getNachname());
        ArrayList<Unternehmen> unternehmen = model.getUnternehmen();
        ObservableList<Unternehmen> unternehmenObservable = FXCollections.observableArrayList(unternehmen);
        unternehmenComboBox.setItems(unternehmenObservable);
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }
}
*/