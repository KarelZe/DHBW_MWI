package de.dhbw.karlsruhe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Unternehmen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String farbe;

    private int ist_aktiv;

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

    public int getIst_aktiv() {
        return ist_aktiv;
    }

    public void setIst_aktiv(int ist_aktiv) {
        this.ist_aktiv = ist_aktiv;
    }

    @Override
    public String toString() {
        return "Unternehmen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", farbe='" + farbe + '\'' +
                ", ist_aktiv=" + ist_aktiv +
                '}';
    }
}
