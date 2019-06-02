package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

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
