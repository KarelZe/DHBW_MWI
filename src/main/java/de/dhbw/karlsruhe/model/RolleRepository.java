package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Rolle} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Markus Bilz, Christian Fix
 */
public class RolleRepository implements CrudRepository<Rolle> {

    private static RolleRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     * @author Christian Fix, Markus Bilz
     */
    private RolleRepository() {
    }

    /**
     * Gibt Instanz des {@link RolleRepository RolleRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link RolleRepository}
     * @author Christian Fix, Markus Bilz
     */
    public static RolleRepository getInstanz() {
        if (RolleRepository.instanz == null) {
            RolleRepository.instanz = new RolleRepository();
        }
        return instanz;
    }

    /**
     * Speichert ein {@link Rolle} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param rolle {@link Rolle} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Rolle rolle) {
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

    /**
     * Speichert eine Liste von {@link Rolle} Objekten in der Datenbank.
     *
     * @param rollen Liste von {@link Rolle} Objekten zur Speicherung.
     * @author Christian Fix
     */
    @Override
    public void save(List<Rolle> rollen) {
        for(Rolle rolle : rollen) {
            save(rolle);
        }
    }

    /**
     * Gibt die Anzahl an {@link Rolle} Objekten in der Datenbank zurück.
     * @return Anzahl an {@link Rolle Rollen}.
     * @author Christian Fix
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Löscht ein {@link Rolle} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     * @param rolle zu löschende {@link Rolle}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(Rolle rolle) {
        delete(List.of(rolle));
    }

    /**
     * Löscht eine Liste von {@link Rolle} Objekten aus der Datenbank, sofern vorhanden.
     * @param rollen Liste von {@link Rolle} Objekten zur Löschung.
     * @author Christian Fix
     */
    @Override
    public void delete(List<Rolle> rollen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Rolle r : rollen) {
                session.delete(r);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Frägt das Vorhandensein eines {@link Rolle} Objekts in der Datenbank ab.
     * @param id Id der abzufragenden {@link Rolle}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix
     */
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    /**
     * Abfrage eines {@link Rolle} Objekts anhand der Id der {@link Rolle} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id der zu findenden {@link Rolle}
     * @return Optional ist ein Container für {@link Rolle}, um vereinfacht das Vorhandensein der {@link Rolle} zu prüfen.
     * @author Christian Fix
     */
    public Optional<Rolle> findById(long id) {
        Transaction tx = null;
        Rolle rolle = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Rolle WHERE id =: id"; //Einschränkung auf Spiel nicht notwendig, da RolleID spielübergreifend eindeutig ist
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            rolle = (Rolle) query.uniqueResult();
            if(rolle != null) {
                return Optional.of(rolle);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage aller {@link Rolle Rollen} Objekte in der Datenbank.
     * @return Liste mit {@link Rolle} Objekten; gegebenenfalls leer.
     * @author Christian Fix
     */
    @Override
    public List<Rolle> findAll() {
        Transaction tx = null;
        ArrayList<Rolle> rollen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Rolle"; //Einschränkung auf Spiel nicht notwendig, da Rolle unabhängig vom Spiel ist
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                rollen.add((Rolle) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return rollen;
    }

}
