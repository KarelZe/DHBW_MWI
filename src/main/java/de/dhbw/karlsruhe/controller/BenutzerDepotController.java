package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.BuchungRepository;
import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.PeriodenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.Buchung;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import javafx.fxml.FXML;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BenutzerDepotController implements ControlledScreen {

    private ScreenController screenController;
    private List<Portfolioposition> depotAktien;
    private List<Portfolioposition> depotAnleihen;
    private List<Portfolioposition> depotETF;
    private List<Portfolioposition> depotFestgeld;


    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }

    private Periode findAktuellePeriode() throws NoSuchElementException {
        return PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);

    }

    @FXML
    private void initialize() {
        long teilnehmerID = AktuelleSpieldaten.getInstanz().getBenutzer().getId();
        long periodeID = findAktuellePeriode().getId();
        depotAktien = PortfolioFassade.getInstanz().getAktienPositionen(teilnehmerID, periodeID);
        depotAnleihen = PortfolioFassade.getInstanz().getAnleihePositionen(teilnehmerID, periodeID);
        depotETF = PortfolioFassade.getInstanz().getEtfPositionen(teilnehmerID, periodeID);
        depotFestgeld = PortfolioFassade.getInstanz().getFestgeldPositionen(teilnehmerID, periodeID);


    }


    private double calcRendite(long teilnehmerID, long periodeID, Portfolioposition portfolioposition) {
        List<Buchung> buchungen = BuchungRepository.getInstanz().findByTeilnehmerId(teilnehmerID);
        buchungen = buchungen.stream().filter(b -> b.getWertpapier().getId() == portfolioposition.getWertpapier()
                .filter(b ->)
                .getId()).collect(Collectors.toList());
        Kurs aktuellerKurs = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodeID, portfolioposition.getWertpapier().getId()).orElseThrow(NoSuchElementException::new);

        for (Buchung b : buchungen) {
            long buchungPeriodeId = b.getPeriode().getId();
            Kurs buchungKurs = KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(buchungPeriodeId, b.getWertpapier().getId()).orElseThrow(NoSuchElementException::new);

        }


        return 0;
    }


}

