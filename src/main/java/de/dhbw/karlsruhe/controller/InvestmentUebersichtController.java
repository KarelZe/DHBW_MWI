package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.model.*;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.*;
import java.util.stream.Collectors;

public class InvestmentUebersichtController implements ControlledScreen {

    private ScreenController screenController;
    private List<Unternehmen> unternehmenListe = UnternehmenRepository.getInstanz().findAllPlanspielUnternehmen();
    //private UnternehmenRepository unternehmenRepo;

    @FXML
    private ComboBox<Periode> cbPeriodenAuswahl;

    @FXML
    private TableView<TabellenInhalt> tvInvestmentUebersicht;
    private List<TableColumn<TabellenInhalt, String>> tabellenZeilen = new ArrayList<>();

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    public void initialize() {
        cbPeriodenAuswahl.setItems(FXCollections.observableArrayList(new ArrayList<Periode>(PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()))));
        cbPeriodenAuswahl.setValue(PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getInstanz().getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new)); //setze aktuelle Periode
        cbPeriodenAuswahl.setConverter(new ConverterHelper().getPeriodenConverter());


        tvInvestmentUebersicht.setEditable(true);
        //tabellenZeilen.add(new TableColumn<TabellenInhalt, String>());

        for (Unternehmen u : unternehmenListe) {
            tvInvestmentUebersicht.getColumns().addAll(new TableColumn(u.getName()));
        }


    }

    private Map<Long, Double> getInvestmentsOfAUnternehmenInEachUnternehmenByUnternehmenAndPeriode(long unternehmensId, long periodenId) {
        class Tupel {
            private long unternehmensId;
            private double investitionssumme;

            public Tupel(long unternehmensId, double investitionssumme) {
                this.unternehmensId = unternehmensId;
                this.investitionssumme = investitionssumme;
            }

            public long getUnternehmensId() {
                return unternehmensId;
            }

            public double getInvestitionssumme() {
                return investitionssumme;
            }            public void setUnternehmensId(long unternehmensId) {
                this.unternehmensId = unternehmensId;
            }

            public void setInvestitionssumme(double investitionssumme) {
                this.investitionssumme = investitionssumme;
            }


        }
        List<Wertpapier> wertpapiereDesUnternehmens = WertpapierRepository.getInstanz().findAll().stream()
                .filter(w -> w.getUnternehmen().getId() == unternehmensId)
                .filter(w -> w.getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_FESTGELD)
                .filter(w -> w.getWertpapierArt().getId() != WertpapierArt.WERTPAPIER_STARTKAPITAL)
                .collect(Collectors.toList());
        List<Buchung> buchungenDerWertpapiereDesUnternehmens = new ArrayList<>();
        for (Wertpapier w : wertpapiereDesUnternehmens) {
            buchungenDerWertpapiereDesUnternehmens.addAll(BuchungRepository.getInstanz().findAll().stream().filter(b -> b.getWertpapier().getId() == w.getId()).collect(Collectors.toList()));
        }
        List<Tupel> tupelList = new ArrayList<>();
        for (Buchung b : buchungenDerWertpapiereDesUnternehmens) {
            tupelList.add(new Tupel(b.getBenutzer().getUnternehmen().getId(),
                    (b.getStueckzahl() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, b.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs())));

        }
        return tupelList.stream().collect(Collectors.groupingBy(Tupel::getUnternehmensId, Collectors.summingDouble(Tupel::getInvestitionssumme)));


    }

    // Hole Positionen eines Unternehmens in einer Periode
    private Map<Long, Double> getSummeDerInvestitionenByUnternehmen(long unternehmensId, long periodenId) {
        // Anonyme Klasse, um Investitionen in die Unternehmen zu speichern
        class InvestitionenTupel {
            private long unternehmensId;
            private double wertDerWertpapiere;

            InvestitionenTupel(long unternehmensId, double wertDerWertpapiere) {
                this.unternehmensId = unternehmensId;
                this.wertDerWertpapiere = wertDerWertpapiere;
            }

            public long getUnternehmensId() {
                return unternehmensId;
            }

            public double getWertDerWertpapiere() {
                return wertDerWertpapiere;
            }            public void setUnternehmensId(long unternehmensId) {
                this.unternehmensId = unternehmensId;
            }

            public void setWertDerWertpapiere(double wertDerWertpapiere) {
                this.wertDerWertpapiere = wertDerWertpapiere;
            }


        }


        //Periode aktuellePeriode = PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()).stream().reduce((first, second) -> second).orElseThrow(NoSuchElementException::new);
        //Periode aktuellePeriode = PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()).stream().max(Comparator.comparing(Periode::getId)).orElseThrow(NoSuchElementException::new);


        List<Portfolioposition> aktienDerTeilnehmerDesUnternehmens = new ArrayList<>();
        List<Portfolioposition> anleihenDerTeilnehmerDesUnternehmens = new ArrayList<>();
        List<Portfolioposition> etfDerTeilnehmerDesUnternehmens = new ArrayList<>();

        List<Benutzer> benutzerDesUnternehmen = BenutzerRepository.getInstanz().findAllBenutzerByUnternehmen(unternehmensId);
        PortfolioFassade portfolioFassade = PortfolioFassade.getInstanz();


        for (Benutzer t : benutzerDesUnternehmen) {
            aktienDerTeilnehmerDesUnternehmens.addAll(portfolioFassade.getAktienPositionen(t.getId(), periodenId)); // Hole die Aktien in dieser Periode von diesem Benutzer
            anleihenDerTeilnehmerDesUnternehmens.addAll(portfolioFassade.getAnleihePositionen(t.getId(), periodenId)); // Hole die Anleihen in dieser Periode von diesem Benutzer
            etfDerTeilnehmerDesUnternehmens.addAll(portfolioFassade.getEtfPositionen(t.getId(), periodenId)); // Hole die ETFs in dieser Periode von diesem Benutzer
        }

        List<InvestitionenTupel> investitionenTupelListUnsorted = new ArrayList<>();

        // Speichere von jeder Position die UnternehmensID des Wertpapiers und die Investitionshöhe in eine Liste
        for (Portfolioposition p : aktienDerTeilnehmerDesUnternehmens) {
            investitionenTupelListUnsorted.add(
                    new InvestitionenTupel(p.getWertpapier().getUnternehmen().getId(), (p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs())));
        }
        for (Portfolioposition p : anleihenDerTeilnehmerDesUnternehmens) {
            investitionenTupelListUnsorted.add(
                    new InvestitionenTupel(p.getWertpapier().getUnternehmen().getId(), (p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs())));
        }
        for (Portfolioposition p : etfDerTeilnehmerDesUnternehmens) {
            investitionenTupelListUnsorted.add(
                    new InvestitionenTupel(p.getWertpapier().getUnternehmen().getId(), (p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).orElseThrow(NoSuchElementException::new).getKurs())));
        }

        // Aggregiere die Invesititionen für jedes Unternehmen
        return investitionenTupelListUnsorted.stream().collect(Collectors.groupingBy(InvestitionenTupel::getUnternehmensId, Collectors.summingDouble(InvestitionenTupel::getWertDerWertpapiere)));


    }

    //TODO: was ist mit den Unternehmen, die keine Investition bekommen haben (niemand hat was von denen gekauft)

    class TabellenInhalt {
        SimpleStringProperty unternehmen;


    }


}
