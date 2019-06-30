package de.dhbw.karlsruhe.model.jpa;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO Klasse für die Speicherung einer {@code WertpapierArt}.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * Die Speicherung der {@code WertpapierArt} in der Datenbank erfolgt in der eigenständigen Tabelle, da sqlite keine Enums unterstützt.
 * Die Datenbank-Tabelle lautet {@code Wertpapier_Art}.
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
@Table(name = "Wertpapier_Art")
public class WertpapierArt {

    public static final long WERTPAPIER_AKTIE = 1;
    public static final long WERTPAPIER_ANLEIHE = 2;
    public static final long WERTPAPIER_FESTGELD = 3;
    public static final long WERTPAPIER_ETF = 4;
    public static final long WERTPAPIER_STARTKAPITAL = 5;
    public static final String WERTPAPIER_AKTIE_NAME = "Aktie";
    public static final String WERTPAPIER_ANLEIHE_NAME = "Anleihe";
    public static final String WERTPAPIER_FESTGELD_NAME = "Festgeld";
    public static final String WERTPAPIER_ETF_NAME = "ETF";
    public static final String WERTPAPIER_STARTKAPITAL_NAME = "Startkapital";

    private String name;

    @Id
    private long id;

    public WertpapierArt(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org">https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor</a>.
     */
    public WertpapierArt() {

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
        return "WertpapierArt{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WertpapierArt that = (WertpapierArt) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
