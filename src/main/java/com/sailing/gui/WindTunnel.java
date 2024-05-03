package com.sailing.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

/**
 *
 */
class WindTunnel extends Pane {

    private final SailboatGUI sailboat;

    WindTunnel(SailboatGUI boat) {
        this.sailboat = boat;
        setStyle("-fx-background-color: grey;");

        // styling
        getStylesheets().add("/styling.css");
        setWidth(Sailing.WIDTH);
        setHeight(Sailing.HEIGHT);

        getChildren().addAll(sailboat);

       setOnKeyPressed(event -> {
           switch (event.getCode()) {
               case LEFT:
                   sailboat.steer(-1);
                   break;
               case RIGHT:
                   sailboat.steer(1);
                   break;
               case UP:
                   sailboat.getSail().heaveSail(-1);
                   break;
               case DOWN:
                   sailboat.getSail().heaveSail(1);
                   break;
           }
       });
    }

    public SailboatGUI getSailboat() {
        return sailboat;
    }
}
