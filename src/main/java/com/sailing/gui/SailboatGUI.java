package com.sailing.gui;

import com.sailing.Sailboat;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector2D;
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
            double startX = sailIV.getBoundsInLocal().getWidth() / 2;
            double endX = sailIV.getBoundsInLocal().getWidth() / 2;
            double startY = sailIV.getLayoutY();
            double endY = sailIV.getBoundsInLocal().getHeight();
            Line chord = new Line(startX, startY, endX, endY);
            getTransforms().add(r);
            getChildren().addAll(sailIV, chord);
        }

        void rotate(double angle) {
            r.setAngle(angle);
        }

        void setPivotPoint(double x, double y) {
            r.setPivotX(x);
            r.setPivotY(y);
        }

        double getRotationAngle() {
            return r.getAngle() % 360;
        }

        ImageView getSailIV() {
            return sailIV;
        }

    }

    private final Rotate r = new Rotate();
    private Sailboat boat;
    private SailGUI sail;
    private ImageView boatIV;

    SailboatGUI(Sailboat boat, SailGUI sailGUI) {
        this.boat = boat;
        this.sail = sailGUI;

        r.setPivotX(Sailing.X_CENTER);
        r.setPivotY(Sailing.Y_CENTER);

        boatIV = new ImageView(Images.boat);
        boatIV.setLayoutX(Sailing.X_CENTER - boatIV.getBoundsInLocal().getWidth() / 2);
        boatIV.setLayoutY(Sailing.Y_CENTER - boatIV.getBoundsInLocal().getHeight() / 2);

        // Line bearing = new Line(Sailing.X_CENTER, Sailing.WIDTH * 1.41 , Sailing.X_CENTER, -Sailing.WIDTH + 1.41);
        // bearing.setStroke(Color.LIGHTGRAY);
        // Line abeam = new Line(-Sailing.WIDTH * 1.41, Sailing.Y_CENTER, Sailing.WIDTH * 1.41, Sailing.Y_CENTER);
        // abeam.setStroke(Color.LIGHTGRAY);

        sail.setLayoutX(Sailing.X_CENTER - sail.getBoundsInLocal().getWidth() / 2);
        sail.setLayoutY(Sailing.Y_CENTER - boatIV.getBoundsInLocal().getHeight() / 4);

        sail.setPivotPoint(sail.getBoundsInLocal().getWidth() / 2, 0);

        getTransforms().add(r);
        getChildren().addAll(boatIV, sail);
    }

    void rotate(double angle) {
        r.setAngle(angle + 90);
    }

    public void setPosition(Vector2D position) {
        setTranslateX(position.getX1() * 10);
        setTranslateY(position.getX2() * 10);
    }

    public void repaintSail(StateSystem currentState) {
        Vector2D windVelocity = currentState.getWindVelocity();
        Vector2D boatVelocity = currentState.getBoatVelocity();
        Vector2D apparentWind = windVelocity.subtract(boatVelocity);
        double beta = Math.toDegrees(apparentWind.toPolar().getX2()) - ((currentState.getPosition().getValue(2) + currentState.getPosition().getValue(3)));
        beta = (beta + 360*3) % 360;

        if (beta > 0 && beta < 180) {
            getSail().getSailIV().setScaleX(1);
        } else {
            getSail().getSailIV().setScaleX(-1);
        }
    }

    public double getSailAngleToNorth() {

        return (getRotationAngle() + sail.getRotationAngle()) % 360;

    }

    Sailboat getBoat() {
        return this.boat;
    }

    public ImageView getBoatIV() {
        return boatIV;
    }

    public SailGUI getSail() {
        return sail;
    }

    void steer(double angle) {
        rotate(r.getAngle() + angle);
    }

    double getRotationAngle() {
        return r.getAngle() % 360.0;
    }
}
