package de.dhbw.karlsruhe.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

/**
 * Diese Klasse ist Teil eines Frameworks zum Laden und Entladen von Screens.
 * Das Framework folgt dabei der Idee, dass Screens wiederverwendet werden können und
 * damit eine Neuerzeugung bei Wechsel eines Screens entfällt. Die Implementierung ist adaptiert von:
 * @see <a href="http://youtube.com">https://www.youtube.com/watch?v=5GsdaZWDcdY</a>
 * @author Markus Bilz, Christian Fix
 */

public class ScreenController extends StackPane {
    static public PeriodenDetailController myPeriodeControllerHandle; //Hierüber kann der Controller von dem Detailscreen der Perioden erreicht werden.
    static public PrintController myPrintControllerHandle;

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
     * Methode zum Laden eines Screens.
     * @param name Fachlicher Name
     * @param resource Technischer Name der FXML-Datei
     */
    void loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            Parent loadScreen = loader.load();
            ControlledScreen controlledScreen = loader.getController();

            switch (name) { //Check, ob es sich um die Detail oder Historie Szene handelt. Wenn ja, dann wir der Controller in der public static Variable instanziert
                case "perioden_detail":
                    myPeriodeControllerHandle = loader.getController();
                    break;
                case "teilnehmer_drucken":
                    myPrintControllerHandle = loader.getController();
                    break;
            }

            controlledScreen.setScreenParent(this);
            addScreen(name, loadScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Setzen des ausgewählten Screens auf die Stage.
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
     * Methode zum Entladen eines Screens.
     * @param name Fachlicher Name
     */
    void unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Keine Screens zum Entfernen vorhanden.");
        }

    }

}
