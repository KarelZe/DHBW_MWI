package de.dhbw.karlsruhe.model;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {


    void save(T entity);

    void save(List<T> entities);

    long count();

    void delete(T entity);

    void delete(List<T> entities);

    boolean existsById(long id);

    Optional<T> findById(long id);

    List<T> findAll();
}
