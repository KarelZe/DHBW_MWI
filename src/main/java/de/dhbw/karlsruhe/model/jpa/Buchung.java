package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

@Entity
public class Buchung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "periode_id")
    private Periode periode;

    @ManyToOne
    @JoinColumn(name = "teilnehmer_id")
    private Teilnehmer teilnehmer;

    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    @ManyToOne
    @JoinColumn(name = "transaktionsart_id")
    private TransaktionsArt transaktionsArt;

    private long stueckzahl;

    private long periodeWertpapierId;

    private double volumen; //beinhaltet Dividendenhöhe bzw. Ordervolumen

    private double ordergebuehr;

    private double saldoZahlungsmittelkonto;

    private double saldoDepot;

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

    public Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public TransaktionsArt getTransaktionsArt() {
        return transaktionsArt;
    }

    public void setTransaktionsArt(TransaktionsArt transaktionsArt) {
        this.transaktionsArt = transaktionsArt;
    }

    public long getStueckzahl() {
        return stueckzahl;
    }

    public void setStueckzahl(long stueckzahl) {
        this.stueckzahl = stueckzahl;
    }

    public long getPeriodeWertpapierId() {
        return periodeWertpapierId;
    }

    public void setPeriodeWertpapierId(long periodeWertpapierId) {
        this.periodeWertpapierId = periodeWertpapierId;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getOrdergebuehr() {
        return ordergebuehr;
    }

    public void setOrdergebuehr(double ordergebuehr) {
        this.ordergebuehr = ordergebuehr;
    }

    public double getSaldoZahlungsmittelkonto() {
        return saldoZahlungsmittelkonto;
    }

    public void setSaldoZahlungsmittelkonto(double saldoZahlungsmittelkonto) {
        this.saldoZahlungsmittelkonto = saldoZahlungsmittelkonto;
    }

    public double getSaldoDepot() {
        return saldoDepot;
    }

    public void setSaldoDepot(double saldoDepot) {
        this.saldoDepot = saldoDepot;
    }
}
