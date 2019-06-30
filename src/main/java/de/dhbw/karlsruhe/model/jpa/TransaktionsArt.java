package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO Klasse für die Speicherung einer {@code TransaktionsArt}.
 * Mittels dieser Klasse erfolgt die Transformation von Daten der Tabelle der Datenbank in POJOs und vice versa.
 * Die Speicherung der {@code TransaktionsArt} in der Datenbank erfolgt in der eigenständigen Tabelle, da sqlite keine Enums unterstützt.
 * Die Datenbank-Tabelle lautet {@code Transaktions_Art}.
 *
 * @author Markus Bilz, Christian Fix
 */
@Entity
@Table(name = "Transaktions_Art")
public class TransaktionsArt {

    public static final long TRANSAKTIONSART_KAUFEN = 1;
    public static final long TRANSAKTIONSART_VERKAUFEN = 2;
    public static final long TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER = 3;
    public static final long TRANSAKTIONSART_ZINSGUTSCHRIFT_FESTGELD = 4;
    public static final long TRANSAKTIONSART_STARTKAPITAL = 5;
    public static final String TRANSAKTIONSART_KAUFEN_NAME = "Kaufen";
    public static final String TRANSAKTIONSART_VERKAUFEN_NAME = "Verkaufen";
    public static final String TRANSAKTIONSART_ZINSGUTSCHRIFT_WERTPAPIER_NAME = "Zinsgutschrift Wertpapier";
    public static final String TRANSAKTIONSART_ZINSGUTSCHRIFT_FESTGELD_NAME = "Zinsgutschrift Festgeld";
    public static final String TRANSAKTIONSART_STARTKAPITAL_NAME = "Startkapital";
    @Id
    private long id;

    private String beschreibung;

    @OneToMany(mappedBy = "transaktionsArt")
    private List<Buchung> buchungen = new ArrayList<>();

    /**
     * Implementierung eines Parameter-Losen Konstruktors. Diese Bereitstellung ist ein Best-Practice-Ansatz.
     * Siehe hierzu: <a href="https://docs.jboss.org">https://docs.jboss.org/hibernate/core/3.5/reference/en/html/persistent-classes.html#persistent-classes-pojo-constructor</a>.
     */
    public TransaktionsArt() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
