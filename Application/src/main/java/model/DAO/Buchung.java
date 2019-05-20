package model.DAO;

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
}
