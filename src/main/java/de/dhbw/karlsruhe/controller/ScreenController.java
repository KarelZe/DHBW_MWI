package de.dhbw.karlsruhe.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

// FIXME: @ Bilz sauber dokumentieren

/**
 * Adaptiert von https://www.youtube.com/watch?v=5GsdaZWDcdY
 */

public class ScreenController extends StackPane {
    static public PeriodenDetailController myControllerHandle; //Hierüber kann der Controller von dem Detailscreen der Perioden erreicht werden.

    private HashMap<String, Node> screens = new HashMap<>();

    ScreenController() {
        super();
    }

    void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Lädt einen Screen
     * @param name Fachlicher Name
     * @param resource Technischer Name der FXML-Datei
     */
    void loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            Parent loadScreen = loader.load();
            ControlledScreen controlledScreen = loader.getController();
            if(name=="perioden_detail"){myControllerHandle = (PeriodenDetailController)loader.getController();}//Check ob es sich um die Detail Szene handelt. Wenn ja dann wir der Controller in der public static Variable myControllerHandle instanziierrt
            controlledScreen.setScreenParent(this);
            addScreen(name, loadScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setzt den angegebenen Screen auf die Stage.
     * @param name Fachlicher Name
     */
    public void setScreen(final String name) {
        if (screens.get(name) != null) {
            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
            } else {
                getChildren().add(screens.get(name));
            }
        } else {
            System.out.println("Keine FXML-Datei vorhanden.");
        }
    }

    /**
     * Entlädt einen Screen
     * @param name Fachlicher Name
     */
    void unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Keine Screens zum Entfernen vorhanden.");
        }

    }

}
