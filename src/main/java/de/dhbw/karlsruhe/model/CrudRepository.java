package de.dhbw.karlsruhe.model;

import java.util.List;
import java.util.Optional;

/**
 * Gemeinsames, standardisiertes Interface zur Implementierung durch alle Repositories
 *
 * @param <T> Objekt des PoJos
 * @author Chrisitian Fix, Markus Bilz
 */
public interface CrudRepository<T> {
    /**
     * Speichert ein Objekt in der Datenbank.
     * Implementierung des Bequemlichkeitsmusters.
     *
     * @param entity Objekt zur Speicherung
     * @author Christian Fix, Markus Bilz
     */
    void save(T entity);

    /**
     * Speichert eine Liste von Objekten in der Datenbank.
     *
     * @param entities Liste von Objekten zur Speicherung.
     * @author Christian Fix, Markus Bilz
     */
    void save(List<T> entities);

    /**
     * Gibt die Anzahl an Objekten in der Datenbank zurück.
     *
     * @return Anzahl an Objekten.
     * @author Christian Fix, Markus Bilz
     */
    long count();

    /**
     * Löscht ein Objekt aus der Datenbank, sofern vorhanden.
     * Implementierung des Patterns Bequemlichkeitsmethode.
     *
     * @param entity zu löschendes Objekt.
     * @author Christian Fix, Markus Bilz
     */
    void delete(T entity);

    /**
     * Löscht eine Liste von Objekten aus der Datenbank, sofern vorhanden.
     *
     * @param entities Liste von Objekten zur Löschung.
     * @author Christian Fix, Markus Bilz
     */
    void delete(List<T> entities);

    /**
     * Frägt das Vorhandensein eines Objekts in der Datenbank ab.
     *
     * @param id Id des abzufragenden Objekts
     * @return {@code true}, sofern vorhanden; andernfalls {@code false}
     * @author Christian Fix, Markus Bilz
     */
    boolean existsById(long id);

    /**
     * Abfrage eines Objekts anhand der Id des Objekts in der Datenbank.
     * Es handelt sich dabei um eine Variante des Null-Objekt-Patterns.
     * Dadurch können Prüfungen auf {@code null}-Werte vereinfacht werden.
     *
     * @param id Id des zu findenden Objekts
     * @return Optional ist ein Container für Objekte, um vereinfacht das Vorhandensein des Objekts zu prüfen.
     * @author Christian Fix, Markus Bilz
     */
    Optional<T> findById(long id);

    /**
     * Abfrage aller Objekte in der Datenbank.
     *
     * @return Liste mit Objekten; gegebenenfalls leer.
     * @author Christian Fix
     */
    List<T> findAll();
}
