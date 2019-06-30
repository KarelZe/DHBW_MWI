
package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt die Verbindung zur Datenbank zur Speicherung von {@link Buchung} Objekten her.
 * Implementiert als Repository Pattern (Fowler) und Singleton Pattern (GOF).
 *
 * @author Christian Fix, Markus Bilz
 */
public class BuchungRepository implements CrudRepository<Buchung> {
    private static BuchungRepository instanz;

    /**
     * Privater Konstruktor.
     * Implementierung des Singleton Patterns (GOF).
     *
     * @author Markus Bilz, Christian Fix
     */
    private BuchungRepository() {
    }


    /**
     * Gibt Instanz des {@link BuchungRepository BuchungRepositories} zurück.
     * Implementierung als Singleton Pattern (GOF).
     * @return instanz von {@link BuchungRepository}
     * @author Markus Bilz, Christian Fix
     */
    public static BuchungRepository getInstanz() {
        if (BuchungRepository.instanz == null) {
            BuchungRepository.instanz = new BuchungRepository();
        }
        return instanz;
    }

    // Implementierung Interface


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


    /**
     * Diese Methode stellt ein Wertpapierobjekt anhand der id des Wertpapiers.
     * Es handelt sich dabei um eine Variante des NUll-Objekt-Patterns.
     * Dadurch können Prüfungen auf Null-Werte vereinfaht werden.
     *
     * @param id ID des Wertpapiers.
     * @return Optional ist ein Container für Wertpapier, um vereinfacht das Vorhandensein des Wertpapiers zu prüfen.
     */

    @Override
    public Optional<Buchung> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Buchung where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Buchung buchung = (Buchung) query.uniqueResult();
            if (buchung != null)
                return Optional.of(buchung);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<Buchung> findAll() {
        Transaction tx = null;
        ArrayList<Buchung> buchungen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Buchung";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                buchungen.add((Buchung) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return buchungen;
    }

    /**
     * Speichert eine Liste von {@link Buchung} Objekten in der Datenbank.
     *
     * @param buchung Liste von {@link Buchung} Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(List<Buchung> buchung) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            for (Buchung e : buchung) {
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
     * Speichert ein {@link Buchung Buchungs} Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     * @param buchung {@link Buchung} zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void save(Buchung buchung) {
        save(List.of(buchung));
    }

    /**
     * Löscht eine Liste von {@link Buchung Buchungs} Objekten aus der Datenbank, sofern vorhanden.
     * @param buchungen Liste von {@link Buchung Buchungs} Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    @Override
    public void delete(List<Buchung> buchungen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Buchung e : buchungen) {
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
     * Implementierung des Patterns Bequemlichkeits Methode.
     *
     * @param buchung Wertpapier zur Löschung.
     */

    @Override
    public void delete(Buchung buchung) {
        delete(List.of(buchung));
    }

    /**
     * Abfrage von {@link Buchung Buchungs} Objekten anhand der Id des {@link de.dhbw.karlsruhe.model.jpa.Teilnehmer Teilnehmers} in der Datenbank.
     * @param teilnehmerId Id des Teilnehmers
     * @return Buchungen oder leere Liste
     * @deprecated ersetzt durch PortfolioFassade {@link de.dhbw.karlsruhe.model.fassade.PortfolioFassade}.
     * Die PortfolioFassade bietet einen einfacheren Zugriff auf Salden des Teilnehmers, weshalb die Fassade bevorzugt
     * zu verwenden ist, sofern nicht zwingend Buchungen benötigt werden.
     */
    @Deprecated
    public List<Buchung> findByTeilnehmerId(long teilnehmerId) {
        Transaction tx = null;
        ArrayList<Buchung> buchungen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Buchung where teilnehmer_id = :teilnehmer_id";
            Query query = session.createQuery(queryString);
            query.setParameter("teilnehmer_id", teilnehmerId);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                buchungen.add((Buchung) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return buchungen;
    }
}

