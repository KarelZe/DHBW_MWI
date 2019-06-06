package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

public class PortfoliopositionFactory {

    Portfolioposition getPortfolioposition(Wertpapier wertpapier) {


        if (wertpapier.getWertpapierArt().equals(WertpapierArt.WERTPAPIER_AKTIE))
            return new Aktie();
        else if (wertpapier.getWertpapierArt().equals(WertpapierArt.WERTPAPIER_ANLEIHE))
            return new FloatingRateNote();
        else if (wertpapier.getWertpapierArt().equals(WertpapierArt.WERTPAPIER_ETF))
            return new ExchangeTradedFund();
        else
            return null;
    }
}
