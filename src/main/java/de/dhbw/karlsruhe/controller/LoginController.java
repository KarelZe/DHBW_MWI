package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Teilnehmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class LoginController {
    @FXML private TextField txt_benutzername;
    @FXML private TextField txt_passwort;

    @FXML
    private void doLogin(ActionEvent event)
    {
        String benutzername = txt_benutzername.getText();
        String passwortKlartext = txt_passwort.getText();
        String passwortVerschluesselt = EncryptionHelper.getStringAsMD5(passwortKlartext);
        System.out.println("["+ benutzername + ","+ passwortKlartext + "," + passwortVerschluesselt+ "]");

        // TODO: Beispiel um Teilnehmer anzuleg
//        Teilnehmer teilnehmer = new Teilnehmer();
//        teilnehmer.setNachname("Fabozzi");
//        teilnehmer.setVorname("Frank");
//        teilnehmer.setBenutzername(benutzername);
//        teilnehmer.setPasswort(passwortVerschluesselt);
//        Unternehmen unternehmen = new Unternehmen();
//        unternehmen.setId(1);
//        teilnehmer.setUnternehmen(unternehmen);
//        Rolle rolle = new Rolle();
//        rolle.setId(1);
//        teilnehmer.setRolle(rolle);

        SessionFactory factory;

        // TODO: Diese Konfiguration in XML-Datei setzen
        Configuration configuration = new Configuration()
                .addAnnotatedClass(de.dhbw.karlsruhe.model.Unternehmen.class)
                .addAnnotatedClass(de.dhbw.karlsruhe.model.Teilnehmer.class)
                .addAnnotatedClass(de.dhbw.karlsruhe.model.Rolle.class)
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hdm2ddl.auto", "create-drop");
        configuration.configure();

        factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // TODO: Beispiel um ein Unternehmen anzulegen
        //Unternehmen unternehmen = new Unternehmen();
        //unternehmen.setName("test");
        //unternehmen.setFarbe("#FF0000");
        //session.persist(unternehmen);
        //tx.commit();
        //session.close();

        // TODO: Beispiel Teilnehmer speichern
        //session.persist(teilnehmer);
        //tx.commit();
        //session.close();

        // TODO: Beispiel Teilnehmer abfragen valide Daten sind z. B. TheoTester und Anika
        String queryString = "from Teilnehmer WHERE benutzername =:benutzername AND passwort =:passwort";
        Query query = session.createQuery(queryString);
        query.setParameter("benutzername", benutzername);
        query.setParameter("passwort", passwortVerschluesselt);
        List teilnehmerListe = query.list();
        if (teilnehmerListe.isEmpty())
            System.out.println("Login falsch");
        else {
            Teilnehmer temp = (Teilnehmer) teilnehmerListe.get(0);
            System.out.println(temp + " @ " + temp.getUnternehmen() + " $ " + temp.getRolle());
        }

    }


}

