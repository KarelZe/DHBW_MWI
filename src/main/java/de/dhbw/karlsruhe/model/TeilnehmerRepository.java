package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Rolle;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TeilnehmerRepository {
    /**
     * Persistiert ein Teilnehmerobjekt in der Datenbank im Rahmen einer Transaktion.
     *
     * @param teilnehmer Teilnehmer
     */
    public static void persistTeilnehmer(Teilnehmer teilnehmer) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(teilnehmer);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /***
     * Gibt eine Liste aller Teilnehmer (mit Seminarleiter!) aus der Datenbank zurück.
     * @return Liste aller Teilnehmer
     */
    public static List<Teilnehmer> getAlleTeilnehmer() {
        Transaction tx = null;
        List<Teilnehmer> alleTeilnehmer = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer where rolle_id = :rolle_id AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("rolle_id", Rolle.ROLLE_TEILNEHMER);
            query.setParameter("spiel", AktuelleSpieldaten.getSpiel());
            tx.commit();
            for (final Object o : query.list()) {
                alleTeilnehmer.add((Teilnehmer) o);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return alleTeilnehmer;
    }

    public static Teilnehmer getTeilnehmerById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            return (Teilnehmer) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return null;
    }

    public static Teilnehmer getTeilnehmerByBenutzername(String benutzername) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer where benutzername = :benutzername";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            tx.commit();
            return (Teilnehmer) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return null;
    }
}
