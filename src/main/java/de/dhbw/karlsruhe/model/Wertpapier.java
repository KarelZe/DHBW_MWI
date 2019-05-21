package de.dhbw.karlsruhe.model;

public class Wertpapier {
    private long id;
    private long wertpapierArtId;
    private long unternehmenId;
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

    public long getWertpapierArtId() {
        return wertpapierArtId;
    }

    public void setWertpapierArtId(long wertpapierArtId) {
        this.wertpapierArtId = wertpapierArtId;
    }

    public long getUnternehmenId() {
        return unternehmenId;
    }

    public void setUnternehmenId(long unternehmenId) {
        this.unternehmenId = unternehmenId;
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
