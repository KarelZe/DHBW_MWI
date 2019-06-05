package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rolle {
    public static final long ROLLE_TEILNEHMER = 1;
    public static final long ROLLE_SPIELLEITER = 2;
    @Id
    private long id;
    private String name;

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
