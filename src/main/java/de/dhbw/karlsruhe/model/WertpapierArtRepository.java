package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WertpapierArtRepository implements CrudRepository<WertpapierArt> {

    private static WertpapierArtRepository instanz;

    // TODO: Überlegen, ob als ENUM? https://dzone.com/articles/java-singletons-using-enum
    private WertpapierArtRepository() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return Instanz von WertpapierArtRepository
     */
    public static WertpapierArtRepository getInstanz() {
        if (WertpapierArtRepository.instanz == null) {
            WertpapierArtRepository.instanz = new WertpapierArtRepository();
        }
        return instanz;
    }

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
     * Diese Methode stellt ein WertpapierArt-Objekt anhand der id der WertpapierArt.
     * Es handelt sich dabei um eine Variante des NUll-Objekt-Patterns.
     * Dadurch können Prüfungen auf Null-Werte vereinfaht werden.
     *
     * @param id ID des Wertpapiers.
     * @return Optional ist ein Container für WertpapierArten, um vereinfacht das Vorhandensein einer WertpapierArt zu prüfen.
     */
    @Override
    public Optional<WertpapierArt> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from WertpapierArt where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            WertpapierArt wertpapierArt = (WertpapierArt) query.uniqueResult();
            if (wertpapierArt != null)
                return Optional.of(wertpapierArt);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<WertpapierArt> findAll() {
        Transaction tx = null;
        ArrayList<WertpapierArt> wertpapierArten = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from WertpapierArt";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                wertpapierArten.add((WertpapierArt) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return wertpapierArten;
    }


    // TODO: Wie werden Exceptions hochgegegeben?
    @Override
    public void save(List<WertpapierArt> wertpapierArten) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (WertpapierArt e : wertpapierArten) {
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
     * @param wertpapierArt WertpapierArtobjekt zur Speicherung.
     */
    @Override
    public void save(WertpapierArt wertpapierArt) {
        save(List.of(wertpapierArt));
    }

    @Override
    public void delete(List<WertpapierArt> wertpapierArt) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (WertpapierArt e : wertpapierArt) {
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
     * @param wertpapierArt WertpapierArt zur Löschung.
     */
    @Override
    public void delete(WertpapierArt wertpapierArt) {
        delete(List.of(wertpapierArt));
    }

}
