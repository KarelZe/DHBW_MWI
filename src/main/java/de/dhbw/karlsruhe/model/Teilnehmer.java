package de.dhbw.karlsruhe.model;

public class Teilnehmer {
    private long id;
    private String benutzername;
    private String passwort; //ToDo: Hashen
    private String vorname;
    private String nachname;
    private long unternehmenId;
    private long rolleId;

    public long getId() {
        return id;
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

    public long getUnternehmenId() {
        return unternehmenId;
    }

    public void setUnternehmenId(long unternehmenId) {
        this.unternehmenId = unternehmenId;
    }

    public long getRolleId() {
        return rolleId;
    }

    public void setRolleId(long rolleId) {
        this.rolleId = rolleId;
    }
}
