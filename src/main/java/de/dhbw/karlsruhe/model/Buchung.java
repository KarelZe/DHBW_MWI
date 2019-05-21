package de.dhbw.karlsruhe.model;

public class Buchung {
    private long id;
    private long periodeId;
    private long teilnehmerId;
    private long wertpapierId;
    private long transaktionsArtId;
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

    public long getPeriodeId() {
        return periodeId;
    }

    public void setPeriodeId(long periodeId) {
        this.periodeId = periodeId;
    }

    public long getTeilnehmerId() {
        return teilnehmerId;
    }

    public void setTeilnehmerId(long teilnehmerId) {
        this.teilnehmerId = teilnehmerId;
    }

    public long getWertpapierId() {
        return wertpapierId;
    }

    public void setWertpapierId(long wertpapierId) {
        this.wertpapierId = wertpapierId;
    }

    public long getTransaktionsArtId() {
        return transaktionsArtId;
    }

    public void setTransaktionsArtId(long transaktionsArtId) {
        this.transaktionsArtId = transaktionsArtId;
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
