package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.JPA.Unternehmen;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnternehmenRepository implements CrudRepository<Unternehmen> {

    private static UnternehmenRepository instanz;

    // TODO: Überlegen, ob als ENUM? https://dzone.com/articles/java-singletons-using-enum
    private UnternehmenRepository() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return
     */
    public static UnternehmenRepository getInstanz() {
        if (UnternehmenRepository.instanz == null) {
            UnternehmenRepository.instanz = new UnternehmenRepository();
        }
        return instanz;
    }


    /***
     * Speichert ein Unternehmensobjekt in der Datenbank im Rahmen einer Transaktion.
     * @param unternehmen Unternehmensobjekt
     */
    @Override
    public void save(Unternehmen unternehmen) {
        save(List.of(unternehmen));
    }

    /***
     * Speichert eine Liste von Unternehmensobjekten in der Datenbank im Rahmen einer Transaktion.
     * @param unternehmen Liste von Unternehmen
     */
    @Override
    public void save(List<Unternehmen> unternehmen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Unternehmen u : unternehmen) {
                session.saveOrUpdate(u);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Unternehmen unternehmen) {
        delete(List.of(unternehmen));
    }

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

    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<Unternehmen> findById(long id) {
        Transaction tx = null;
        Optional<Unternehmen> optional = Optional.empty();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Unternehmen unternehmen = (Unternehmen) query.uniqueResult();
            return Optional.of(unternehmen);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return optional;
    }

    @Override
    public List<Unternehmen> findAll() {
        Transaction tx = null;
        ArrayList<Unternehmen> unternehmen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen";
            Query query = session.createQuery(queryString);
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

    public List<Unternehmen> findAllSpielbar() {
        Transaction tx = null;
        ArrayList<Unternehmen> unternehmen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Unternehmen where ist_spielbar = :ist_spielbar";
            Query query = session.createQuery(queryString);
            query.setParameter("ist_spielbar", Unternehmen.UNTERNEHMEN_TEILNEHMER);
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
}
