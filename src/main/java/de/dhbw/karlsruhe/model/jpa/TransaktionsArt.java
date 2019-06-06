package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Transaktions_Art")
public class TransaktionsArt {

    public static final long TRANSAKTIONSART_KAUFEN = 1;
    public static final long TRANSAKTIONSART_VERKAUFEN = 2;
    public static final long TRANSAKTIONSART_ZINSGUTSCHRIFT = 3;
    public static final String TRANSAKTIONSART_KAUFEN_NAME = "Kaufen";
    public static final String TRANSAKTIONSART_VERKAUFEN_NAME = "Verkaufen";
    public static final String TRANSAKTIONSART_ZINSGUTSCHRIFT_NAME = "Zinsgutschrift";

    @Id
    private long id;

    private String beschreibung;

    @OneToMany(mappedBy = "transaktionsArt")
    private List<Buchung> buchungen = new ArrayList<>();

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
