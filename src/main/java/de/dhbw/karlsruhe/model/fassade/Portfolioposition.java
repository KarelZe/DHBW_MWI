package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Wrapper um Wertapapierklasse zum vereinfachten Handling von Wertpapier-Objekten für den Handel von Wertpapieren.
 * Eine Verwendung ist insbesondere in Verbindung mit {@link PortfolioFassade} sinnvoll.
 *
 * @author Markus Bilz
 */
public class Portfolioposition {

    Wertpapier wertpapier;
    double bezugsgroesse;

    /**
     * Kontruktor für die Erzeugung einer Portfolioposition.
     * @param wertpapier Wertpapier der Portfolioposition
     * @param bezugsgroesse Stückzahl oder Nennwert der Wertpapierposition
     */
    public Portfolioposition(Wertpapier wertpapier, double bezugsgroesse) {
        this.wertpapier = wertpapier;
        this.bezugsgroesse = bezugsgroesse;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public double getBezugsgroesse() {
        return bezugsgroesse;
    }

    public void setBezugsgroesse(double bezugsgroesse) {
        this.bezugsgroesse = bezugsgroesse;
    }

    @Override
    public String toString() {
        return "Portfolioposition{" +
                "wertpapier=" + wertpapier +
                ", bezugsgroesse=" + bezugsgroesse +
                '}';
    }
}
