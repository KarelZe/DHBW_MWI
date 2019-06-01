package de.dhbw.karlsruhe.model.jpa;


import javax.persistence.*;

@Entity
@Table(name = "Wertpapier_Art")
public class WertpapierArt {

    public static final long WERTPAPIER_AKTIE = 1;
    public static final long WERTPAPIER_ANLEIHE = 2;
    public static final String WERTPAPIER_AKTIE_NAME = "Aktie";
    public static final String WERTPAPIER_ANLEIHE_NAME = "Anleihe";
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public WertpapierArt(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "WertpapierArt{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WertpapierArt that = (WertpapierArt) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
