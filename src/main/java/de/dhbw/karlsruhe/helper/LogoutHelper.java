package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.controller.ScreensFramework;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;

public class LogoutHelper {

    /**
     * Loggt den derzeitigen Anwender aus und leitet ihn auf den Login-Screen weiter
     * @param screenController
     */
    public static void logout(ScreenController screenController) {
        AktuelleSpieldaten.setTeilnehmer(null);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }
}
