package de.dhbw.karlsruhe.model.JPA;

import javax.persistence.*;

@Entity
public class Wertpapier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="id")
    private WertpapierArt wertpapierArt;

    @ManyToOne
    @JoinColumn(name="id")
    private Unternehmen unternehmen;

    private double nennwert; //optional (Anleihen)

    private double emissionszins; //optional (Anleihen)

    private int faelligkeitsPeriode; //optional (Anleihen)

    private int emissionsPeriode; //optional (Anleihen)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WertpapierArt getWertpapierArt() {
        return wertpapierArt;
    }

    public void setWertpapierArt(WertpapierArt wertpapierArt) {
        this.wertpapierArt = wertpapierArt;
    }

    public Unternehmen getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
    }

    public double getNennwert() {
        return nennwert;
    }

    public void setNennwert(double nennwert) {
        this.nennwert = nennwert;
    }

    public double getEmissionszins() {
        return emissionszins;
    }

    public void setEmissionszins(double emissionszins) {
        this.emissionszins = emissionszins;
    }

    public int getFaelligkeitsPeriode() {
        return faelligkeitsPeriode;
    }

    public void setFaelligkeitsPeriode(int faelligkeitsPeriode) {
        this.faelligkeitsPeriode = faelligkeitsPeriode;
    }

    public int getEmissionsPeriode() {
        return emissionsPeriode;
    }

    public void setEmissionsPeriode(int emissionsPeriode) {
        this.emissionsPeriode = emissionsPeriode;
    }
}
