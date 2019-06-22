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

    void loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            Parent loadScreen = loader.load();
            ControlledScreen controlledScreen = loader.getController();
            controlledScreen.setScreenParent(this);
            addScreen(name, loadScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    void unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Keine Screens zum Entfernen vorhanden.");
        }

    }

}
