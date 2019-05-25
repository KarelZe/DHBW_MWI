package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UnternehmenRepository {

    /***
     * Persistiert ein Unternehmensobjekt in der Datenbank im Rahmen einer Transaktion.
     * @param unternehmen Unternehmensobjekt
     */
    public static void persistUnternehmen(Unternehmen unternehmen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(unternehmen);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
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
                session.save(u);
            }
            tx.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    //ToDo: Javadoc
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


}
