package de.dhbw.karlsruhe.model.JPA;

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

    public static final int UNTERNEHMEN_TEILNEHMER = 1;
    public static final int UNTERNEHMEN_KAPITALANLAGEGESELLSCHAFT = 2;
    private int ist_spielbar;


    public Unternehmen(String name, String farbe) {
        this.name = name;
        this.farbe = farbe;
        this.ist_spielbar = UNTERNEHMEN_TEILNEHMER;
    }

    public Unternehmen() {
    }


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

    public int getIst_spielbar() {
        return ist_spielbar;
    }

    public void setIst_spielbar(int ist_spielbar) {
        this.ist_spielbar = ist_spielbar;
    }

    @Override
    public String toString() {
        return "Unternehmen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", farbe='" + farbe + '\'' +
                ", ist_spielbar=" + ist_spielbar +
                '}';
    }
}
