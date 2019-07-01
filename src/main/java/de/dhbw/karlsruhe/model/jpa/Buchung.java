package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;

/**
 * <p>
 * POJO Klasse für die Speicherung einer {@code Buchung}.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * </p>
 * <p>
 * Grundsätzlich entspricht jede Veränderung eines Saldos z. B. Zinsgutschrift, Kauf, Verbuchung Startkapital einer
 * {@code Buchung}, dies erlaubt eine Analyse von Saldenveränderungen und eine transparente Rückabwicklung von Buchungen.
 * Weiterhin sind einzelne Buchungen für die korrekte Bestimmung der Rendite notwendig.
 * </p>
 *
 * @author Markus Bilz, Christian Fix
 */
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
    private Benutzer benutzer;

    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    @ManyToOne
    @JoinColumn(name = "transaktionsart_id")
    private TransaktionsArt transaktionsArt;

    private long stueckzahl;

    private double volumen; //beinhaltet Dividendenhöhe bzw. Ordervolumen

    private double ordergebuehr;

    private double veraenderungZahlungsmittelkonto;

    private double veraenderungDepot;

    private double veraenderungFestgeld;

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor">https://docs.jboss.org/</a>.
     */
    public Buchung() {
    }

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

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
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

    public double getVeraenderungZahlungsmittelkonto() {
        return veraenderungZahlungsmittelkonto;
    }

    public void setVeraenderungZahlungsmittelkonto(double veraenderungZahlungsmittelkonto) {
        this.veraenderungZahlungsmittelkonto = veraenderungZahlungsmittelkonto;
    }

    public double getVeraenderungDepot() {
        return veraenderungDepot;
    }

    public void setVeraenderungDepot(double veraenderungDepot) {
        this.veraenderungDepot = veraenderungDepot;
    }

    public double getVeraenderungFestgeld() {
        return veraenderungFestgeld;
    }

    public void setVeraenderungFestgeld(double veraenderungFestgeld) {
        this.veraenderungFestgeld = veraenderungFestgeld;
    }
}
