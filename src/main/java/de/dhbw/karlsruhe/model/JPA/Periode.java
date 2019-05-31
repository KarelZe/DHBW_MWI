package de.dhbw.karlsruhe.model.JPA;

import javax.persistence.*;

@Entity
public class Periode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Spiel spiel;

    private double ordergebuehrInProzent;

    private double kapitalmarktzinssatzInProzent;

    public Periode(Spiel spiel, double ordergebuehrInProzent, double kapitalmarktzinssatzInProzent) {
        this.spiel = spiel;
        this.ordergebuehrInProzent = ordergebuehrInProzent;
        this.kapitalmarktzinssatzInProzent = kapitalmarktzinssatzInProzent;
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

    public double getOrdergebuehrInProzent() {
        return ordergebuehrInProzent;
    }

    public void setOrdergebuehrInProzent(double ordergebuehrInProzent) {
        this.ordergebuehrInProzent = ordergebuehrInProzent;
    }

    public double getKapitalmarktzinssatzInProzent() {
        return kapitalmarktzinssatzInProzent;
    }

    public void setKapitalmarktzinssatzInProzent(double kapitalmarktzinssatzInProzent) {
        this.kapitalmarktzinssatzInProzent = kapitalmarktzinssatzInProzent;
    }
}
