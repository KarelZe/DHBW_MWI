package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Rolle;
import de.dhbw.karlsruhe.model.JPA.Spiel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Iterator;

public class RolleRepository {

    public static Rolle getSeminarleiterRolle() {
        Transaction tx = null;
        Rolle rolle = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Rolle WHERE id =:id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", Rolle.ROLLE_SPIELLEITER);
            tx.commit();
            rolle = (Rolle) query.uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return rolle;
    }

    public static void insertRollenIfNotExists() {
        Transaction tx = null;
        Rolle rolle = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "select count(id) from Rolle";
            Query query = session.createQuery(queryString);
            Iterator it = query.iterate();
            if(it.hasNext()) {
                long anzahlZeilen = (Long) it.next();
                tx.commit();

                if(anzahlZeilen == 0) {
                    Rolle teilnehmerRolle = new Rolle();
                    teilnehmerRolle.setName("Teilnehmer");
                    RolleRepository.persistRolle(teilnehmerRolle);

                    Rolle seminarleiterRolle = new Rolle();
                    seminarleiterRolle.setName("Seminarleiter");
                    RolleRepository.persistRolle(seminarleiterRolle);
                }
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }

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
