package de.dhbw.karlsruhe.model.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WertpapierArt {

    public static final long WERTPAPIER_AKTIE = 1;
    public static final long WERTPAPIER_ANLEIHE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String beschreibung;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        return "WertpapierArt{" +
                "id=" + id +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }
}
