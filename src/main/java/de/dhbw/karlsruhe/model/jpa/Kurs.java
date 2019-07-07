package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

/**
 * <p>
 * POJO Klasse für die Speicherung eines {@code Kurses}.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * Die Speicherung in der Datenbank erfolgt in der anderslautenden Tabelle {@code Periode_Wertpapier}.
 * </p>
 * 
 * <p>
 * Grundsätzlich verfügt jedes {@link Wertpapier} je {@link Periode} über einen eindeutigen Kurs unabhängig der
 * Art des Wertpapiers. Für Floating Rate Notes wird zusätzlich der Spread gespeichert.
 * </p>
 * 
 * <p>
 * Ein {@link Kurs} repräsentiert in der Anwendung den Marktwert einer Finanzanlage. Beispielsweise entspricht er
 * bei ETFs dem Indexstand oder bei Aktien dem Aktienkurs des Planspielunternehmens.
 * </p>
 * 
 * <p>
 * Die Erfassung und Berechnung von Kursen ist in {@link de.dhbw.karlsruhe.buchung.BuchungsFactory}
 * nebst zugehörigem Fachkonzept geregelt.
 * </p>
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

    private double kurs;

    // Double, da optional für einzelne Wertpapiere wie Aktien. Siehe https://stackoverflow.com/questions/3154582/why-do-i-get-a-null-value-was-assigned-to-a-property-of-primitive-type-setter-o/13906763
    private Double spread;
    private Double manuellerKurs;

    public Kurs(Periode periode, Wertpapier wertpapier) {
        this.periode = periode;
        this.wertpapier = wertpapier;
        this.kurs = 100;
    }

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor">https://docs.jboss.org/</a>.
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

    /**
     * Gibt den Kurs eines Wertpapiers zurück. Sollte der Kurs zuvor überschrieben worden sein (z. B. bei Insolvenz),
     * dann wird der {@code manuellerKurs} zurückgegeben, andernfalls der {@link Kurs}.
     *
     * @return Kurs
     * @author Christian Fix, Markus Bilz
     */
    public double getKurs() {
        return this.manuellerKurs == null ? this.kurs : this.manuellerKurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public Double getManuellerKurs() {
        return manuellerKurs;
    }

    public void setManuellerKurs(Double manuellerKurs) {
        this.manuellerKurs = manuellerKurs;
    }

    @Override
    public String toString() {
        return "Kurs{" +
                "id=" + id +
                ", periode=" + periode +
                ", wertpapier=" + wertpapier +
                ", kurs=" + kurs +
                ", spread=" + spread +
                '}';
    }
}
