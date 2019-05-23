package de.dhbw.karlsruhe.model;


import de.dhbw.karlsruhe.helper.HibernateHelper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    /**
     * Methode zur Speicherung von Teilnehmern in der Datenbank.
     *
     * @param teilnehmer Teilnehmer
     */
    public void setTeilnehmer(Teilnehmer teilnehmer) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(teilnehmer);
            tx.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }
}
