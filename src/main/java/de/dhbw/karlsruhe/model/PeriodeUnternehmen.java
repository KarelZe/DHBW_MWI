package de.dhbw.karlsruhe.model;

public class PeriodeUnternehmen {
    private long id;
    private long periodeId;
    private long unternehmenId;
    private int rating;
    private double aktienkurs;

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

    public long getUnternehmenId() {
        return unternehmenId;
    }

    public void setUnternehmenId(long unternehmenId) {
        this.unternehmenId = unternehmenId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getAktienkurs() {
        return aktienkurs;
    }

    public void setAktienkurs(double aktienkurs) {
        this.aktienkurs = aktienkurs;
    }
}
