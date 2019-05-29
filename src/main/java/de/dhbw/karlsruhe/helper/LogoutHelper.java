package de.dhbw.karlsruhe.helper;

import de.dhbw.karlsruhe.controller.ScreenController;
import de.dhbw.karlsruhe.controller.ScreensFramework;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;

public class LogoutHelper {

    public static void logout(ScreenController screenController) {
        AktuelleSpieldaten.setTeilnehmer(null);
        screenController.setScreen(ScreensFramework.SCREEN_LOGIN);
    }
}
