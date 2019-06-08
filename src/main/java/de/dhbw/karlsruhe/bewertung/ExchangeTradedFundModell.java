package de.dhbw.karlsruhe.bewertung;

import de.dhbw.karlsruhe.model.KursRepository;
import de.dhbw.karlsruhe.model.jpa.Kurs;
import de.dhbw.karlsruhe.model.jpa.Periode;
import de.dhbw.karlsruhe.model.jpa.Wertpapier;
import de.dhbw.karlsruhe.model.jpa.WertpapierArt;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class ExchangeTradedFundModell implements Bewertungsmodell {
    /**
     * Diese Methode implementiert die Bewertung des Exchange Traded Funds (ETF) als arithmetisches Mittel der Aktienkurse
     * der Periode. Es wird dabei unterstellt, dass der ETF die Aktien seines Portfolios 1:1 repliziert.
     * Die Gesamtkostenquote (TER) werden im Bewertungsmodell mit 0 angenommen. Der TrackingError wird mit 0 angenommen.
     * Die Bewertung des ETFs ist erst möglich, nachdem die Aktien des Portfolios bewertet wurden.
     *
     * @param periode    Periode, für die eine Bewertung erfolgen soll.
     * @param wertpapier Wertpapier, das zu bewerten ist.
     * @return Kurs des ETFs
     */
    @Override
    public double bewerte(Periode periode, Wertpapier wertpapier) {
        List<Kurs> kurseInPeriode = KursRepository.getInstanz().findByPeriodenId(periode.getId());
        List<Kurs> kurseInIndex = kurseInPeriode.stream().filter(k -> k.getWertpapier().getWertpapierArt().getId() == WertpapierArt.WERTPAPIER_AKTIE).collect(Collectors.toList());
        OptionalDouble indexkurs = kurseInIndex.stream().mapToDouble(Kurs::getKurs).average();
        return indexkurs.orElse(100.00d);
    }
}
