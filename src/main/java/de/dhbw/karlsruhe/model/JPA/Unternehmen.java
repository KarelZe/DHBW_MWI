package de.dhbw.karlsruhe.model.JPA;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;

import javax.persistence.*;

@Entity
public class Unternehmen {
    public static final int UNTERNEHMEN_TEILNEHMER = 1;
    public static final int UNTERNEHMEN_KAPITALANLAGEGESELLSCHAFT = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String farbe;
    private int ist_spielbar;

    @ManyToOne
    @JoinColumn(name = "spiel_id")
    private Spiel spiel;


    public Unternehmen(String name, String farbe) {
        this();
        this.name = name;
        this.farbe = farbe;
    }

    public Unternehmen() {
        this.farbe = "0xff0000ff";
        this.name = "";
        this.ist_spielbar = UNTERNEHMEN_TEILNEHMER;
        this.spiel = AktuelleSpieldaten.getSpiel();
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

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
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
