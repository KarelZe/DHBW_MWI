package de.dhbw.karlsruhe.model.jpa;

import javax.persistence.*;
import java.util.Set;
import java.util.List;
import de.dhbw.karlsruhe.helper.HibernateHelper;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Entity
public class Teilnehmer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String benutzername;
    private String passwort;
    private String vorname;
    private String nachname;

    @ManyToOne
    @JoinColumn(name = "unternehmen_id")
    private Unternehmen unternehmen;

    @ManyToOne
    @JoinColumn(name = "rolle_id")
    private Rolle rolle;

    @ManyToOne //( cascade = CascadeType.ALL)
    @JoinColumn(name = "spiel_id")
    private Spiel spiel;

    @OneToMany(mappedBy = "teilnehmer", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Buchung> buchungSet;

    // Hibernate benötigt leeren Konstruktor. Zur Erklärung siehe https://stackoverflow.com/a/25452112
    public Teilnehmer() {

    }

    public Teilnehmer(String benutzername, String passwort, String vorname, String nachname, Unternehmen unternehmen, Rolle rolle, Spiel spiel) {
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
        this.unternehmen = unternehmen;
        this.rolle = rolle;
        this.spiel = spiel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Unternehmen getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
    }

    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    @Override
    public String toString() {
        return "Teilnehmer{" +
                "id=" + id +
                ", benutzername='" + benutzername + '\'' +
                ", passwort='" + passwort + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", unternehmen'" + unternehmen + '\'' +
                '}';
    }

    //ToDo: Tatsächlich neuste Salden abfragen, nicht die Salden mit der höchsten Perioden-ID
    public double getGesamtSaldo(){
        Transaction tx = null;
        double gesamtsaldo = -999;
        System.out.println("Saldo-Query gestartet");
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            System.out.println("Saldo-Query gestartet (Try)");
            tx = session.beginTransaction();
            //"SELECT saldoDepot, saldoFestgeld, saldoZahlungsmittelkonto from Buchung WHERE teilnehmer_id =:t_id ORDER BY perioden_id DESC"
            String queryString = "SELECT saldoDepot FROM Buchung WHERE teilnehmer_id =:t_id ORDER BY perioden_id DESC";
            Query query = session.createQuery(queryString);
            query.setParameter("t_id", id);
            query.setMaxResults(1);


            /*List<Object[]> salden= (List<Object[]>) query.list();
            for(Object[] saldo: salden) {
                double depot = (double) saldo[0];
                double festgeld = (double) saldo[1];
                double zahlungsmittel = (double) saldo[2];
            }*/
            tx.commit();
            query.uniqueResult();

        } catch (HibernateException e) {
            System.out.println("Saldo-Query gestartet (Catch)");
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        }
        return gesamtsaldo;
    }

}