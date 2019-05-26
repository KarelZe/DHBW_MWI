package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Teilnehmer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
            String queryString = "from Teilnehmer";
            //String queryString = "from Teilnehmer inner join Rolle r with r.id = :rolle_id"; //ToDo: Einschränkung auf Teilnehmer funktioniert nicht
            Query query = session.createQuery(queryString);
            //query.setParameter("rolle_id", Long.valueOf(Berechtigungsrolle.TEILNEHMER.ordinal()));
            tx.commit();
            /*List<Object[]> dbResult = query.getResultList();
            Iterator it = dbResult.iterator();
            while(it.hasNext()) {
                Object[] zeile = (Object[]) it.next();
                alleTeilnehmer.add((Teilnehmer) zeile[0]);;
            }*/
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
}
