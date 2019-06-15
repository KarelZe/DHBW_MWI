package de.dhbw.karlsruhe.controller;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.TeilnehmerRepository;
import de.dhbw.karlsruhe.model.UnternehmenRepository;
import de.dhbw.karlsruhe.model.fassade.PortfolioFassade;
import de.dhbw.karlsruhe.model.fassade.Portfolioposition;
import de.dhbw.karlsruhe.model.jpa.Teilnehmer;
import de.dhbw.karlsruhe.model.jpa.Unternehmen;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvestmentUebersichtController implements ControlledScreen {

    private ScreenController screenController;
    private List<Unternehmen> unternehmenListe;
    //private UnternehmenRepository unternehmenRepo;

    @FXML
    private TableView<Object> tvInvestmentUebersicht;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    public void initialize(){
        System.out.println("Init");

        tvInvestmentUebersicht.setEditable(true);

        unternehmenListe = UnternehmenRepository.getInstanz().findAll();
        for (int i = 0; i < unternehmenListe.size(); i++) {
            tvInvestmentUebersicht.getColumns().addAll(new TableColumn(unternehmenListe.get(i).getName()));
        }

        //TODO test durchführen
        /*System.out.println(

                Arrays.toString(getSummeDerInvestitionenByUnternehmen(76, 3).entrySet().toArray())

        );
        System.out.println("Bla");*/

    }

    // Hole Positionen eines Unternehmens in einer Periode
    public Map<Long, Double> getSummeDerInvestitionenByUnternehmen(long unternehmensId, long periodenId) {
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

            public void setUnternehmensId(long unternehmensId) {
                this.unternehmensId = unternehmensId;
            }

            public double getWertDerWertpapiere() {
                return wertDerWertpapiere;
            }

            public void setWertDerWertpapiere(double wertDerWertpapiere) {
                this.wertDerWertpapiere = wertDerWertpapiere;
            }
        }


        List<Portfolioposition> aktienDerTeilnehmerDesUnternehmens = new ArrayList<>();
        List<Portfolioposition> anleihenDerTeilnehmerDesUnternehmens = new ArrayList<>();
        List<Portfolioposition> etfDerTeilnehmerDesUnternehmens = new ArrayList<>();

        List<Teilnehmer> teilnehmerDesUnternehmens = TeilnehmerRepository.getInstanz().findAllTeilnehmerbyUnternehmen(unternehmensId);
        PortfolioFassade portfolioFassade = new PortfolioFassade();
        //TODO perioden werden nicht unbedingt sequenziell angelegt, d.h. wir müssen perioden verketten und im periodenrepository die aktuellste periode anfordern können

        // Periode aktuellePeriode = PeriodenRepository.getInstanz().findAllBySpieleId(AktuelleSpieldaten.getSpiel().getId()).stream().reduce((first, second) -> second).get(); // Hole alle Perioden dieses Spiels und gebe die letzte zurück

        for (Teilnehmer t : teilnehmerDesUnternehmens) {
            aktienDerTeilnehmerDesUnternehmens.addAll(portfolioFassade.getAktienPositionen(t.getId(), periodenId)); // Hole die Aktien in dieser Periode von diesem Teilnehmer
            anleihenDerTeilnehmerDesUnternehmens.addAll(portfolioFassade.getAnleihePositionen(t.getId(), periodenId)); // Hole die Anleihen in dieser Periode von diesem Teilnehmer
            etfDerTeilnehmerDesUnternehmens.addAll(portfolioFassade.getEtfPositionen(t.getId(), periodenId)); // Hole die ETFs in dieser Periode von diesem Teilnehmer
        }


        List<InvestitionenTupel> investitionenTupelListUnsorted = new ArrayList<>();

        // Speichere von jeder Position die UnternehmensID des Wertpapiers und die Investitionshöhe in eine Liste
        for (Portfolioposition p : aktienDerTeilnehmerDesUnternehmens) {
            investitionenTupelListUnsorted.add(
                    new InvestitionenTupel(p.getWertpapier().getUnternehmen().getId(), (p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).get().getKurs())));
        }
        for (Portfolioposition p : anleihenDerTeilnehmerDesUnternehmens) {
            investitionenTupelListUnsorted.add(
                    new InvestitionenTupel(p.getWertpapier().getUnternehmen().getId(), (p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).get().getKurs())));
        }
        for (Portfolioposition p : etfDerTeilnehmerDesUnternehmens) {
            investitionenTupelListUnsorted.add(
                    new InvestitionenTupel(p.getWertpapier().getUnternehmen().getId(), (p.getBezugsgroesse() * KursRepository.getInstanz().findByPeriodenIdAndWertpapierId(periodenId, p.getWertpapier().getId()).get().getKurs())));
        }

        // Aggregiere die Invesititionen für jedes Unternehmen
        return investitionenTupelListUnsorted.stream().collect(Collectors.groupingBy(InvestitionenTupel::getUnternehmensId, Collectors.summingDouble(InvestitionenTupel::getWertDerWertpapiere)));


    }


}