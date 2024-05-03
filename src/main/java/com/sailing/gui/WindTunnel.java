package com.sailing.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

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

       Scale s = new Scale();
       s.setPivotX(Sailing.X_CENTER);
       s.setPivotY(Sailing.Y_CENTER);
       sailboat.getTransforms().add(s);

       setOnScroll(event -> {
           s.setX(s.getX() + event.getDeltaY() / 500);
           s.setY(s.getY() + event.getDeltaY() / 500);
       });
    }

    public SailboatGUI getSailboat() {
        return sailboat;
    }
}
