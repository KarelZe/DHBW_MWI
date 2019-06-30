package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * POJO Klasse für die Speicherung der Rollen.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * Die Speicherung der Rollen in der Datenbank erfolgt in der eigenständigen Tabelle, da sqlite keine Enums unterstützt.
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
public class Rolle {
    public static final long ROLLE_TEILNEHMER = 1;
    public static final long ROLLE_SPIELLEITER = 2;
    @Id
    private long id;
    private String name;

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org">https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor</a>.
     */
    public Rolle() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Rolle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
