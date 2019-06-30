package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.TransaktionsArt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link TransaktionsArt} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class TransaktionsArtRepository implements CrudRepository<TransaktionsArt> {

    private static TransaktionsArtRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private TransaktionsArtRepository() {
    }

    /**
     * Gibt Instanz des {@link TransaktionsArtRepository TransaktionsArtRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link TransaktionsArtRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static TransaktionsArtRepository getInstanz() {
        if (TransaktionsArtRepository.instanz == null) {
            TransaktionsArtRepository.instanz = new TransaktionsArtRepository();
        }
        return instanz;
    }

    // TODO: Richtig mit HQL implementieren, sofern bekannt ist, ob wirklich benötigt
    @Override
    public long count() {
        return findAll().size();
    }

    // TODO: Richtig mit HQL implementieren, sofern bekannt ist, ob wirklich benötigt
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<TransaktionsArt> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from TransaktionsArt where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            TransaktionsArt transaktionsArt = (TransaktionsArt) query.uniqueResult();
            if (transaktionsArt != null)
                return Optional.of(transaktionsArt);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<TransaktionsArt> findAll() {
        Transaction tx = null;
        ArrayList<TransaktionsArt> transaktionsArten = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from TransaktionsArt";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                transaktionsArten.add((TransaktionsArt) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return transaktionsArten;
    }


    /**
     * Speichert eine Liste von {@link TransaktionsArt} Objekten in der Datenbank.
     *
     * @param transaktionsArten Liste von {@link TransaktionsArt} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<TransaktionsArt> transaktionsArten) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (TransaktionsArt e : transaktionsArten) {
                tx = session.beginTransaction();
                session.saveOrUpdate(e);
                tx.commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Speichert ein {@link TransaktionsArt} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param transaktionsArt {@link TransaktionsArt} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(TransaktionsArt transaktionsArt) {
        save(List.of(transaktionsArt));
    }

    @Override
    public void delete(List<TransaktionsArt> transaktionsArten) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (TransaktionsArt e : transaktionsArten) {
                session.delete(e);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }
    @Override
    public void delete(TransaktionsArt transaktionsArt) {
        delete(List.of(transaktionsArt));
    }

}
