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

public class SpielRepository implements CrudRepository<Spiel> {

    private static SpielRepository instanz;

    // TODO: Überlegen, ob als ENUM? https://dzone.com/articles/java-singletons-using-enum
    private SpielRepository() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return instanz von Spiel
     */
    public static SpielRepository getInstanz() {
        if (SpielRepository.instanz == null) {
            SpielRepository.instanz = new SpielRepository();
        }
        return instanz;
    }

    public static void deleteSpiel (Spiel spiel) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(spiel);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    public Spiel getAktivesSpiel() {
        Transaction tx = null;
        Spiel spiel = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Spiel WHERE ist_aktiv =:ist_aktiv";
            Query query = session.createQuery(queryString);
            query.setParameter("ist_aktiv", Spiel.SPIEL_AKTIV);
            tx.commit();
            spiel = (Spiel) query.uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return spiel;
    }

    @Override
    public void save(Spiel spiel) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
                session.saveOrUpdate(spiel);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    @Override
    public void save(List<Spiel> spiele) {
        for(Spiel spiel : spiele) {
            save(spiel);
        }
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Spiel spiel) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
                session.delete(spiel);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    @Override
    public void delete(List<Spiel> spiele) {
        for(Spiel spiel : spiele) {
            delete(spiele);
        }
    }

    @Override
    public boolean existsById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Spiel> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Spiel where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Spiel spiel = (Spiel) query.uniqueResult();
            if (spiel != null)
                return Optional.of(spiel);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<Spiel> findAll() {
        Transaction tx = null;
        List<Spiel> alleSpiele = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Spiel";
            Query query = session.createQuery(queryString);
            tx.commit();
            for (final Object o : query.list()) {
                alleSpiele.add((Spiel) o);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return alleSpiele;
    }
}
