package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.EncryptionHelper;
import de.dhbw.karlsruhe.model.Teilnehmer;
import de.dhbw.karlsruhe.model.Unternehmen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


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

        Teilnehmer teilnehmer = new Teilnehmer();
        teilnehmer.setBenutzername(benutzername);
        teilnehmer.setPasswort(passwortVerschluesselt);

        SessionFactory factory;

        // TODO: Diese Konfiguration in XML-Datei setzen
        Configuration configuration = new Configuration()
                .addAnnotatedClass(de.dhbw.karlsruhe.model.Unternehmen.class)
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hdm2ddl.auto", "create-drop");
        configuration.configure();

        factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setName("test");
        unternehmen.setFarbe("#FF0000");
        session.persist(unternehmen);
        tx.commit();
        session.close();
    }


}

