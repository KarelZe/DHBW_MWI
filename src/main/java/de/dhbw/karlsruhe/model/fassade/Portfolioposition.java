package de.dhbw.karlsruhe.model.fassade;

import de.dhbw.karlsruhe.model.jpa.Wertpapier;

public class Portfolioposition {

    Wertpapier wertpapier;
    double bezugsgroesse;

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
