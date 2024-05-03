package com.sailing.gui;

import com.sailing.Sail;
import com.sailing.Sailboat;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

class SailboatGUI extends Group {

    static class SailGUI extends Node {

        private Rotate r = new Rotate();

        SailGUI() {

            ImageView sailIV = new ImageView(Images.sail);

        }

        void rotate(double angle) {
            r.setAngle(angle);
        }
    }

    private Rotate r = new Rotate();
    private Sailboat boat;

    SailboatGUI(Sailboat boat) {
        this.boat = boat;

        r.setPivotX(Sailing.X_CENTER);
        r.setPivotY(Sailing.Y_CENTER);

        ImageView boatIV = new ImageView(Images.boat);
        boatIV.setLayoutX(Sailing.X_CENTER - boatIV.getBoundsInLocal().getWidth() / 2);
        boatIV.setLayoutY(Sailing.Y_CENTER - boatIV.getBoundsInLocal().getHeight() / 2);

        Line bearing = new Line(Sailing.X_CENTER, Sailing.WIDTH + 50, Sailing.X_CENTER, -Sailing.WIDTH + 50);
        Line abeam = new Line(-Sailing.WIDTH + 50, Sailing.Y_CENTER, Sailing.WIDTH + 50, Sailing.Y_CENTER);

        getTransforms().add(r);
        getChildren().addAll(boatIV, bearing, abeam, new SailGUI());
    }

    void rotate(double angle) {
        r.setAngle(angle);
    }

    Sailboat getBoat() {
        return this.boat;
    }
}
