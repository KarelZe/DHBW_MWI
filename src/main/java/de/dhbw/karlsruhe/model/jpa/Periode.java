package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;
import java.util.Set;

/**
 * POJO Klasse für die Speicherung einer Periode.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
public class Periode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "spiel_id")
    private Spiel spiel;

    @OneToMany(mappedBy = "periode", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Kurs> KursSet;

    @Column(name = "ordergebuehr_in_prozent")
    private double ordergebuehr;
    @Column(name = "kapitalmarktzins_in_prozent")
    private double kapitalmarktzinssatz;

    public Periode(Spiel spiel, double ordergebuehr, double kapitalmarktzinssatz) {
        this.spiel = spiel;
        this.ordergebuehr = ordergebuehr;
        this.kapitalmarktzinssatz = kapitalmarktzinssatz;
    }

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org">https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor</a>.
     */
    public Periode() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public double getOrdergebuehr() {
        return ordergebuehr;
    }

    public void setOrdergebuehr(double ordergebuehr) {
        this.ordergebuehr = ordergebuehr;
    }

    public double getKapitalmarktzinssatz() {
        return kapitalmarktzinssatz;
    }

    public void setKapitalmarktzinssatz(double kapitalmarktzinssatzInProzent) {
        this.kapitalmarktzinssatz = kapitalmarktzinssatzInProzent;
    }


    @Override
    public String toString() {
        return "Periode{" +
                "id=" + id +
                ", name='" + spiel + '\'' +
                ", ordergebühr='" + ordergebuehr + '\'' +
                ", kapitalmarktzinssatz=" + kapitalmarktzinssatz +
                '}';
    }
}
