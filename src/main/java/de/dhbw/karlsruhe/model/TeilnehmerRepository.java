package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Spiel;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
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

    /**
     * Gibt die Anzahl an {@link Teilnehmer} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link Teilnehmer Teilnehmern}.
     * @author Markus Bilz
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Löscht ein {@link Teilnehmer} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     * @param teilnehmer zu löschender {@link Teilnehmer}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(Teilnehmer teilnehmer) {
        delete(List.of(teilnehmer));
    }

    /**
     * Löscht eine Liste von {@link Teilnehmer} Objekten aus der Datenbank, sofern vorhanden.
     *
     * @param teilnehmer Liste von {@link Teilnehmer} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
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

    /**
     * Frägt das Vorhandensein eines {@link Teilnehmer} Objekts in der Datenbank ab.
     * @param id Id der abzufragenden {@link Teilnehmer}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    /**
     * Abfrage eines {@link Teilnehmer} Objekts anhand der Id der {@link Teilnehmer} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id des zu findenden {@link Teilnehmer Teilnehmers}
     * @return Optional ist ein Container für {@link Teilnehmer}, um vereinfacht das Vorhandensein der {@link Teilnehmer} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
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
     * Abfrage eines {@link Teilnehmer Teilnehmers} aus  der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     * Mögliche Testdaten umfassen {@code benutzername = admin} und {@code passwort = master}
     *
     * @param benutzername Benuztername des zu findenden {@link Teilnehmer Teilnehmers}
     * @param passwort     Passwort des zu findenden {@link Teilnehmer Teilnehmers}
     * @return Optional ist ein Container für {@link Teilnehmer}, um vereinfacht das Vorhandensein der {@link Teilnehmer} zu prüfen.
     * @author Christian Fix, Markus Bilz
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

    /**
     * Abfrage eines {@link Teilnehmer Teilnehmers} aus der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Teilnehmer Teilnehmern} fremder Spiele preisgegeben werden.
     *
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * Mögliche Testdaten umfassen {@code benutzername = admin}.
     *
     * @param benutzername Benuztername des zu findenden {@link Teilnehmer Teilnehmers}
     * @return Optional ist ein Container für {@link Teilnehmer}, um vereinfacht das Vorhandensein der {@link Teilnehmer} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
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

    /**
     * Abfrage aller {@link Teilnehmer} Objekte in der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Teilnehmer Teilnehmern} fremder Spiele preisgegeben werden.
     * Es findet dabei eine Einschränkung auf {@link Teilnehmer} mit der {@code Rolle = ROLLE_TEILNEHMER} statt.
     * @return Liste mit {@link Teilnehmer} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
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

    /**
     * Abfrage aller {@link Teilnehmer} Objekte eines {@link Unternehmen Unternehmens} in der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Teilnehmer Teilnehmern} fremder Spiele preisgegeben werden.
     * Es findet dabei eine Einschränkung auf {@link Teilnehmer} mit der {@code Rolle = ROLLE_TEILNEHMER} statt.
     * @param unternehmensId Id des {@link Unternehmen Unternehmens}
     * @return Liste mit {@link Teilnehmer} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    public List<Teilnehmer> findAllTeilnehmerbyUnternehmen(long unternehmensId) {
        return findAll().stream().filter(t -> t.getUnternehmen().getId() == unternehmensId).collect(Collectors.toList());
    }
}
