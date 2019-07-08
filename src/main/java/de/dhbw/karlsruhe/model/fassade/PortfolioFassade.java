package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.KursRepository;
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
 * Die Klasse bietet einen einfachen Zugriff auf das {@link BuchungRepository}, indem {@link Buchung Buchungen}
 * zu {@link Portfolioposition Portfoliopositionen} aggregiert werden.
 *
 * <p>
 * Verwendung ist in Verbindung mit {@link Portfolioposition} sinnvoll.
 * </p>
 *
 * <p>
 * Implementierung erfolgt Facade-Patterns (GOF) in Verbindung mit dem Singleton-Patterns (GOF).
 * </p>
 *
 * @author Markus Bilz, Raphael Winkler
 */
public class PortfolioFassade {

    private static PortfolioFassade instanz;
    private BuchungRepository buchungRepository;

    /**
     * Implementierung des Singleton-Patterns (GOF).
     *
     * @author Markus Bilz
     */
    private PortfolioFassade() {
        buchungRepository = BuchungRepository.getInstanz();
    }

    /**
     * Implementierung des Singleton-Patterns (GOF).
     *
     * @return Instanz der PortfolioFassade
     * @author Markus Bilz
     */
    public static PortfolioFassade getInstanz() {
        if (PortfolioFassade.instanz == null) {
            PortfolioFassade.instanz = new PortfolioFassade();
        }
        return instanz;
    }


    /**
     * Methode gibt den aktuellen Saldo eines Depots eines Teilnehmers zurück.
     *
     * @param teilnehmerId Id des Depotinhabers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden.
     * @return Saldo des Depots
     * @author Raphael Winkler
     */
    public double getDepotSaldo(long teilnehmerId, long periodenId) {
        return getEtfSaldo(teilnehmerId, periodenId)
                + getAktienSaldo(teilnehmerId, periodenId)
                + getAnleihenSaldo(teilnehmerId, periodenId);
    }

