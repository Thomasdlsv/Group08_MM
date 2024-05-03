package com.sailing.gui;

import javafx.scene.image.Image;

abstract class Images {

    private static final double SCALE = 0.2;

    static final Image boat = new Image("/boat.png", Sailing.WIDTH * SCALE, Sailing.HEIGHT * SCALE, true, false);
    static final Image sail = new Image("/sail.png", Sailing.WIDTH * SCALE, Sailing.HEIGHT * SCALE, true, false);

}
