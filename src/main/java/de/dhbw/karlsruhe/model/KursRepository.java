package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KursRepository implements CrudRepository<Kurs> {

    private static KursRepository instanz;

    // TODO: Überlegen, ob als ENUM? https://dzone.com/articles/java-singletons-using-enum
    private KursRepository() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return Instanz von Kurs
     */
    public static KursRepository getInstanz() {
        if (KursRepository.instanz == null) {
            KursRepository.instanz = new KursRepository();
        }
        return instanz;
    }


    @Override
    public void save(Kurs kurs) {
        save(List.of(kurs));
    }

    @Override
    public void save(List<Kurs> kurse) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Kurs k : kurse) {
                session.saveOrUpdate(k);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Kurs kurs) {
        delete(List.of(kurs));
    }

    @Override
    public void delete(List<Kurs> kurse) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Kurs k : kurse) {
                session.delete(k);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Kurs> findById(long id) {
        throw new UnsupportedOperationException();
    }

    public List<Kurs> findByPeriodenId(long periodeId) {
        Transaction tx = null;
        ArrayList<Kurs> kurse = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Kurs where periode_id = :periode_id";
            Query query = session.createQuery(queryString);
            query.setParameter("periode_id", periodeId);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) kurse.add((Kurs) o);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return kurse;
    }

    @Override
    public List<Kurs> findAll() {
        throw new UnsupportedOperationException();
    }
}
