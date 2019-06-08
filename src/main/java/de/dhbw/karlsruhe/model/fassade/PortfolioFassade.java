package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasse bietet einen einfachen Zugriff auf das Buchungs-Repository.
 * Implementierung des Facade Patterns. Für Details siehe z. B. https://refactoring.guru/design-patterns/facade
 */
public class PortfolioFassade {

    private BuchungRepository buchungRepository;

    public PortfolioFassade() {
        buchungRepository = BuchungRepository.getInstanz();
    }

    public double getZahlungsmittelkontoSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getSaldoZahlungsmittelkonto).sum();
    }

    public double getDepotSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getSaldoDepot).sum();
    }

    public double getFestgeldSaldo(long teilnehmerId) {
        List<Buchung> buchungenTeilnehmer = buchungRepository.findByTeilnehmerId(teilnehmerId);
        return buchungenTeilnehmer.stream().mapToDouble(Buchung::getSaldoFestgeld).sum();
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
        BuchungRepository buchungRepository = BuchungRepository.getInstanz();
        List<Buchung> buchungen = buchungRepository.findByTeilnehmerId(teilnehmerId);

        // TODO: Muss noch implementiert werden.
        // https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-
        // Map<Integer,Portfolioposition> ergebnis = buchungen.stream().collect(groupingBy(w));
        // filtere auf Periode
        // gruppiere buchungen
        // buchungen.stream();
        ArrayList<Portfolioposition> portfoliopositionen = new ArrayList<>();
        return portfoliopositionen;
    }
}
