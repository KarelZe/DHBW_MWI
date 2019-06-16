package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

/**
 * Klasse bietet einen einfachen Zugriff auf das Buchungs-Repository.
 * Implementierung des Facade Patterns. Für Details siehe z. B. https://refactoring.guru/design-patterns/facade
 */
public class PortfolioFassade {

    private static PortfolioFassade instanz;
    private BuchungRepository buchungRepository;

    private PortfolioFassade() {
        buchungRepository = BuchungRepository.getInstanz();
    }

    public static PortfolioFassade getInstanz() {
        if (PortfolioFassade.instanz == null) {
            PortfolioFassade.instanz = new PortfolioFassade();
        }
        return instanz;
    }

    public double getZahlungsmittelkontoSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungZahlungsmittelkonto).sum();
    }

    public double getDepotSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungDepot).sum();
    }

    public double getFestgeldSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getVeraenderungZahlungsmittelkonto).sum();
    }

    /**
     * Gibt den Gesamtsaldo eines Teilnehmers bestehend aus Zahlungsmittelkonto-, Festgeld- und Depotguthaben zurück.
     *
     * @param teilnehmerId Id des Teilnehmers
     * @return Gesamtsaldo des Teilnehmer Engagements
     */
    public double getGesamtSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(b -> b.getVeraenderungZahlungsmittelkonto() + b.getVeraenderungDepot() + b.getVeraenderungFestgeld()).sum();
    }

    public List<Portfolioposition> getAnleihePositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ANLEIHE).collect(Collectors.toList());
    }

    public List<Portfolioposition> getAktienPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).collect(Collectors.toList());
    }

    public List<Portfolioposition> getEtfPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_ETF).collect(Collectors.toList());
    }

    public List<Portfolioposition> getFestgeldPositionen(long teilnehmerId, long periodenId) {
        List<Portfolioposition> portfoliopositionen = getPortfoliopositionen(teilnehmerId, periodenId);
        return portfoliopositionen.stream().filter(p -> p.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_FESTGELD).collect(Collectors.toList());
    }

    public List<Portfolioposition> getPortfoliopositionen(long teilnehmerId, long periodenId) {

        /* Schöner: Adaptiert von https://stackoverflow.com/a/26346574
        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungen.stream().collect(groupingBy(Buchung::getWertpapier, collectingAndThen(reducing((a, b) -> new Portfolioposition(a.getWertpapier(), a.getVolumen() + b.getVolumen())),Optional::get)));
        */

        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);
        // Beschränke die Auswahl auf alle Transaktionen bis einschließlich zur abzufragenden Periode, dies ist notwendig, um auch umsatzlose Portfolios zu erfassen und summiere alle Transaktionen je Wertpapierposition
        Map<Wertpapier, Double> buchungenMap = buchungen.stream().filter(b -> b.getPeriode().getId() <= periodenId).collect(groupingBy(Buchung::getWertpapier, summingDouble(Buchung::getVolumen)));
        // Konvertiere Map mit Summen je Wertpapier in List vom Typ Portfolioposition
        return buchungenMap.entrySet().stream().map(w -> new Portfolioposition(w.getKey(), w.getValue())).collect(Collectors.toList());
    }
}
