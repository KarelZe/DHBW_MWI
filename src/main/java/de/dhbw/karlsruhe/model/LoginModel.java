package de.dhbw.karlsruhe.model;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class LoginModel {

    private static LoginModel instanz;
    private static SessionFactory factory;

    private LoginModel() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return
     */
    public static LoginModel getInstanz() {
        if (LoginModel.instanz == null) {
            LoginModel.instanz = new LoginModel();
            // TODO: Refactoren in separate Klasse.
            // Initialisiere Factory
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(de.dhbw.karlsruhe.model.Unternehmen.class)
                    .addAnnotatedClass(de.dhbw.karlsruhe.model.Teilnehmer.class)
                    .addAnnotatedClass(de.dhbw.karlsruhe.model.Rolle.class)
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.hdm2ddl.auto", "create-drop");
            configuration.configure();
            factory = configuration.buildSessionFactory();
        }
        return instanz;
    }

    /**
     * Methode zur Abfrage von Teilnehmer aus Datenbank. Hierbei werden Transaktionen zur Abfrage benutzt.
     * Ist eine Abfrage nicht vollständig möglich, erfolgt ein Rollback.
     * Mögliche Testdaten umfassen benutzername = TheoTester und Passwort = Anika
     *
     * @param benutzername Benutzername des Teilnehmers
     * @param passwort     Passwort des Teilnehmers
     * @return Teilnehmer oder null
     */
    public Teilnehmer getTeilnehmer(String benutzername, String passwort) {
        Session session = factory.openSession();
        Transaction tx = null;
        Teilnehmer teilnehmer = null;
        try (session) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer WHERE benutzername =:benutzername AND passwort =:passwort";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("passwort", passwort);
            tx.commit();
            teilnehmer = (Teilnehmer) query.uniqueResult();

        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
        }
        return teilnehmer;
    }
}
