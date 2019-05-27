package de.dhbw.karlsruhe.model.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Spiel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private double startkapital;

    private Date erstellungsdatum;

    private boolean isAktiv;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getStartkapital() {
        return startkapital;
    }

    public void setStartkapital(double startkapital) {
        this.startkapital = startkapital;
    }

    public Date getErstellungsdatum() {
        return erstellungsdatum;
    }

    public void setErstellungsdatum(Date erstellungsdatum) {
        this.erstellungsdatum = erstellungsdatum;
    }

    public boolean isAktiv() {
        return isAktiv;
    }

    public void setAktiv(boolean aktiv) {
        isAktiv = aktiv;
    }
}
