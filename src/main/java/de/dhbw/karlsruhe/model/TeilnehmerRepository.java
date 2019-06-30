package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Teilnehmer} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class TeilnehmerRepository implements CrudRepository<Teilnehmer> {

    private static TeilnehmerRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private TeilnehmerRepository() {
    }

    /**
     * Gibt Instanz des {@link TeilnehmerRepository TeilnehmerRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link TeilnehmerRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static TeilnehmerRepository getInstanz() {
        if (TeilnehmerRepository.instanz == null) {
            TeilnehmerRepository.instanz = new TeilnehmerRepository();
        }
        return instanz;
    }


    /**
     * Methode zur Abfrage von Teilnehmer aus Datenbank. Hierbei werden Transaktionen zur Abfrage benutzt.
     * Ist eine Abfrage nicht vollständig möglich, erfolgt ein Rollback.
     * Mögliche Testdaten umfassen benutzername = TheoTester und Passwort = Anika
     *
     * @param benutzername Benutzername des Teilnehmers
     * @param passwort     Passwort des Teilnehmers
     * @return Teilnehmer oder null
     */
    public static Teilnehmer getTeilnehmerByBenutzernameAndPasswort(String benutzername, String passwort) {
        Transaction tx = null;
        Teilnehmer teilnehmer = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer WHERE benutzername =:benutzername AND passwort =:passwort AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("passwort", passwort);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            teilnehmer = (Teilnehmer) query.uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return teilnehmer;
    }

    /**
     * Speichert ein {@link Teilnehmer} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param teilnehmer {@link Teilnehmer} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Teilnehmer teilnehmer) {
        save(List.of(teilnehmer));
    }

    /**
     * Speichert eine Liste von {@link Teilnehmer} Objekten in der Datenbank.
     *
     * @param teilnehmer Liste von {@link Teilnehmer} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Teilnehmer> teilnehmer) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Teilnehmer t : teilnehmer) {
                tx = session.beginTransaction();
                session.saveOrUpdate(t);
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
        return findAll().size();
    }

    /**
     * Implementierung des Patterns Bequemlichkeits Methode.
     *
     * @param teilnehmer Teilnehmer zur Löschung.
     */
    @Override
    public void delete(Teilnehmer teilnehmer) {
        delete(List.of(teilnehmer));
    }

    @Override
    public void delete(List<Teilnehmer> teilnehmer) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Teilnehmer t : teilnehmer) {
                session.delete(t);
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
        return findById(id).isPresent();
    }

    @Override
    public Optional<Teilnehmer> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Teilnehmer teilnehmer = (Teilnehmer) query.uniqueResult();
            if (teilnehmer != null)
                return Optional.of(teilnehmer);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Methode zur Abfrage von Teilnehmer aus Datenbank. Hierbei werden Transaktionen zur Abfrage benutzt.
     * Ist eine Abfrage nicht vollständig möglich, erfolgt ein Rollback.
     * Mögliche Testdaten umfassen benutzername = TheoTester und Passwort = Anika
     *
     * @param benutzername Benutzername des Teilnehmers
     * @param passwort     Passwort des Teilnehmers
     * @return Teilnehmer oder null
     */
    public Optional<Teilnehmer> findByBenutzernameAndPasswort(String benutzername, String passwort) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer WHERE benutzername =:benutzername AND passwort =:passwort AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("passwort", passwort);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            Teilnehmer teilnehmer = (Teilnehmer) query.uniqueResult();
            if (teilnehmer != null)
                return Optional.of(teilnehmer);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    public Optional<Teilnehmer> findByBenutzername(String benutzername) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer WHERE benutzername =:benutzername AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            Teilnehmer teilnehmer = (Teilnehmer) query.uniqueResult();
            if (teilnehmer != null)
                return Optional.of(teilnehmer);

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /***
     * Gibt eine Liste aller Teilnehmer (mit Seminarleiter!) aus der Datenbank zurück.
     * @return Liste aller Teilnehmer
     */
    @Override
    public List<Teilnehmer> findAll() {
        Transaction tx = null;
        List<Teilnehmer> alleTeilnehmer = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Teilnehmer where rolle_id = :rolle_id AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("rolle_id", Rolle.ROLLE_TEILNEHMER);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
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

    public List<Teilnehmer> findAllTeilnehmerbyUnternehmen(long unternehmensId) {
        return findAll().stream().filter(t -> t.getUnternehmen().getId() == unternehmensId).collect(Collectors.toList());
    }
}
