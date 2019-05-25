package de.dhbw.karlsruhe.model;

import de.dhbw.karlsruhe.model.JPA.Teilnehmer;

public class CurrentUser {
    private Teilnehmer teilnehmer;

    public CurrentUser(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }
}
