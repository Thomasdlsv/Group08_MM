package com.sailing.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

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
            getScene().setRoot(new WindTunnel());
        });

        Button parkour = new Button("Parkour 1");
        parkour.setPrefWidth(400);
        parkour.setPrefHeight(70);
        parkour.setTranslateX(WIDTH / 2 - 200);
        parkour.setTranslateY(HEIGHT / 2 );
        parkour.setOnAction(e -> {
            System.out.println("Parkour");
            getScene().setRoot(new Parkour());
        });

        getChildren().addAll(windTunnel, parkour);

    }

}
