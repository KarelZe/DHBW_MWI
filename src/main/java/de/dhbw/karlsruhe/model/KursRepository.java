package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.jpa.Kurs;

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
     * @return
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
        throw new UnsupportedOperationException();
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

    }

    @Override
    public boolean existsById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Kurs> findById(long id) {
        throw new UnsupportedOperationException();
    }

    public List<Kurs> findByPeriodenId(long periodenId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Kurs> findAll() {
        throw new UnsupportedOperationException();
    }
}
