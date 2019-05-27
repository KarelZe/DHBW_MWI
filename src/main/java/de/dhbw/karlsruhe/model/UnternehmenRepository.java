package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UnternehmenRepository {

    /***
     * Persistiert ein Unternehmensobjekt in der Datenbank im Rahmen einer Transaktion.
     * @param unternehmen Unternehmensobjekt
     */
    public static void persistUnternehmen(Unternehmen unternehmen) {
        persistUnternehmen(new ArrayList<>(List.of(unternehmen)));
    }

    /***
     * Persistiert eine Liste von Unternehmensobjekten in der Datenbank im Rahmen einer Transaktion.
     * @param unternehmen Liste von Unternehmen
     */
    public static void persistUnternehmen(ArrayList<Unternehmen> unternehmen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Unternehmen u : unternehmen) {
                session.saveOrUpdate(u);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    public static void deleteUnternehmen(Unternehmen unternehmen) {
        deleteUnternehmen((ArrayList<Unternehmen>) List.of(unternehmen));
    }

    public static void deleteUnternehmen(ArrayList<Unternehmen> unternehmen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Unternehmen u : unternehmen) {
                session.delete(u);
            }
            tx.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    public static ArrayList<Unternehmen> getAlleUnternehmen() {
        Transaction tx = null;
        ArrayList<Unternehmen> unternehmen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                unternehmen.add((Unternehmen) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return unternehmen;
    }

    public static ArrayList<Unternehmen> getAlleSpielbarenUnternehmen() {
        Transaction tx = null;
        ArrayList<Unternehmen> unternehmen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen where ist_spielbar = :ist_spielbar";
            Query query = session.createQuery(queryString);
            query.setParameter("ist_spielbar", Unternehmen.UNTERNEHMEN_TEILNEHMER);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                unternehmen.add((Unternehmen) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return unternehmen;
    }

}
