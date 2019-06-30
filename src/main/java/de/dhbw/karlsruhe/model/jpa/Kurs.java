package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

/**
 * POJO Klasse für die Speicherung eines Kurses.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * Die Speicherung in der Datenbank erfolgt in der anderslautenden Tabelle {@code Periode_Wertpapier}.
 * Grundsätzlich verfügt jedes {@code Wertpapier} je {@code Periode} über einen eindeutigen Kurs unabhängig der
 * Art des Wertpapiers. Für Floating Rate Notes wird zusätzlich der Spread gespeichert.
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
@Table(name = "Periode_Wertpapier")
public class Kurs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "periode_id")
    private Periode periode;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    private double kursValue;

    // Double, da null. Siehe https://stackoverflow.com/questions/3154582/why-do-i-get-a-null-value-was-assigned-to-a-property-of-primitive-type-setter-o/13906763
    private Double spread;

    public Kurs(Periode periode, Wertpapier wertpapier) {
        this.periode = periode;
        this.wertpapier = wertpapier;
    }

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org">https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor</a>.
     */
    public Kurs() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public double getKursValue() {
        return kursValue;
    }

    public void setKursValue(double kurs) {
        this.kursValue = kurs;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    @Override
    public String toString() {
        return "Kurs{" +
                "id=" + id +
                ", periode=" + periode +
                ", wertpapier=" + wertpapier +
                ", kurs=" + kursValue +
                ", spread=" + spread +
                '}';
    }
}
