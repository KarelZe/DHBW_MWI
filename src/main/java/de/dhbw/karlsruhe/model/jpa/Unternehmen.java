package de.dhbw.karlsruhe.model.jpa;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;

import javax.persistence.*;
import java.util.Set;

/**
 * <p>
 * POJO Klasse für die Speicherung eines {@link Unternehmen Unternehmens}.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * </p>
 *
 * <p>
 * Ein {@link Unternehmen} ist in der Software das Äquivalent zum Planspielunternehmen der Benutzer.
 * Ein Planspielunternehmen kann als Emittent von Aktien und Anleihen auftreten.
 * Neben den Planspielunternehmen bestehen in der Anwendung Dummy-Unternehmen für die Ausgabe von
 * Spielkapital, die Emission des GMAX und die Anlage von Festgeldern.
 * </p>
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
public class Unternehmen {
    public static final int UNTERNEHMEN_TEILNEHMER = 1;
    public static final int UNTERNEHMEN_KAPITALANLAGEGESELLSCHAFT = 2;
    public static final int UNTERNEHMEN_BANK = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String farbe;
    private int unternehmenArt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "spiel_id")
    private Spiel spiel;

    @OneToMany(mappedBy = "unternehmen", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Wertpapier> wertpapierSet;

    public Unternehmen(String name, String farbe) {
        this();
        this.name = name;
        this.farbe = farbe;
    }

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor">https://docs.jboss.org/</a>.
     */
    public Unternehmen() {
        this.farbe = "0xff0000ff";
        this.name = "";
        this.unternehmenArt = UNTERNEHMEN_TEILNEHMER;
        this.spiel = AktuelleSpieldaten.getInstanz().getSpiel();
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

    public int getUnternehmenArt() {
        return unternehmenArt;
    }

    public void setUnternehmenArt(int unternehmensArt) {
        this.unternehmenArt = unternehmensArt;
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
                ", unternehmenArt=" + unternehmenArt +
                ", spiel=" + spiel +
                '}';
    }
}
