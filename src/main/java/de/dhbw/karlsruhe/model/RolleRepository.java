package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RolleRepository {

    public static Rolle findById(long id) {
        Transaction tx = null;
        Rolle rolle = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Rolle WHERE id =: id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            rolle = (Rolle) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return rolle;
    }

    /**
     * Persistiert ein Rollenobjekt in der Datenbank im Rahmen einer Transaktion.
     *
     * @param rolle Rolle
     */
    public static void persistRolle(Rolle rolle) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(rolle);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }
}
