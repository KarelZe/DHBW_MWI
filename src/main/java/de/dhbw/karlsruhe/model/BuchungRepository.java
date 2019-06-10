
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

public class BuchungRepository implements CrudRepository<Buchung> {
    private static BuchungRepository instanz;

    // TODO: Überlegen, ob als ENUM? https://dzone.com/articles/java-singletons-using-enum
    private BuchungRepository() {
    }


    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return instanz des repository
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


    // TODO: Wie werden Exceptions hochgegegeben?

    @Override
    public void save(List<Buchung> buchung) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Buchung e : buchung) {
                session.saveOrUpdate(e);
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
     * @param buchung Wertpapierobjekt zur Speicherung.
     */

    @Override
    public void save(Buchung buchung) {
        save(List.of(buchung));
    }

    @Override
    public void delete(List<Buchung> buchung) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Buchung e : buchung) {
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

    public Buchung findLastBuchungFromTeilnehmerByTeilnehmerId(long teilnehmerId) {
        List<Buchung> buchungenVonTeilnehmer = findByTeilnehmerId(teilnehmerId);
        return buchungenVonTeilnehmer.stream().reduce((a, b) -> b).get(); //siehe https://stackoverflow.com/questions/21426843/get-last-element-of-stream-list-in-a-one-liner

    }
}

