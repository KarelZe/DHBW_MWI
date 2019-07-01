package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link WertpapierArt} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class WertpapierArtRepository implements CrudRepository<WertpapierArt> {

    private static WertpapierArtRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private WertpapierArtRepository() {
    }

    /**
     * Gibt Instanz des {@link WertpapierArtRepository WertpapierArtRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link WertpapierArtRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static WertpapierArtRepository getInstanz() {
        if (WertpapierArtRepository.instanz == null) {
            WertpapierArtRepository.instanz = new WertpapierArtRepository();
        }
        return instanz;
    }

    /**
     * Gibt die Anzahl an {@link WertpapierArt} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link WertpapierArt WertpapierArten}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Frägt das Vorhandensein eines {@link WertpapierArt} Objekts in der Datenbank ab.
     * @param id Id der abzufragenden {@link WertpapierArt}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }


    /**
     * Abfrage eines {@link WertpapierArt} Objekts anhand der Id der {@link WertpapierArt} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id der zu findenden {@link WertpapierArt}
     * @return Optional ist ein Container für {@link WertpapierArt}, um vereinfacht das Vorhandensein der {@link WertpapierArt} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public Optional<WertpapierArt> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from WertpapierArt where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            WertpapierArt wertpapierArt = (WertpapierArt) query.uniqueResult();
            if (wertpapierArt != null)
                return Optional.of(wertpapierArt);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage aller {@link WertpapierArt} Objekte in der Datenbank.
     * @return Liste mit {@link WertpapierArt} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public List<WertpapierArt> findAll() {
        Transaction tx = null;
        ArrayList<WertpapierArt> wertpapierArten = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from WertpapierArt";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                wertpapierArten.add((WertpapierArt) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return wertpapierArten;
    }

    /**
     * Speichert eine Liste von {@link WertpapierArt} Objekten in der Datenbank.
     *
     * @param wertpapierArten Liste von {@link WertpapierArt} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<WertpapierArt> wertpapierArten) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (WertpapierArt e : wertpapierArten) {
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
     * Speichert ein {@link WertpapierArt} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param wertpapierArt {@link WertpapierArt} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(WertpapierArt wertpapierArt) {
        save(List.of(wertpapierArt));
    }

    /**
     * Löscht eine Liste von {@link WertpapierArt} Objekten aus der Datenbank, sofern vorhanden.
     * @param wertpapierArt Liste von {@link WertpapierArt} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(List<WertpapierArt> wertpapierArt) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (WertpapierArt e : wertpapierArt) {
                session.delete(e);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Löscht ein {@link WertpapierArt} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     * @param wertpapierArt zu löschende {@link WertpapierArt}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(WertpapierArt wertpapierArt) {
        delete(List.of(wertpapierArt));
    }

}
