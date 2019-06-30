package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.TransaktionsArt;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Klasse bietet einen einfachen Zugriff auf das Buchungs-Repository, indem Buchungen zu Portfoliopositionen aggregiert
 * werden. Verwendung ist in Verbindung mit {@link Portfolioposition} sinnvoll.
 * Implementierung erfolgt mittels  Facade-Patterns (GOF) in Verbindung mit dem Singleton-Patterns (GOF).
 * Für Pattern siehe <a href="https://refactoring.guru/design-patterns/facade/">https://refactoring.guru/</a>
 * @author Markus Bilz, Raphael Winkler
 */
public class PortfolioFassade {

    private static PortfolioFassade instanz;
    private BuchungRepository buchungRepository;

    /**
     * Implementierung des Singleton-Patterns (GOF).
     */
    private PortfolioFassade() {
        buchungRepository = BuchungRepository.getInstanz();
    }

    /**
     * Implementierung des Singleton-Patterns (GOF).
     *
     * @return Instanz der PortfolioFassade
     */
    public static PortfolioFassade getInstanz() {
        if (PortfolioFassade.instanz == null) {
            PortfolioFassade.instanz = new PortfolioFassade();
        }
        return instanz;
    }

    /**
     * Methode gibt den aktuellen Saldo eines Zahlungsmittelkontos eines Teilnehmers zurück.
     * @param teilnehmerId Id des Kontoinhabers
     * @return Saldo des Zahlungsmittelkontos
     */
    public double getZahlungsmittelkontoSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungZahlungsmittelkonto).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo eines Depots eines Teilnehmers zurück.
     * @param teilnehmerId Id des Depotinhabers
     * @return Saldo des Depots
     */
    public double getDepotSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungDepot).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo eines Festgelds für einen Teilnehmer zurück.
     * Hierbei werden alle Buchungen aller Perioden addiert, um den aktuellen Saldo zu erhalten.
     * @param teilnehmerId Id des Festgeldinhabers
     * @return Saldo des Depots.
     */
    public double getFestgeldSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungZahlungsmittelkonto).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo eines Teilnehmers bestehend aus Zahlungsmittelkonto-, Festgeld- und Depotguthaben zurück.
     * @param teilnehmerId Id des Teilnehmers
     * @return Gesamtsaldo des Teilnehmer Engagements
     */
    public double getGesamtSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(b -> b.getVeraenderungZahlungsmittelkonto() + b.getVeraenderungDepot() + b.getVeraenderungFestgeld()).sum();
    }

    /**
     * Methode gibt den Gesamtbestand an Anleihepositionen bis einschließlich eines bestimmten Periodenstichtags zurück.
     * Neben aktiven Anleihepositionen sind auch Anleihepositionen enthalten, die zwischenzeitlich veräußert wurden und
     * einen Bestand von Null aufweisen.
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden.
     * @return Liste mit Anleihepositionen
     */
    public List<Portfolioposition> getAnleihePositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).collect(Collectors.toList());
    }

    /**
     * Methode gibt den Gesamtbestand an Aktienpositionen bis einschließlich eines bestimmten Periodenstichtags zurück.
     * Neben aktiven Aktienpositionen sind auch Aktienpositionen, die zwischenzeitlich veräußert wurden und einen Bestand
     * von Null aufweisen.
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden.
     * @return Liste mit Aktienpositionen
     */
    public List<Portfolioposition> getAktienPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).collect(Collectors.toList());
    }

    /**
     * Methode gibt den Gesamtbestand an ETFs bis einschließlich eines bestimmten Periodenstichtags zurück.
     * Neben aktiven ETF-Positionen werden auch zwischenzeitlich veräußerte Positionen ausgeweien.
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit ETF-Positionen
     */
    public List<Portfolioposition> getEtfPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF).collect(Collectors.toList());
    }

    /**
     * Methode gibt den Gesamtbestand an Festgeldpositionen bis einschießlich eines bestimmten eines Periodenstichtags zurück.
     * Neben aktiven Festgeld-Positionen werden auch zwischenzeitlich veräußerte Positionen ausgewiesen.
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit Festgeld-Positionen
     */
    public List<Portfolioposition> getFestgeldPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD).collect(Collectors.toList());
    }

    /**
     * Methode gibt alle Portfoliopositionen das heißt Festgeld, Zahlungsmittelkonto, Depotpositionen etc. zurück bis
     * einschließlich eines Periodenstichtags zurück. Es werden neben aktiven Portfoliopositionen auch historische
     * Positonen ausgewiesen. Die Ermittlung erfolgt anhand von Buchungen, die dem Teilnehmer zugeordnet werden können.
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit Portfolio-Positionen
     */
    public List<Portfolioposition> getPortfoliopositionen(long teilnehmerId, long periodenId) {

        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        // Beschränke die Auswahl auf alle Transaktionen bis einschließlich zur abzufragenden Periode, dies ist notwendig, um auch umsatzlose Portfolios zu erfassen und summiere alle Transaktionen je Wertpapierposition
        Map<Wertpapier, Double> buchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId).collect(groupingBy(Buchung::getWertpapier, summingDouble(Buchung::getVolumen)));
        // Konvertiere Map mit Summen je Wertpapier in List vom Typ Portfolioposition
        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());
    }

    // TODO: Raphael warum so kompliziert? Ich würde einfach get ETFPositionen ... machen und dann addieren.
    public long getCountOfPositionen(long teilnehmerId, long periodenId, long wertpapierId) {
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        Map<Wertpapier, Long> kaufBuchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId)
                .filter(b -> b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_KAUFEN)
                .filter(b -> b.getWertpapier().getId() == wertpapierId)
                .collect(groupingBy(Buchung::getWertpapier, summingLong(Buchung::getStueckzahl)));

        Map<Wertpapier, Long> verkaufBuchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId)
                .filter(b -> b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN)
                .filter(b -> b.getWertpapier().getId() == wertpapierId)
                .collect(groupingBy(Buchung::getWertpapier, summingLong(Buchung::getStueckzahl)));

        if (kaufBuchungenMap.size() <= 0)
            return 0;
        else if (verkaufBuchungenMap.size() <= 0)
            return kaufBuchungenMap.get(WertpapierRepository.getInstanz().findById(wertpapierId).orElseThrow(NoSuchElementException::new));
        else
            return kaufBuchungenMap.get(WertpapierRepository.getInstanz().findById(wertpapierId).orElseThrow(NoSuchElementException::new))
                    - verkaufBuchungenMap.get(WertpapierRepository.getInstanz().findById(wertpapierId).orElseThrow(NoSuchElementException::new));
    }


}
