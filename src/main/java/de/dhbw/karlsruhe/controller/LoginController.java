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


        Configuration configuration = new Configuration()
                .addAnnotatedClass(Teilnehmer.class)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", "jdbc:sqlite:../db/application.db")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hdm2ddl.auto", "create-drop");
        configuration.configure();

        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(teilnehmer);
        tx.commit();
        session.close();
    }


}

