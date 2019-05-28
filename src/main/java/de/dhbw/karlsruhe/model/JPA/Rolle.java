package de.dhbw.karlsruhe.model.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rolle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;

    public static final int ROLLE_TEILNEHMER = 1;
    public static final int ROLLE_SPIELLEITER = 2;

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
        return "Rolle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
