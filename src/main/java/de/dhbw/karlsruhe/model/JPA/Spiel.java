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

    public static final int SPIEL_AKTIV = 1;
    public static final int SPIEL_INAKTIV = 2;
    private int ist_aktiv;

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

    public int getIst_aktiv() {
        return ist_aktiv;
    }

    public void setIst_aktiv(int ist_aktiv) {
        this.ist_aktiv = ist_aktiv;
    }
}
