package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Wertpapier} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class WertpapierRepository implements CrudRepository<Wertpapier> {

    private static WertpapierRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private WertpapierRepository() {
    }

    /**
     * Gibt Instanz des {@link WertpapierRepository WertpapierRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     *
     * @return instanz von {@link WertpapierRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static WertpapierRepository getInstanz() {
        if (WertpapierRepository.instanz == null) {
            WertpapierRepository.instanz = new WertpapierRepository();
        }
        return instanz;
    }

    /**
     * Gibt die Anzahl an {@link Wertpapier} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link Wertpapier Wertpapieren}.
     * @author Christian Fix, Markus Bilz
     */
    public long count() {
        return findAll().size();
    }

    /**
     * Frägt das Vorhandensein eines {@link Wertpapier} Objekts in der Datenbank ab.
     *
     * @param id Id des abzufragenden {@link Wertpapier Wertpapiers}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }


    /**
     * Abfrage eines {@link Wertpapier} Objekts anhand der Id des {@link Wertpapier Wertpapiers} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id der zu findenden {@link Wertpapier Wertpapiers}
     * @return Optional ist ein Container für {@link Wertpapier Wertpapiere}, um vereinfacht das Vorhandensein des {@link Wertpapier Wertpapiers} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public Optional<Wertpapier> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Wertpapier where id = :id"; //Einschränkung auf Spiel nicht notwendig, da WertpapierID spielübergreifend eindeutig ist
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Wertpapier wertpapier = (Wertpapier) query.uniqueResult();
            if (wertpapier != null)
                return Optional.of(wertpapier);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage aller {@link Wertpapier} Objekte in der Datenbank.
     * Es werden ausschließlich Wertpapiere des aktuell aktiven Spiels zurückgegeben.
     *
     * @return Liste mit {@link Wertpapier} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public List<Wertpapier> findAll() {
        Transaction tx = null;
        ArrayList<Wertpapier> wertpapiere = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Wertpapier where spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                wertpapiere.add((Wertpapier) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return wertpapiere;
    }


    /**
     * Speichert eine Liste von {@link Wertpapier} Objekten in der Datenbank.
     *
     * @param wertpapiere Liste von {@link Wertpapier} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Wertpapier> wertpapiere) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Wertpapier w : wertpapiere) {
                tx = session.beginTransaction();
                session.saveOrUpdate(w);
                tx.commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Speichert ein {@link Wertpapier} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     *
     * @param wertpapier {@link Wertpapier} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Wertpapier wertpapier) {
        save(List.of(wertpapier));
    }

    /**
     * Löscht eine Liste von {@link Wertpapier} Objekten aus der Datenbank, sofern vorhanden.
     *
     * @param wertpapiere Liste von {@link Wertpapier} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(List<Wertpapier> wertpapiere) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Wertpapier e : wertpapiere) {
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
     * Löscht ein {@link Wertpapier} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     *
     * @param wertpapier zu löschendes {@link Wertpapier}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(Wertpapier wertpapier) {
        delete(List.of(wertpapier));
    }

    public Wertpapier findByWertpapierArt(long wertpapierArtId) {
        return findAll().stream().filter(w -> w.getWertpapierArt().getId() == wertpapierArtId).findAny().orElseThrow(NoSuchElementException::new);
    }

}
