package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.helper.HibernateHelper;
import de.dhbw.karlsruhe.model.jpa.Rolle;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolleRepository implements CrudRepository<Rolle> {

    private static RolleRepository instanz;

    private RolleRepository() {
    }

    /**
     * Methode gibt Instanz des Modells zurück.
     * Implementierung als Singleton Pattern.
     *
     * @return instanz von RolleRepository
     */
    public static RolleRepository getInstanz() {
        if (RolleRepository.instanz == null) {
            RolleRepository.instanz = new RolleRepository();
        }
        return instanz;
    }

    /**
     * Persistiert ein Rollenobjekt in der Datenbank im Rahmen einer Transaktion.
     *
     * @param rolle Rolle
     * @author Christian Fix
     */
    @Override
    public void save(Rolle rolle) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(rolle);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
    }

    /**
     * Persistiert eine Liste von Rollenobjekten in der Datenbank.
     *
     * @param rollen Liste von Rollen
     * @author Christian Fix
     */
    @Override
    public void save(List<Rolle> rollen) {
        for(Rolle rolle : rollen) {
            save(rolle);
        }
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public void delete(Rolle rolle) {
        delete(List.of(rolle));
    }

    @Override
    public void delete(List<Rolle> rollen) {
        Transaction tx = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Rolle r : rollen) {
                session.delete(r);
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

    /**
     * Diese Methode stellt ein Rollenobjekt anhand der id der Rolle.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf Null-Werte vereinfaht werden.
     *
     * @param id ID der Rolle.
     * @return Optional ist ein Container für ROlle, um vereinfacht das Vorhandensein der Rolle zu prüfen.
     * @author Christian Fix
     */
    public Optional<Rolle> findById(long id) {
        Transaction tx = null;
        Rolle rolle = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Rolle WHERE id =: id";
            Query query = session.createQuery(queryString);
            query.setParameter("id", id);
            tx.commit();
            rolle = (Rolle) query.uniqueResult();
            if(rolle != null) {
                return Optional.of(rolle);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return Optional.empty();
    }

    @Override
    public List<Rolle> findAll() {
        Transaction tx = null;
        ArrayList<Rolle> rollen = new ArrayList<>();
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String queryString = "from Rolle";
            Query query = session.createQuery(queryString);
            tx.commit();
            // Typen-Sichere Konvertierung. Siehe z. B. https://stackoverflow.com/a/15913247.
            for (final Object o : query.list()) {
                rollen.add((Rolle) o);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return rollen;
    }

}
