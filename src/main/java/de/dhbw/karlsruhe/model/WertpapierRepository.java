package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WertpapierRepository implements CrudRepository<Wertpapier> {

    private static WertpapierRepository instanz;

    // TODO: Überlegen, ob als ENUM? https://dzone.com/articles/java-singletons-using-enum
    private WertpapierRepository() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return
     */
    public static WertpapierRepository getInstanz() {
        if (WertpapierRepository.instanz == null) {
            WertpapierRepository.instanz = new WertpapierRepository();
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
     * Diese Methode stellt ein Wertpapierobjekt anhand der id des Wertpapiers.
     * Es handelt sich dabei um eine Variante des NUll-Objekt-Patterns.
     * Dadurch können Prüfungen auf Null-Werte vereinfaht werden.
     *
     * @param id ID des Wertpapiers.
     * @return Optional ist ein Container für Wertpapier, um vereinfacht das Vorhandensein des Wertpapiers zu prüfen.
     */
    @Override
    public Optional<Wertpapier> findById(long id) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Wertpapier where id = :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            Wertpapier wertpapier = (Wertpapier) query.uniqueResult();
            if (wertpapier != null)
                return Optional.of(wertpapier);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<Wertpapier> findAll() {
        Transaction tx = null;
        ArrayList<Wertpapier> wertpapiere = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Wertpapier";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                wertpapiere.add((Wertpapier) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return wertpapiere;
    }

    /*
    public List<Wertpapier> findAllByArt() {
        Transaction tx = null;
        ArrayList<Wertpapier> wertpapier = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Wertpapier where ist_spielbar = :ist_spielbar";
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
    */
    // TODO: Wie werden Exceptions hochgegegeben?
    @Override
    public void save(List<Wertpapier> wertpapier) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Wertpapier e : wertpapier) {
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
     * @param wertpapier Wertpapierobjekt zur Speicherung.
     */
    @Override
    public void save(Wertpapier wertpapier) {
        save(List.of(wertpapier));
    }

    @Override
    public void delete(List<Wertpapier> wertpapier) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Wertpapier e : wertpapier) {
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
     * @param wertpapier Wertpapier zur Löschung.
     */
    @Override
    public void delete(Wertpapier wertpapier) {
        delete(List.of(wertpapier));
    }

}
