package de.dhbw.karlsruhe.model.DAO;

public class Wertpapier {
    private long id;
    private long wertpapierArtId;
    private long unternehmenId;
    private double nennwert; //optional (Anleihen)
    private double emissionszins; //optional (Anleihen)
    private int faelligkeitsPeriode; //optional (Anleihen)
    private int emissionsPeriode; //optional (Anleihen)
}
