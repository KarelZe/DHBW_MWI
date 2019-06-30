package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * POJO Klasse für die Speicherung der Rollen.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
public class Spiel {
    public static final int SPIEL_AKTIV = 1;
    public static final int SPIEL_INAKTIV = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private double startkapital;
    private Date erstellungsdatum;
    private int ist_aktiv;

    @OneToMany(mappedBy = "spiel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Teilnehmer> teilnehmerSet;

    @OneToMany(mappedBy = "spiel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Periode> periodeSet;

    @OneToMany(mappedBy = "spiel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Unternehmen> unternehmenSet;

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org">https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor</a>.
     */
    public Spiel() {
    }

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

    @Override
    public String toString(){
        return "Spiel{" +
                "Id=" + id +
                ", Startkapital= " + startkapital +
                ", Erstellungsdatum=" + erstellungsdatum +
                ", Ist aktiv=" + ist_aktiv +
                '}';
    }
}
