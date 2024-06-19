package com.sailing.gui;

import com.sailing.Sailboat;
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

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new WindTunnel(new SailboatGUI(new Sailboat(), new SailboatGUI.SailGUI())), WIDTH, HEIGHT);
        scene.getRoot().requestFocus();
        stage.setTitle("Sailing Simulation");
        stage.setFullScreen(false);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
