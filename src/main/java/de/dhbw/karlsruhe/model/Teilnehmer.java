package de.dhbw.karlsruhe.model;

import javax.persistence.*;

@Entity
public class Teilnehmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String benutzername;
    private String passwort;
    private String vorname;
    private String nachname;

    @ManyToOne
    @JoinColumn(name = "unternehmen_id", referencedColumnName = "id")
    private Unternehmen unternehmen;

    @ManyToOne
    @JoinColumn(name = "rolle_id", referencedColumnName = "id")
    private Rolle rolle;

    // Hibernate benötigt leeren Konstruktor. Zur Erklärung siehe https://stackoverflow.com/a/25452112
    public Teilnehmer() {

    }

    public Teilnehmer(String benutzername, String passwort, String vorname, String nachname, Unternehmen unternehmen, Rolle rolle) {
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
        this.unternehmen = unternehmen;
        this.rolle = rolle;
    }

    public long getId() {
        return id;
    }

    public Unternehmen getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
    }

    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }


    @Override
    public String toString() {
        return "Teilnehmer{" +
                "id=" + id +
                ", benutzername='" + benutzername + '\'' +
                ", passwort='" + passwort + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                '}';
    }

}