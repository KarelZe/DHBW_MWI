package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TransaktionsArt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String beschreibung;

    @OneToMany(mappedBy = "transaktionsArt")
    private List<Buchung> buchungen = new ArrayList<Buchung>();

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
