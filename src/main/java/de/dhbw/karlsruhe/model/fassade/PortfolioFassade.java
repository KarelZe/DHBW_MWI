package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.WertpapierRepository;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.TransaktionsArt;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Die Klasse bietet einen einfachen Zugriff auf das {@link BuchungRepository}, indem {@link Buchung Buchungen}
 * zu {@link Portfolioposition Portfoliopositionen} aggregiert werden.
 * Verwendung ist in Verbindung mit {@link Portfolioposition} sinnvoll.
 * Implementierung erfolgt Facade-Patterns (GOF) in Verbindung mit dem Singleton-Patterns (GOF).
 * @author Markus Bilz, Raphael Winkler
 */
public class PortfolioFassade {

    private static PortfolioFassade instanz;
    private BuchungRepository buchungRepository;

    /**
     * Implementierung des Singleton-Patterns (GOF).
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
     * Methode gibt den aktuellen Saldo eines Zahlungsmittelkontos eines Teilnehmers zurück.
     * @param teilnehmerId Id des Kontoinhabers
     * @return Saldo des Zahlungsmittelkontos
     * @author Markus Bilz
     */
    public double getZahlungsmittelkontoSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungZahlungsmittelkonto).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo eines Depots eines Teilnehmers zurück.
     * @param teilnehmerId Id des Depotinhabers
     * @return Saldo des Depots
     * @author Markus Bilz
     */
    public double getDepotSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungDepot).sum();
    }

    /**
     * Methode gibt den aktuellen Saldo eines Festgelds für einen Benutzer zurück.
     * Hierbei werden alle Buchungen aller Perioden addiert, um den aktuellen Saldo zu erhalten.
     * @param teilnehmerId Id des Festgeldinhabers
     * @return Saldo des Depots.
     * @author Markus Bilz
     */
    public double getFestgeldSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungFestgeld).sum();
    }

    //Todo kommentieren Raphael
    public double getEtfSaldo(long teilnehmerId, long periodenId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream()
                .filter(b -> b.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF)
                .mapToDouble(Buchung::getVeraenderungDepot).sum();
/*        buchungenTeilnehmer = buchungenTeilnehmer.stream().filter(b -> b.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF).collect(toList());
        long vorhandeneStueckzahl = 0;
        for (Buchung b: buchungenTeilnehmer) {
            if(b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_KAUFEN) {
                vorhandeneStueckzahl += b.getStueckzahl();
            } else if(b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN){
                vorhandeneStueckzahl -= b.getStueckzahl();
            }
        }
        return vorhandeneStueckzahl *
                KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId,
                        WertpapierRepository.getInstanz().findByWertpapierArt(WertpapierArt.WERTPAPIER_ETF).getId())
                        .orElseThrow(NoSuchElementException::new).getKurs();*/
    }

    public double getAktienSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        buchungenTeilnehmer = buchungenTeilnehmer.stream()
                .filter(b -> b.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).collect(Collectors.toList());
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungDepot).sum();

    }

    public double getAnleihenSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream()
                .filter(b -> b.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE)
                .mapToDouble(Buchung::getVeraenderungDepot).sum();
    }



    /**
     * Methode gibt den aktuellen Saldo eines Teilnehmers bestehend aus Zahlungsmittelkonto-, Festgeld- und Depotguthaben zurück.
     * @param teilnehmerId Id des Teilnehmers
     * @return Gesamtsaldo des Benutzer Engagements
     * @author Markus Bilz
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
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden.
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
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden
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
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden
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
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId Periode, bis zu der Buchungen berücksichtigt werden
     * @return Liste mit Portfolio-Positionen
     * @author Markus Bilz
     */
    public List<Portfolioposition> getPortfoliopositionen(long teilnehmerId, long periodenId) {

/*        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        // Beschränke die Auswahl auf alle Transaktionen bis einschließlich zur abzufragenden Periode, dies ist notwendig, um auch umsatzlose Portfolios zu erfassen und summiere alle Transaktionen je Wertpapierposition
        Map<Wertpapier, Double> buchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId).collect(groupingBy(Buchung::getWertpapier, summingDouble(Buchung::getVolumen)));
        // Konvertiere Map mit Summen je Wertpapier in List vom Typ Portfolioposition
        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());*/

        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId)
                .forEach(b -> {
                    if (b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN) {
                        b.setStueckzahl(b.getStueckzahl() * (-1));
                    }
                });
        Map<Wertpapier, Long> buchungenMap = buchungen.stream().collect(groupingBy(Buchung::getWertpapier, summingLong(Buchung::getStueckzahl)));


        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());
    }

/*    public List<Portfolioposition> getKaufPortfoliopositionen(long teilnehmerId, long periodenId) {
        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        buchungen = buchungen.stream().filter(b -> b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_KAUFEN).collect(toList());
        Map<Wertpapier, Double> buchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId).collect(groupingBy(Buchung::getWertpapier, summingDouble(Buchung::getVolumen)));
        // Konvertiere Map mit Summen je Wertpapier in List vom Typ Portfolioposition
        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());

    }

    public List<Portfolioposition> getVerkaufPortfoliopositionen(long teilnehmerId, long periodenId) {
        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        buchungen = buchungen.stream().filter(b -> b.getTransaktionsArt().getId() == TransaktionsArt.TRANSAKTIONSART_VERKAUFEN).collect(toList());
        Map<Wertpapier, Double> buchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId).collect(groupingBy(Buchung::getWertpapier, summingDouble(Buchung::getVolumen)));
        // Konvertiere Map mit Summen je Wertpapier in List vom Typ Portfolioposition
        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());

    }*/

    /**
     * // TODO: Raphael kommentieren
     *
     * @param teilnehmerId Id des Teilnehmers
     * @param periodenId   Periode, bis zu der Buchungen berücksichtigt werden
     * @param wertpapierId Id des Wertpapiers
     * @return Anzahl der {@link Portfolioposition Portfoliopositionen}
     * @author Raphael Winkler
     */
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

    public double getRenditeDepot(long teilnehmerId, long periodenId, long wertpapierId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public double getRenditeFestgeld(long teilnehmerId, long periodenId, long wertpapierId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public double getRenditeEtf(long teilnehmerId, long periodenId, long wertpapierId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public double getRenditeAnleihenGesamt(long teilnehmerId, long periodenId, long wertpapierId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public double getRenditeAktienGesamt(long teilnehmerId, long periodenId) {
        double rendite = 0;
        ArrayList<Portfolioposition> portfoliopositionen = new ArrayList<>(getAktienPositionen(teilnehmerId, periodenId));

        for (Portfolioposition p : portfoliopositionen) {
            rendite += getRenditeAktie(teilnehmerId, periodenId, p.getWertpapier().getId());
        }

        return rendite;

    }

    public double getRenditeAnleihe(long teilnehmerId, long periodenId, long wertpapierId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public double getRenditeAktie(long teilnehmerId, long periodenId, long wertpapierId) {
        double gezahlt = 0;
        double depotstand = 0;
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        buchungen = buchungen.stream().filter(b -> b.getWertpapier().getId() == wertpapierId)
                .filter(b -> b.getPeriode().getId() <= periodenId)
                .collect(toList());
        for (Buchung b : buchungen) {
            gezahlt += b.getVeraenderungZahlungsmittelkonto();
            depotstand += b.getVeraenderungDepot();
        }
        return gezahlt + depotstand;

    }


}
