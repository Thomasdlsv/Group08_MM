package com.sailing.gui;

import com.sailing.Sailboat;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;


class SailboatGUI extends Group {

    static class SailGUI extends Pane {

        private final Rotate r = new Rotate();

        private ImageView sailIV;

        SailGUI() {

            sailIV = new ImageView(Images.sail);

            getTransforms().add(r);
            getChildren().add(sailIV);
        }

        void rotate(double angle) {
            r.setAngle(angle);
        }

        void setPivotPoint(double x, double y) {
            r.setPivotX(x);
            r.setPivotY(y);
        }

        double getRotationAngle() {
            return r.getAngle();
        }

        ImageView getSailIV() {
            return sailIV;
        }

    }

    private final Rotate r = new Rotate();
    private Sailboat boat;
    private SailGUI sail;

    SailboatGUI(Sailboat boat, SailGUI sailGUI) {
        this.boat = boat;
        this.sail = sailGUI;

        r.setPivotX(Sailing.X_CENTER);
        r.setPivotY(Sailing.Y_CENTER);

        ImageView boatIV = new ImageView(Images.boat);
        boatIV.setLayoutX(Sailing.X_CENTER - boatIV.getBoundsInLocal().getWidth() / 2);
        boatIV.setLayoutY(Sailing.Y_CENTER - boatIV.getBoundsInLocal().getHeight() / 2);

        Line bearing = new Line(Sailing.X_CENTER, Sailing.WIDTH * 1.41 , Sailing.X_CENTER, -Sailing.WIDTH + 1.41);
        Line abeam = new Line(-Sailing.WIDTH * 1.41, Sailing.Y_CENTER, Sailing.WIDTH * 1.41, Sailing.Y_CENTER);

        sail.setLayoutX(Sailing.X_CENTER - sail.getBoundsInLocal().getWidth() / 2);
        sail.setLayoutY(Sailing.Y_CENTER - boatIV.getBoundsInLocal().getHeight() / 4);

        sail.setPivotPoint(sail.getBoundsInLocal().getWidth() / 2, 0);

        getTransforms().add(r);
        getChildren().addAll(boatIV, bearing, abeam, sail);
    }

    void rotate(double angle) {
        r.setAngle(angle + 90);
    }

    Sailboat getBoat() {
        return this.boat;
    }

    public SailGUI getSail() {
        return sail;
    }

    void steer(double angle) {
        rotate(r.getAngle() + angle);
    }

    double getRotationAngle() {
        return r.getAngle();
    }
}
