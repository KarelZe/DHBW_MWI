package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Benutzer;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Spiel;
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
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Benutzer} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class BenutzerRepository implements CrudRepository<Benutzer> {

    private static BenutzerRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private BenutzerRepository() {
    }

    /**
     * Gibt Instanz des {@link BenutzerRepository BenutzerRepository} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link BenutzerRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static BenutzerRepository getInstanz() {
        if (BenutzerRepository.instanz == null) {
            BenutzerRepository.instanz = new BenutzerRepository();
        }
        return instanz;
    }

    /**
     * Speichert ein {@link Benutzer} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param benutzer {@link Benutzer} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Benutzer benutzer) {
        save(List.of(benutzer));
    }

    /**
     * Speichert eine Liste von {@link Benutzer} Objekten in der Datenbank.
     *
     * @param benutzer Liste von {@link Benutzer} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Benutzer> benutzer) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Benutzer t : benutzer) {
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
     * Gibt die Anzahl an {@link Benutzer} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link Benutzer Benutzern}.
     * @author Markus Bilz
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Löscht ein {@link Benutzer} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     * @param benutzer zu löschender {@link Benutzer}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(Benutzer benutzer) {
        delete(List.of(benutzer));
    }

    /**
     * Löscht eine Liste von {@link Benutzer} Objekten aus der Datenbank, sofern vorhanden.
     *
     * @param benutzer Liste von {@link Benutzer} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(List<Benutzer> benutzer) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Benutzer t : benutzer) {
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
     * Frägt das Vorhandensein eines {@link Benutzer} Objekts in der Datenbank ab.
     * @param id Id der abzufragenden {@link Benutzer}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    /**
     * Abfrage eines {@link Benutzer} Objekts anhand der Id der {@link Benutzer} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id des zu findenden {@link Benutzer Benutzers}
     * @return Optional ist ein Container für {@link Benutzer}, um vereinfacht das Vorhandensein der {@link Benutzer} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public Optional<Benutzer> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Benutzer where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Benutzer benutzer = (Benutzer) query.uniqueResult();
            if (benutzer != null)
                return Optional.of(benutzer);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage eines {@link Benutzer Benutzers} aus  der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     * Mögliche Testdaten umfassen {@code benutzername = admin} und {@code passwort = master}
     *
     * @param benutzername Benuztername des zu findenden {@link Benutzer Benutzers}
     * @param passwort     Passwort des zu findenden {@link Benutzer Benutzers}
     * @return Optional ist ein Container für {@link Benutzer}, um vereinfacht das Vorhandensein der {@link Benutzer} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    public Optional<Benutzer> findByBenutzernameAndPasswort(String benutzername, String passwort) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Benutzer WHERE benutzername =:benutzername AND passwort =:passwort AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("passwort", passwort);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            Benutzer benutzer = (Benutzer) query.uniqueResult();
            if (benutzer != null)
                return Optional.of(benutzer);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage eines {@link Benutzer Benutzers} aus der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Benutzer Benutzers} fremder Spiele preisgegeben werden.
     *
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * Mögliche Testdaten umfassen {@code benutzername = admin}.
     *
     * @param benutzername Benuztername des zu findenden {@link Benutzer Benutzers}
     * @return Optional ist ein Container für {@link Benutzer}, um vereinfacht das Vorhandensein der {@link Benutzer} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    public Optional<Benutzer> findByBenutzername(String benutzername) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Benutzer WHERE benutzername =:benutzername AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("benutzername", benutzername);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            Benutzer benutzer = (Benutzer) query.uniqueResult();
            if (benutzer != null)
                return Optional.of(benutzer);

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage aller {@link Benutzer} Objekte in der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Benutzer Benutzern} fremder Spiele preisgegeben werden.
     * Es findet dabei eine Einschränkung auf {@link Benutzer} mit der {@code Rolle = ROLLE_TEILNEHMER} statt.
     * @return Liste mit {@link Benutzer} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public List<Benutzer> findAll() {
        Transaction tx = null;
        List<Benutzer> alleBenutzer = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Benutzer where rolle_id = :rolle_id AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("rolle_id", Rolle.ROLLE_TEILNEHMER);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            for (final Object o : query.list()) {
                alleBenutzer.add((Benutzer) o);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return alleBenutzer;
    }

    /**
     * Abfrage aller {@link Benutzer} Objekte eines {@link Unternehmen Unternehmens} in der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Benutzer Benutzern} fremder Spiele preisgegeben werden.
     * Es findet dabei eine Einschränkung auf {@link Benutzer} mit der {@code Rolle = ROLLE_TEILNEHMER} statt.
     * @param unternehmensId Id des {@link Unternehmen Unternehmens}
     * @return Liste mit {@link Benutzer} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    public List<Benutzer> findAllBenutzerByUnternehmen(long unternehmensId) {
        return findAll().stream().filter(t -> t.getUnternehmen().getId() == unternehmensId).collect(Collectors.toList());
    }
}
