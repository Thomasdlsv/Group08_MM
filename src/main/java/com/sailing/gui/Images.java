package com.sailing.gui;

import javafx.scene.image.Image;

abstract class Images {

    public static double SCALE = 0.3;

    static final Image boat = new Image("/boat.png",
            Sailing.WIDTH * SCALE,
            Sailing.HEIGHT * SCALE,
            true, false);

    static final Image sail = new Image("/sail.png",
            Sailing.WIDTH * SCALE * 0.6,
            Sailing.HEIGHT * SCALE * 0.6,
            true, false);

    static final Image background = new Image("/background.png");

    static final Image paperTop = new Image("/paper_top.png");
    static final Image paperRight = new Image("/paper_right.png");
    static final Image paperBottom = new Image("/paper_bottom.png");
    static final Image paperLeft = new Image("/paper_left.png");


}
