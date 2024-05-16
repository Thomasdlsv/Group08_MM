package com.sailing.gui;

import javafx.scene.image.Image;

abstract class Images {

    public static double SCALE = 0.4;

    static final Image boat = new Image("/boat.png",
            Sailing.WIDTH * SCALE,
            Sailing.HEIGHT * SCALE,
            true, false);

    static final Image sail = new Image("/sail.png",
            Sailing.WIDTH * SCALE * 0.6,
            Sailing.HEIGHT * SCALE * 0.6,
            true, false);

}
