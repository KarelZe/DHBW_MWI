package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Periode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Periode Perioden} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class PeriodenRepository implements CrudRepository<Periode> {

    private static PeriodenRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private PeriodenRepository() {
    }

    /**
     * Gibt Instanz des {@link PeriodenRepository PeriodenRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link PeriodenRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static PeriodenRepository getInstanz() {
        if (PeriodenRepository.instanz == null) {
            PeriodenRepository.instanz = new PeriodenRepository();
        }
        return instanz;
    }

    /**
     * Speichert ein {@link Periode} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param periode {@link Periode} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Periode periode) {
        save(List.of(periode));
    }

    /**
     * Speichert eine Liste von {@link Periode} Objekten in der Datenbank.
     *
     * @param perioden Liste von {@link Periode} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Periode> perioden) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Periode p : perioden) {
                tx = session.beginTransaction();
                session.saveOrUpdate(p);
                tx.commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Periode periode) {
        delete(List.of(periode));
    }

    @Override
    public void delete(List<Periode> perioden) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Periode p : perioden) {
                session.delete(p);
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
    public Optional<Periode> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Periode where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Periode periode = (Periode) query.uniqueResult();
            if (periode != null)
                return Optional.of(periode);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<Periode> findAll() {
        throw new UnsupportedOperationException();
    }

    public List<Periode> findAllBySpieleId(long spieleId) {
        Transaction tx = null;
        ArrayList<Periode> perioden = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Periode where spiel_id = :spiel_id";
            Query query = session.createQuery(queryString);
            query.setParameter("spiel_id", spieleId);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) perioden.add((Periode) o);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return perioden;
    }
}
