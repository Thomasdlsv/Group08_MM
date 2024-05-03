package com.sailing.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;


/**
 *
 */
class WindTunnel extends Pane {

    private SailboatGUI sailboat;

    WindTunnel(SailboatGUI sailboat) {
        this.sailboat = sailboat;
        // styling
        getStylesheets().add("/styling.css");
        setWidth(Sailing.WIDTH);
        setHeight(Sailing.HEIGHT);

        sailboat.rotate(15);
        getChildren().addAll(sailboat);
    }

}
