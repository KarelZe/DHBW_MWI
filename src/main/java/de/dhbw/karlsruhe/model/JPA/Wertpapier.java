package de.dhbw.karlsruhe.model.JPA;

import javax.persistence.*;

@Entity
public class Wertpapier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "wertpapier_art_id")
    private WertpapierArt wertpapierArt;

    @ManyToOne
    @JoinColumn(name = "unternehmen_id")
    private Unternehmen unternehmen;

    private double nennwert; //optional (Anleihen)

    private double emissionszins; //optional (Anleihen)

    private int faelligkeit_periode; //optional (Anleihen)

    private int emission_periode; //optional (Anleihen)

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

    public int getFaelligkeit_periode() {
        return faelligkeit_periode;
    }

    public void setFaelligkeit_periode(int faelligkeit_periode) {
        this.faelligkeit_periode = faelligkeit_periode;
    }

    public int getEmission_periode() {
        return emission_periode;
    }

    public void setEmission_periode(int emission_periode) {
        this.emission_periode = emission_periode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wertpapier that = (Wertpapier) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Wertpapier{" +
                "id=" + id +
                ", wertpapierArt=" + wertpapierArt +
                ", unternehmen=" + unternehmen +
                ", nennwert=" + nennwert +
                ", emissionszins=" + emissionszins +
                ", faelligkeit_periode=" + faelligkeit_periode +
                ", emission_periode=" + emission_periode +
                '}';
    }
}
