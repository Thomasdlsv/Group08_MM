package com.sailing.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This acts as an entry point to the entire application
 */
public class Sailing extends Application {

    public static final double WIDTH = 1600;
    public static final double HEIGHT = 900;

//    public static final double WIDTH = 1280;
//    public static final double HEIGHT = 800;

    public static final double X_CENTER = WIDTH / 2;
    public static final double Y_CENTER = HEIGHT / 2;

    public static Stage window;

    @Override
    public void start(Stage stage) {
        window = stage;
        Scene scene = new Scene(new Menu(), WIDTH, HEIGHT);
        window.setTitle("Sailing Simulation");
        window.setFullScreen(false);
        window.setWidth(WIDTH);
        window.setHeight(HEIGHT);
        window.setScene(scene);
        window.getScene().getRoot().requestFocus();
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
