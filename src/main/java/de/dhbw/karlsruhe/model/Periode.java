package de.dhbw.karlsruhe.model;

public class Periode {
    private long id;
    private long spielId;
    private double ordergebuehrInProzent;
    private double kapitalmarktzinssatzInProzent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpielId() {
        return spielId;
    }

    public void setSpielId(long spielId) {
        this.spielId = spielId;
    }

    public double getOrdergebuehrInProzent() {
        return ordergebuehrInProzent;
    }

    public void setOrdergebuehrInProzent(double ordergebuehrInProzent) {
        this.ordergebuehrInProzent = ordergebuehrInProzent;
    }

    public double getKapitalmarktzinssatzInProzent() {
        return kapitalmarktzinssatzInProzent;
    }

    public void setKapitalmarktzinssatzInProzent(double kapitalmarktzinssatzInProzent) {
        this.kapitalmarktzinssatzInProzent = kapitalmarktzinssatzInProzent;
    }
}
