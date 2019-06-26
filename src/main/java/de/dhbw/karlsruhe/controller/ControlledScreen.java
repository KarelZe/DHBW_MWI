package de.dhbw.karlsruhe.controller;

/**
 * Gemeinsames Interface für den Zugriff für den {@code ScreenController}.
 * Es ist Bestandteil des ScreensFrameworks.
 *
 * @author Markus Bilz
 */

public interface ControlledScreen {
    /**
     * Methode für den Zugriff auf den {@code ScreenController} des übergeordneten Screens.
     *
     * @param screenPage Controller des Screens
     */
    void setScreenParent(ScreenController screenPage);
}
