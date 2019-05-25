package de.dhbw.karlsruhe.model;


import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;


public class LoginRepository {

    /**
     * Methode zur Abfrage von Teilnehmer aus Datenbank. Hierbei werden Transaktionen zur Abfrage benutzt.
     * Ist eine Abfrage nicht vollständig möglich, erfolgt ein Rollback.
     * Mögliche Testdaten umfassen benutzername = TheoTester und Passwort = Anika
     *
     * @param benutzername Benutzername des Teilnehmers
     * @param passwort     Passwort des Teilnehmers
     * @return Teilnehmer oder null
     */
    public static Teilnehmer getTeilnehmerByBenutzernameAndPasswort(String benutzername, String passwort) {
        Transaction tx = null;
        Teilnehmer teilnehmer = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer WHERE benutzername =:benutzername AND passwort =:passwort";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("passwort", passwort);
            tx.commit();
            teilnehmer = (Teilnehmer) query.uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return teilnehmer;
    }
}
