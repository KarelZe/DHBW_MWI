package de.dhbw.karlsruhe.model;

public class Unternehmen {
    private long id;
    private String name;
    private String farbe;
    private boolean isAktiv;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public boolean isAktiv() {
        return isAktiv;
    }

    public void setAktiv(boolean aktiv) {
        isAktiv = aktiv;
    }
}
