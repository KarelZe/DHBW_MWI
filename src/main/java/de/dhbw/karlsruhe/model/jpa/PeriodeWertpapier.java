package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

@Entity
public class PeriodeWertpapier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Periode periode;

    @ManyToOne
    @JoinColumn(name = "id")
    private Wertpapier wertpapier;

    private double kurs;

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

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }
}
