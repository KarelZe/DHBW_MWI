package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Spiel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SpielRepository {

    /**
     * Persistiert ein Spielobjekt in der Datenbank im Rahmen einer Transaktion.
     *
     * @param spiel Spiel
     */
    public static void persistSpiel(Spiel spiel) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(spiel);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    public static Spiel getAktivesSpiel() {
        Transaction tx = null;
        Spiel spiel = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Spiel WHERE ist_aktiv =:ist_aktiv";
            Query query = session.createQuery(queryString);
            query.setParameter("ist_aktiv", Spiel.SPIEL_AKTIV);
            tx.commit();
            spiel = (Spiel) query.uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return spiel;
    }
}
