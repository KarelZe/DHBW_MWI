package de.dhbw.karlsruhe.model;

public class PeriodeWertpapier {
    private long id;
    private long periodeId;
    private long wertpapierId;
    private double kurs;

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

    public long getWertpapierId() {
        return wertpapierId;
    }

    public void setWertpapierId(long wertpapierId) {
        this.wertpapierId = wertpapierId;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }
}
