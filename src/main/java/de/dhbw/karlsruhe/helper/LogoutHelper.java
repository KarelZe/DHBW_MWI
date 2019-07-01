package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.controller.ScreensFramework;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;

/**
 * Diese Klasse stellt Bequemlichkeitsmethoden für den Logout zur Verfügung.
 *
 * @author Christian Fix
 */

public class LogoutHelper {

    /**
     * Methode zum Logout des aktuellen Teilnehmers und Weiterleitung auf den Startbildschirm.
     * @param screenController screenController des aufrufenden Screens
     */
    public static void logout(ScreenController screenController) {
        AktuelleSpieldaten.getInstanz().setBenutzer(null);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }
}
