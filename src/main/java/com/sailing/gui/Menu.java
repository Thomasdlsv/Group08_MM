package com.sailing.gui;

import com.sailing.math.data_structures.Vector2D;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

import java.util.List;

import static com.sailing.gui.Sailing.HEIGHT;
import static com.sailing.gui.Sailing.WIDTH;

public class Menu extends Pane {

    Menu() {
        setBackground(new Background(new BackgroundImage(Images.background, null, null, null, null)));

        getStylesheets().add("/styling.css");
        Button windTunnel = new Button("Training");
        windTunnel.setPrefWidth(400);
        windTunnel.setPrefHeight(70);
        windTunnel.setTranslateX(WIDTH / 2 - 200);
        windTunnel.setTranslateY(HEIGHT / 2 - 100);
        windTunnel.setOnAction(e -> {
            System.out.println("Wind Tunnel");
            Sailing.window.getScene().setRoot((new WindTunnel()));
            Sailing.window.getScene().getRoot().requestFocus();
        });

        Button parkour1 = new Button("Parkour 1");
        parkour1.setPrefWidth(400);
        parkour1.setPrefHeight(70);
        parkour1.setTranslateX(WIDTH / 2 - 200);
        parkour1.setTranslateY(HEIGHT / 2 );
        parkour1.setOnAction(e -> {
            System.out.println("Parkour");
            Sailing.window.getScene().setRoot(new Parkour(List.of(
                    new Vector2D(50, -25),
                    new Vector2D(20, 25),
                    new Vector2D(-20, -25),
                    new Vector2D(-50, 25))));
            Sailing.window.getScene().getRoot().requestFocus();
        });

        Button parkour2 = new Button("Parkour 2");
        parkour2.setPrefWidth(400);
        parkour2.setPrefHeight(70);
        parkour2.setTranslateX(WIDTH / 2 - 200);
        parkour2.setTranslateY(HEIGHT / 2 + 100);
        parkour2.setOnAction(e -> {
            System.out.println("Parkour");
            Sailing.window.getScene().setRoot(new Parkour(List.of(
                    new Vector2D(-50, 25),
                    new Vector2D(50, -25),
                    new Vector2D(20, 25),
                    new Vector2D(-20, -25))));
            Sailing.window.getScene().getRoot().requestFocus();
        });

        getChildren().addAll(windTunnel, parkour1, parkour2);

    }

}
