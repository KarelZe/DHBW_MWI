package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.jpa.Wertpapier;

/**
 * Wrapper um {@link Wertpapier} zum vereinfachten Handling von {@link Wertpapier}-Objekten für den Handel.
 *
 * <p>
 * Eine Verwendung ist insbesondere in Verbindung mit {@link PortfolioFassade} sinnvoll.
 * </p>
 *
 * @author Markus Bilz
 */
public class Portfolioposition {

    private Wertpapier wertpapier;
    private double bezugsgroesse;

    /**
     * Kontruktor für die Erzeugung einer {@link Portfolioposition}.
     *
     * @param wertpapier    Wertpapier der {@link Portfolioposition}
     * @param bezugsgroesse Stückzahl oder Nennwert der Wertpapierposition
     * @author Markus Bilz
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
