package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Kurs} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class KursRepository implements CrudRepository<Kurs> {

    private static KursRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private KursRepository() {
    }

    /**
     * Gibt Instanz des {@link KursRepository KursRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link KursRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static KursRepository getInstanz() {
        if (KursRepository.instanz == null) {
            KursRepository.instanz = new KursRepository();
        }
        return instanz;
    }

    /**
     * Speichert ein {@link Kurs} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param kurs {@link Kurs} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Kurs kurs) {
        save(List.of(kurs));
    }

    /**
     * Speichert eine Liste von {@link Kurs} Objekten in der Datenbank.
     *
     * @param kurse Liste von {@link Kurs} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Kurs> kurse) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Kurs k : kurse) {
                tx = session.beginTransaction();
                session.saveOrUpdate(k);
                tx.commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Gibt die Anzahl an {@link Kurs} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link Kurs Kursen}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Kurs kurs) {
        delete(List.of(kurs));
    }

    /**
     * Löscht eine Liste von {@link Kurs} Objekten aus der Datenbank, sofern vorhanden.
     * @param kurse Liste von {@link Kurs} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(List<Kurs> kurse) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Kurs k : kurse) {
                session.delete(k);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    @Override
    public boolean existsById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Kurs> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Kurs where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Kurs kurs = (Kurs) query.uniqueResult();
            if (kurs != null)
                return Optional.of(kurs);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    public List<Kurs> findByPeriodenId(long periodeId) {
        Transaction tx = null;
        ArrayList<Kurs> kurse = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Kurs where periode_id = :periode_id";
            Query query = session.createQuery(queryString);
            query.setParameter("periode_id", periodeId);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) kurse.add((Kurs) o);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return kurse;
    }

    /**
     * Diese Methode gibt den Kurs eines Wertpapiers für eine Periode zurück.
     *
     * @param periodeId    Id der abzufragenden Periode
     * @param wertpapierId Id des abzufragenden Wertpapiers
     * @return Optional mit Kurs oder Platzhalter
     */

    public Optional<Kurs> findByPeriodenIdAndWertpapierId(long periodeId, long wertpapierId) {
        Transaction tx = null;
        ArrayList<Kurs> kurse = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Kurs where periode_id = :periode_id and wertpapier_id = :wertpapier_id";
            Query query = session.createQuery(queryString);
            query.setParameter("periode_id", periodeId);
            query.setParameter("wertpapier_id", wertpapierId);
            tx.commit();
            Kurs kurs = (Kurs) query.uniqueResult();
            if (kurs != null)
                return Optional.of(kurs);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<Kurs> findAll() {
        throw new UnsupportedOperationException();
    }
}
