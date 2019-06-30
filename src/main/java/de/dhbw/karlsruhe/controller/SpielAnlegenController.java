package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ColorHelper;
import de.dhbw.karlsruhe.helper.ConstantsHelper;
import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.*;
import de.dhbw.karlsruhe.model.jpa.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SpielAnlegenController implements ControlledScreen {

    @FXML
    private TextField txtStartkapital;

    private ScreenController screenController;

    private Spiel neuesSpiel;

    private UnternehmenRepository unternehmenRepository = UnternehmenRepository.getInstanz();

    private WertpapierRepository wertpapierRepository = WertpapierRepository.getInstanz();

    private WertpapierArtRepository wertpapierArtRepository = WertpapierArtRepository.getInstanz();

    private TransaktionsArtRepository transaktionsArtRepository = TransaktionsArtRepository.getInstanz();

    /**
     * Eventhandler für Spiel-Anlagen-Button
     * @param event
     * @author Christian Fix
     */
    @FXML
    private void doSpielAnlegen(ActionEvent event) {
        //Das neu angelegte Spiel wird immer auf AKTIV gesetzt
        this.neuesSpiel = new Spiel();
        try {
            double startkapital = Double.valueOf(txtStartkapital.getText());
            if(startkapital > 0.0) {
                this.neuesSpiel.setStartkapital(startkapital);
            }
            else { //Startkapital <= 0.0
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Spiel anlegen");
                alert.setContentText("Das Startkapital muss positiv sein.");
                alert.showAndWait();
                return;
            }

            this.neuesSpiel.setErstellungsdatum(new Date());
            if (AktuelleSpieldaten.getInstanz().getSpiel() == null) { //kein Spiel gesetzt -> erstelltes Spiel auf aktiv setzen
                this.neuesSpiel.setIst_aktiv(Spiel.SPIEL_AKTIV);
            } else {
                AktuelleSpieldaten.getInstanz().getSpiel().setIst_aktiv(Spiel.SPIEL_INAKTIV);
                this.neuesSpiel.setIst_aktiv(Spiel.SPIEL_AKTIV);
                SpielRepository.getInstanz().save(AktuelleSpieldaten.getInstanz().getSpiel());
            }

            initializeSpielInDB();

            screenController.loadScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN, ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN_FILE);
            screenController.setScreen(ScreensFramework.SCREEN_UNTERNEHMEN_ANLEGEN);

        } catch (NumberFormatException e) { //keine Zahl beim Startkapital eingegeben
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Spiel anlegen");
            alert.setContentText("Bitte geben Sie eine Zahl ein.");
            alert.showAndWait();
        }


    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    /**
     * Initialisiert das Spiel in der Datenbank
     * @author Christian Fix
     */
    private void initializeSpielInDB() {
        SpielRepository.getInstanz().save(this.neuesSpiel);
        AktuelleSpieldaten.getInstanz().setSpiel(this.neuesSpiel);

        insertRollenInDBIfNotExists();
        insertWertpapierArtenInDBIfNotExists();
        insertTransaktionsArtenInDBIfNotExists();
        insertAdminInDB();
        insertUnternehmenInDB();
        insertWertpapiereInDB();
        insertFirstPeriodeInDB();
    }


    /**
     * Legt Rollen in der Datenbank an, wenn sie noch nicht existieren (z.B. bei Neuaufsetzung der Datenbank)
     * @author Christian Fix
     */
    private void insertRollenInDBIfNotExists() {
        if(RolleRepository.getInstanz().findById(Rolle.ROLLE_TEILNEHMER).isEmpty()) { //Teilnehmerrolle existiert noch nicht in der Datenbank
            Rolle teilnehmerRolle = new Rolle();
            teilnehmerRolle.setName("Teilnehmer");
            teilnehmerRolle.setId(1);
            RolleRepository.getInstanz().save(teilnehmerRolle);
        }
        if(RolleRepository.getInstanz().findById(Rolle.ROLLE_SPIELLEITER).isEmpty()) { //Spielleiterrolle existiert noch nicht in der Datenbank
            Rolle seminarleiterRolle = new Rolle();
            seminarleiterRolle.setName("Seminarleiter");
            seminarleiterRolle.setId(2);
            RolleRepository.getInstanz().save(seminarleiterRolle);
        }
    }

    /**
     * Legt Wertpapiere in der Datenbank an, wenn sie noch nicht existieren (z.B. bei Neuaufsetzung der Datenbank)
     * @author Christian Fix
     */
    private void insertWertpapierArtenInDBIfNotExists() {
        Optional<WertpapierArt> optional;
        //Aktie
        optional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_AKTIE);
        if (optional.isEmpty()) {
            WertpapierArt aktie = new WertpapierArt();
            aktie.setId(WertpapierArt.WERTPAPIER_AKTIE);
            aktie.setName(WertpapierArt.WERTPAPIER_AKTIE_NAME);
            wertpapierArtRepository.save(aktie);
        }

        //Anleihe
        optional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_ANLEIHE);
        if (optional.isEmpty()) {
            WertpapierArt anleihe = new WertpapierArt();
            anleihe.setId(WertpapierArt.WERTPAPIER_ANLEIHE);
            anleihe.setName(WertpapierArt.WERTPAPIER_ANLEIHE_NAME);
            wertpapierArtRepository.save(anleihe);
        }

        //Festgeld
        optional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_FESTGELD);
        if (optional.isEmpty()) {
            WertpapierArt festgeld = new WertpapierArt();
            festgeld.setId(WertpapierArt.WERTPAPIER_FESTGELD);
            festgeld.setName(WertpapierArt.WERTPAPIER_FESTGELD_NAME);
            wertpapierArtRepository.save(festgeld);
        }

        //ETF (auf GMAX)
        optional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_ETF);
        if (optional.isEmpty()) {
            WertpapierArt etf = new WertpapierArt();
            etf.setId(WertpapierArt.WERTPAPIER_ETF);
            etf.setName(WertpapierArt.WERTPAPIER_ETF_NAME);
            wertpapierArtRepository.save(etf);
        }

        // Wertpapier für Startkapital
        optional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_STARTKAPITAL);
        if (optional.isEmpty()) {
            WertpapierArt etf = new WertpapierArt();
            etf.setId(WertpapierArt.WERTPAPIER_STARTKAPITAL);
            etf.setName(WertpapierArt.WERTPAPIER_STARTKAPITAL_NAME);
            wertpapierArtRepository.save(etf);
        }
    }

    /**
     * Legt TransaktionsArten in der Datenbank an, wenn sie noch nicht existieren (z.B. bei Neuaufsetzung der Datenbank)
     * @author Christian Fix
     */
    private void insertTransaktionsArtenInDBIfNotExists() {
        Optional<TransaktionsArt> optional;
        //Kaufen
        optional = transaktionsArtRepository.findById(TransaktionsArt.TRANSAKTIONSART_KAUFEN);
        if (optional.isEmpty()) {
            TransaktionsArt kaufen = new TransaktionsArt();
            kaufen.setId(TransaktionsArt.TRANSAKTIONSART_KAUFEN);
            kaufen.setBeschreibung(TransaktionsArt.TRANSAKTIONSART_KAUFEN_NAME);
            transaktionsArtRepository.save(kaufen);
        }

        //Verkaufen
        optional = transaktionsArtRepository.findById(TransaktionsArt.TRANSAKTIONSART_VERKAUFEN);
        if (optional.isEmpty()) {
            TransaktionsArt verkaufen = new TransaktionsArt();
            verkaufen.setId(TransaktionsArt.TRANSAKTIONSART_VERKAUFEN);
            verkaufen.setBeschreibung(TransaktionsArt.TRANSAKTIONSART_VERKAUFEN_NAME);
            transaktionsArtRepository.save(verkaufen);
        }

        //Zinsgutschrift Wertpapier
        optional = transaktionsArtRepository.findById(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER);
        if (optional.isEmpty()) {
            TransaktionsArt zinsgutschrift = new TransaktionsArt();
            zinsgutschrift.setId(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER);
            zinsgutschrift.setBeschreibung(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER_NAME);
            transaktionsArtRepository.save(zinsgutschrift);
        }

        //Zinsgutschrift Festgeld
        optional = transaktionsArtRepository.findById(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_FESTGELD);
        if (optional.isEmpty()) {
            TransaktionsArt zinsgutschrift = new TransaktionsArt();
            zinsgutschrift.setId(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_FESTGELD);
            zinsgutschrift.setBeschreibung(TransaktionsArt.TRANSAKTIONSART_ZINSGUTSCHRIFT_FESTGELD_NAME);
            transaktionsArtRepository.save(zinsgutschrift);
        }

        //Startkapital
        optional = transaktionsArtRepository.findById(TransaktionsArt.TRANSAKTIONSART_STARTKAPITAL);
        if (optional.isEmpty()) {
            TransaktionsArt startkapital = new TransaktionsArt();
            startkapital.setId(TransaktionsArt.TRANSAKTIONSART_STARTKAPITAL);
            startkapital.setBeschreibung(TransaktionsArt.TRANSAKTIONSART_STARTKAPITAL_NAME);
            transaktionsArtRepository.save(startkapital);
        }
    }

    //TODO: UnternehmensID von Teilnehmer ist Null, gibt das eventuell irgendwo NullpointerExceptions?
    /**
     * Fügt einen Spielleiteraccount in der Datenbank ein
     * @author Christian Fix
     */
    private void insertAdminInDB() {
        Teilnehmer spielleiter = new Teilnehmer();
        spielleiter.setBenutzername(ConstantsHelper.ADMIN_USERNAME);
        spielleiter.setPasswort(EncryptionHelper.getStringAsMD5(ConstantsHelper.ADMIN_PASSWORT));
        spielleiter.setVorname("Admin");
        spielleiter.setNachname("Admin");
        Optional<Rolle> optionalRolle = RolleRepository.getInstanz().findById(Rolle.ROLLE_SPIELLEITER);
        optionalRolle.ifPresent(spielleiter::setRolle);
        spielleiter.setSpiel(this.neuesSpiel);
        TeilnehmerRepository.getInstanz().save(spielleiter);
    }

    /**
     * Fügt die Unternehmen "GMAX" (für ETF) und "Bank" (für Festgeld) in der Datenbank ein
     * @author Christian Fix
     */
    private void insertUnternehmenInDB() {
        //GMAX (für ETF auf alle Unternehmen)
        Unternehmen gmax = new Unternehmen();
        gmax.setFarbe(ColorHelper.color2String(Color.BLACK));
        gmax.setName("GMAX");
        gmax.setUnternehmenArt(Unternehmen.UNTERNEHMEN_KAPITALANLAGEGESELLSCHAFT);
        gmax.setSpiel(AktuelleSpieldaten.getInstanz().getSpiel());
        unternehmenRepository.save(gmax);

        //Bank (für Festgeld)
        Unternehmen bank = new Unternehmen();
        bank.setFarbe(ColorHelper.color2String(Color.BLACK));
        bank.setName("Bank");
        bank.setUnternehmenArt(Unternehmen.UNTERNEHMEN_BANK);
        bank.setSpiel(AktuelleSpieldaten.getInstanz().getSpiel());
        unternehmenRepository.save(bank);
    }

    /**
     * Fügt die Wertpapiere "ETF", "Festgeld" und "Startkapital" in die Datenbank ein
     * @author Christian Fix
     */
    private void insertWertpapiereInDB() {
        //ETF auf GMAX
        Wertpapier etf = new Wertpapier();
        etf.setName("ETF");
        Optional<WertpapierArt> etfOptional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_ETF);
        etfOptional.ifPresent(etf::setWertpapierArt);

        List<Unternehmen> etfUnternehmen = unternehmenRepository.findByUnternehmenArt(Unternehmen.UNTERNEHMEN_KAPITALANLAGEGESELLSCHAFT);
        if (etfUnternehmen.size() >= 1)
            etf.setUnternehmen(etfUnternehmen.get(0));
        wertpapierRepository.save(etf);

        //Festgeld
        Wertpapier festgeld = new Wertpapier();
        festgeld.setEmission_periode(0);
        festgeld.setName("Festgeld");

        Optional<WertpapierArt> festgeldOptional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_FESTGELD);
        festgeldOptional.ifPresent(festgeld::setWertpapierArt);

        List<Unternehmen> festgeldUnternehmen = unternehmenRepository.findByUnternehmenArt(Unternehmen.UNTERNEHMEN_BANK);
        if (festgeldUnternehmen.size() >= 1)
            festgeld.setUnternehmen(festgeldUnternehmen.get(0));
        wertpapierRepository.save(festgeld);

        // Startkapital
        Wertpapier startkapital = new Wertpapier();
        startkapital.setName("Startkapital");
        Optional<WertpapierArt> startkapitalOptional = wertpapierArtRepository.findById(WertpapierArt.WERTPAPIER_STARTKAPITAL);
        startkapitalOptional.ifPresent(startkapital::setWertpapierArt);

        List<Unternehmen> startkapitalUnternehmen = unternehmenRepository.findByUnternehmenArt(Unternehmen.UNTERNEHMEN_BANK);
        if (startkapitalUnternehmen.size() >= 1)
            startkapital.setUnternehmen(etfUnternehmen.get(0));
        wertpapierRepository.save(startkapital);
    }

    /**
     * Fügt eine erste Periode in die Datenbank ein
     */
    private void insertFirstPeriodeInDB() {
        Periode periode = new Periode(AktuelleSpieldaten.getInstanz().getSpiel(), 0, 0); //TODO: überlegen, ob die erste Periode konfigurierbar gemacht wird bei Spielanlegen.
        PeriodenRepository.getInstanz().save(periode);

        // TODO: Das hier legt für jedes Wertpapier (also auch die von anderen Spielen) einen neuen Kurs an
       /* List<Wertpapier> wertpapiere = wertpapierRepository.findAll();
        List<Kurs> kurse = wertpapiere.stream().map(wertpapier -> new Kurs(periode, wertpapier)).collect(Collectors.toList());
        KursRepository.getInstanz().save(kurse);*/
    }
}
