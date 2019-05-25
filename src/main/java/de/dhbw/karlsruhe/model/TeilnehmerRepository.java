package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
}