    /**
     * Methode gibt den aktuellen Saldo eines Zahlungsmittelkontos eines Teilnehmers zurück.
     *
     * @param teilnehmerId Id des Kontoinhabers
     * @return Saldo des Zahlungsmittelkontos
     * @author Markus Bilz
     */
    public double getZahlungsmittelkontoSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungZahlungsmittelkonto).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo eines Festgelds für einen Benutzer zurück.
     * Hierbei werden alle Buchungen aller Perioden addiert, um den aktuellen Saldo zu erhalten.
     *
     * @param teilnehmerId Id des Festgeldinhabers
     * @return Saldo des Festgelds.
     * @author Markus Bilz
     */
    public double getFestgeldSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungFestgeld).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo des ETF-Bestands für einen Benutzer zurück.
     * Hierbei werden alle Buchungen aller Perioden addiert, um den aktuellen Saldo zu erhalten.
     *
     * @param teilnehmerId Id des Festgeldinhabers
     * @param periodenId   Id der aktuellen Periode
     * @return Saldo des Depots.
     * @author Raphael Winkler
     */
    public double getEtfSaldo(long teilnehmerId, long periodenId) {
        double etfSaldo = 0;
        List<Portfolioposition> etfPositionen = getEtfPositionen(teilnehmerId, periodenId);
        for (Portfolioposition p : etfPositionen) {
            etfSaldo += p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs();
        }
        return etfSaldo;
    }

    /**
     * Methode gibt den aktuellen Saldo des Aktien-Bestands für einen Benutzer zurück.
     * Hierbei werden alle Buchungen aller Perioden addiert, um den aktuellen Saldo zu erhalten.
     *
     * @param teilnehmerId Id des Festgeldinhabers
     * @param periodenId   Id der aktuellen Periode
     * @return Saldo des Depots.
     * @author Raphael Winkler
     */
    public double getAktienSaldo(long teilnehmerId, long periodenId) {
        double aktienSaldo = 0;
        List<Portfolioposition> etfPositionen = getAktienPositionen(teilnehmerId, periodenId);
        for (Portfolioposition p : etfPositionen) {
            aktienSaldo += p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs();
        }
        return aktienSaldo;

    }

    /**
     * Methode gibt den aktuellen Saldo des Anleihen-Bestands für einen Benutzer zurück.
     * Hierbei werden alle Buchungen aller Perioden addiert, um den aktuellen Saldo zu erhalten.
     *
     * @param teilnehmerId Id des Festgeldinhabers
     * @param periodenId   Id der aktuellen Periode
     * @return Saldo des Depots.
     * @author Raphael Winkler
     */
    public double getAnleihenSaldo(long teilnehmerId, long periodenId) {
        double anleihenSaldo = 0;
        List<Portfolioposition> etfPositionen = getAnleihePositionen(teilnehmerId, periodenId);
        for (Portfolioposition p : etfPositionen) {
            anleihenSaldo += p.getBezugsgroesse() * (KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs() / 100);
        }
        return anleihenSaldo;
    }


    /**
     * Methode gibt den aktuellen Saldo eines Teilnehmers bestehend aus Zahlungsmittelkonto-, Festgeld- und Depotguthaben zurück.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden.
     * @return Gesamtsaldo des Benutzer Engagements
     * @author Raphael Winkler
     */
    public double getGesamtSaldo(long teilnehmerId, long periodenId) {
        return getZahlungsmittelkontoSaldo(teilnehmerId)
                + getFestgeldSaldo(teilnehmerId)
                + getEtfSaldo(teilnehmerId, periodenId)
                + getAktienSaldo(teilnehmerId, periodenId)
                + getAnleihenSaldo(teilnehmerId, periodenId);
    }

    /**
     * Methode gibt den Gesamtbestand an Anleihepositionen bis einschließlich eines bestimmten Periodenstichtags zurück.
     * Neben aktiven Anleihepositionen sind auch Anleihepositionen enthalten, die zwischenzeitlich veräußert wurden und
     * einen Bestand von Null aufweisen.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden.
     * @return Liste mit Anleihepositionen
     * @author Markus Bilz
     */
    public List<Portfolioposition> getAnleihePositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).collect(Collectors.toList());
    }

    /**
     * Methode gibt den Gesamtbestand an Aktienpositionen bis einschließlich eines bestimmten Periodenstichtags zurück.
     * Neben aktiven Aktienpositionen sind auch Aktienpositionen, die zwischenzeitlich veräußert wurden und einen Bestand
     * von Null aufweisen.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden.
     * @return Liste mit Aktienpositionen
     * @author Markus Bilz
     */
    public List<Portfolioposition> getAktienPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).collect(Collectors.toList());
    }

    /**
     * Methode gibt den Gesamtbestand an ETFs bis einschließlich eines bestimmten Periodenstichtags zurück.
     * Neben aktiven ETF-Positionen werden auch zwischenzeitlich veräußerte Positionen ausgeweien.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit ETF-Positionen
     * @author Markus Bilz
     */
    public List<Portfolioposition> getEtfPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF).collect(Collectors.toList());
    }

    /**
     * Methode gibt den Gesamtbestand an Festgeldpositionen bis einschießlich eines bestimmten eines Periodenstichtags zurück.
     * Neben aktiven Festgeld-Positionen werden auch zwischenzeitlich veräußerte Positionen ausgewiesen.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit Festgeld-Positionen
     * @author Markus Bilz
     */
    public List<Portfolioposition> getFestgeldPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD).collect(Collectors.toList());
    }

    /**
     * Methode gibt alle Portfoliopositionen das heißt Festgeld, Zahlungsmittelkonto, Depotpositionen etc. zurück bis
     * einschließlich eines Periodenstichtags zurück. Es werden neben aktiven Portfoliopositionen auch historische
     * Positonen ausgewiesen. Die Ermittlung erfolgt anhand von Buchungen, die dem Benutzer zugeordnet werden können.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit Portfolio-Positionen
     * @author Markus Bilz, Raphael Winkler
     */
    public List<Portfolioposition> getPortfoliopositionen(long teilnehmerId, long periodenId) {

        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        // Beschränke die Auswahl auf alle Transaktionen bis einschließlich zur abzufragenden Periode, dies ist notwendig, um auch umsatzlose Portfolios zu erfassen und summiere alle Transaktionen je Wertpapierposition
        Map<Wertpapier, Double> buchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId).collect(groupingBy(Buchung::getWertpapier, summingDouble(Buchung::getVolumen)));
        // Konvertiere Map mit Summen je Wertpapier in List vom Typ Portfolioposition
        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());


    }


    /**
     * Die Methode gibt die Stückzahl eines Wertpapieres im Depot eines Teilnehmers zurück
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden
     * @param wertpapierId Id des Wertpapiers
     * @return Anzahl der {@link Portfolioposition Portfoliopositionen}
     * @author Raphael Winkler
     */

    public long getCountOfPositionen(long teilnehmerId, long periodenId, long wertpapierId) {
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);

        // Sammeln aller Kaufbuchungen eines Teilnehmes für ein Wertpapier
        Map<Wertpapier, Long> kaufBuchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId)
                .filter(b -> b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_KAUFEN)
                .filter(b -> b.getWertpapier().getId() == wertpapierId)
                .collect(groupingBy(Buchung::getWertpapier, summingLong(Buchung::getStueckzahl)));

        // Sammeln aller Verkaufbuchungen eines Teilnehmes für ein Wertpapier
        Map<Wertpapier, Long> verkaufBuchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId)
                .filter(b -> b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN)
                .filter(b -> b.getWertpapier().getId() == wertpapierId)
                .collect(groupingBy(Buchung::getWertpapier, summingLong(Buchung::getStueckzahl)));


        // Verrechnen der Kauf- und Verkaufbuchungen
        if (kaufBuchungenMap.size() <= 0)
            return 0;
        else if (verkaufBuchungenMap.size() <= 0)
            return kaufBuchungenMap.get(WertpapierRepository.getInstanz().findById(wertpapierId).orElseThrow(NoSuchElementException::new));
        else
            return kaufBuchungenMap.get(WertpapierRepository.getInstanz().findById(wertpapierId).orElseThrow(NoSuchElementException::new))
                    - verkaufBuchungenMap.get(WertpapierRepository.getInstanz().findById(wertpapierId).orElseThrow(NoSuchElementException::new));
    }



}
