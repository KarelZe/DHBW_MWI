package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

@Entity
public class Periode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "spiel_id")
    private Spiel spiel;

    @Column(name = "ordergebuehr_in_prozent")
    private double ordergebuehr;
    @Column(name = "kapitalmarktzins_in_prozent")
    private double kapitalmarktzinssatz;

    public Periode(Spiel spiel, double ordergebuehr, double kapitalmarktzinssatz) {
        this.spiel = spiel;
        this.ordergebuehr = ordergebuehr;
        this.kapitalmarktzinssatz = kapitalmarktzinssatz;
    }

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
