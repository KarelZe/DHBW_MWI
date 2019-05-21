package de.dhbw.karlsruhe.model;

import javax.persistence.*;

@Entity
public class PeriodeUnternehmen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Periode periode;

    @ManyToOne
    @JoinColumn(name = "id")
    private Unternehmen unternehmen;

    private int rating;

    private double aktienkurs;

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

    public Unternehmen getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getAktienkurs() {
        return aktienkurs;
    }

    public void setAktienkurs(double aktienkurs) {
        this.aktienkurs = aktienkurs;
    }
}
