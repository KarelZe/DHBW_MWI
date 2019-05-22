package de.dhbw.karlsruhe.controller;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

// FIXME: @ Bilz Sauber dokumentieren
public class ScreensFramework extends Application {

    // Lege Namen und Pfade der einzelnen Scenes fest.
    static final String SCREEN_LOGIN = "login";
    static final String SCREEN_REGISTER = "register";
    private static final String SCREEN_LOGIN_FILE = "scene_login.fxml";
    private static final String SCREEN_REGISTER_FILE = "scene_register.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        // Füge Screens zum ScreensController hinzu
        ScreensController controller = new ScreensController();
        controller.loadScreen(ScreensFramework.SCREEN_LOGIN, ScreensFramework.SCREEN_LOGIN_FILE);
        controller.loadScreen(ScreensFramework.SCREEN_REGISTER, ScreensFramework.SCREEN_REGISTER_FILE);

        // Lege Screen fest, der als Erstes aufgerufen wird.
        controller.setScreen(ScreensFramework.SCREEN_LOGIN);
        Group root = new Group();
        root.getChildren().addAll(controller);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Anika");
        window.show();
    }


}
