package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Spiel;
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

    /**
     * Gibt die Anzahl an {@link Periode} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link Periode Perioden}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Löscht ein {@link Periode Perioden} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     *
     * @param periode zu löschende {@link Periode}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(Periode periode) {
        delete(List.of(periode));
    }

    /**
     * Löscht eine Liste von {@link Periode} Objekten aus der Datenbank, sofern vorhanden.
     * @param perioden Liste von {@link Periode} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
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

    /**
     * Frägt das Vorhandensein eines {@link Periode} Objekts in der Datenbank ab.
     * @param id Id der abzufragenden {@link Periode}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    /**
     * Abfrage eines {@link Periode} Objekts anhand der Id der {@link Periode} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns (GOF).
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id der zu findenden {@link Periode}
     * @return Optional ist ein Container für eine {@link Periode}, um vereinfacht das Vorhandensein der {@link Periode} zu prüfen.
     * @author Christian Fix, Markus
     */
    @Override
    public Optional<Periode> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Periode where id = :id"; //Einschränkung auf Spiel nicht notwendig, da PeriodeID spielübergreifend eindeutig ist
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

    /**
     * Abfrage aller {@link Periode Perioden} Objekte in der Datenbank.
     * @throws UnsupportedOperationException Exception, da noch nicht implementiert.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public List<Periode> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Abfrage aller {@link Periode Perioden} eines {@link Spiel Spiels}.
     * @param spieleId Id des zugehörigen Spiels
     * @return Liste mit {@link Periode} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
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
