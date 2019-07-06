package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
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
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Unternehmen Unternehmens} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class UnternehmenRepository implements CrudRepository<Unternehmen> {

    private static UnternehmenRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private UnternehmenRepository() {
    }

    /**
     * Gibt Instanz des {@link UnternehmenRepository UnternehemensRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link UnternehmenRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static UnternehmenRepository getInstanz() {
        if (UnternehmenRepository.instanz == null) {
            UnternehmenRepository.instanz = new UnternehmenRepository();
        }
        return instanz;
    }


    /**
     * Speichert ein {@link Unternehmen Unternehmens} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param unternehmen {@link Unternehmen} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Unternehmen unternehmen) {
        save(List.of(unternehmen));
    }

    /**
     * Speichert eine Liste von {@link Unternehmen Unternehmens} Objekten in der Datenbank.
     *
     * @param unternehmen Liste von {@link Unternehmen Unternehemns} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Unternehmen> unternehmen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Unternehmen u : unternehmen) {
                tx = session.beginTransaction();
                session.saveOrUpdate(u);
                tx.commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Gibt die Anzahl an {@link Unternehmen} Objekten in der Datenbank zurück.
     *
     * @return Anzahl an {@link Unternehmen}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Löscht ein {@link Unternehmen} Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     * @param unternehmen zu löschende {@link Unternehmen}.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(Unternehmen unternehmen) {
        delete(List.of(unternehmen));
    }

    /**
     * Löscht eine Liste von {@link Unternehmen Unternehmens} Objekten aus der Datenbank, sofern vorhanden.
     * @param unternehmen Liste von {@link Unternehmen Unternehmens} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(List<Unternehmen> unternehmen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Unternehmen u : unternehmen) {
                session.delete(u);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Frägt das Vorhandensein eines {@link Unternehmen Unternehmens} Objekts in der Datenbank ab.
     * @param id Id des abzufragenden {@link Unternehmen Unternehmens}
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    /**
     * Abfrage eines {@link Unternehmen Unternehmens} Objekts anhand der Id der {@link Unternehmen} in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id der zu findenden {@link Unternehmen}
     * @return Optional ist ein Container für {@link Unternehmen}, um vereinfacht das Vorhandensein des {@link Unternehmen Unternehmens} zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public Optional<Unternehmen> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen where id = :id"; //Einschränkung auf Spiel nicht notwendig, da UnternehmenID spielübergreifend eindeutig ist
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Unternehmen unternehmen = (Unternehmen) query.uniqueResult();
            if (unternehmen != null)
                return Optional.of(unternehmen);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    /**
     * Abfrage aller {@link Unternehmen} Objekte in der Datenbank nach Unternehmensart.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Unternehmen} fremder Spiele preisgegeben werden.
     * Die Unternehmensart ist in der Klasse {@link Unternehmen} dokumentiert.
     * @param unternehmenArt Art des {@link Unternehmen Unternehmens} z. B. {@code 1}
     * @return Liste mit {@link Unternehmen Unternehmens} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    public List<Unternehmen> findByUnternehmenArt(int unternehmenArt) {
        Transaction tx = null;
        ArrayList<Unternehmen> unternehmen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen where unternehmenArt =: unternehmenArt AND spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("unternehmenArt", unternehmenArt);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                unternehmen.add((Unternehmen) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return unternehmen;
    }

    /**
     * Abfrage aller {@link Unternehmen} Objekte in der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Unternehmen} fremder Spiele preisgegeben werden.
     * Die Ergebnismenge kann dabei Planspielunternehmen als auch Dummy-Unternehmen für die Emission des ETFs o. Ä.
     * enthalten.
     * @return Liste mit {@link Unternehmen Unternehmens} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public List<Unternehmen> findAll() {
        Transaction tx = null;
        ArrayList<Unternehmen> unternehmen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen WHERE spiel =: spiel";
            Query query = session.createQuery(queryString);
            query.setParameter("spiel", AktuelleSpieldaten.getInstanz().getSpiel());
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                unternehmen.add((Unternehmen) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return unternehmen;
    }

    /**
     * Abfrage aller {@link Unternehmen} Objekte in der Datenbank.
     * Es findet dabei eine Einschränkung auf das aktuelle {@link Spiel} statt,
     * sodass keine Daten von {@link Unternehmen} fremder Spiele preisgegeben werden.
     * Die Ergebnismenge enthält dabei nur {@link Unternehmen} mit der {@code UnternehmenArt = UNTERNEHMEN_TEILNEHMER}
     * und damit keine Dummy-Unternehmen für beispielsweise die Emission von ETFs.
     * @return Liste mit {@link Unternehmen Unternehmens} Objekten; gegebenenfalls leer.
     * @author Christian Fix, Markus Bilz
     */
    public List<Unternehmen> findAllPlanspielUnternehmen() {
        return findAll().stream().filter(u -> u.getUnternehmenArt() == Unternehmen.UNTERNEHMEN_TEILNEHMER).collect(Collectors.toList());
    }
}
