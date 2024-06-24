package com.sailing.gui;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Stats extends Legend {

    private Text time, boatVelocity, boatPosition, appWindVelocity, appWindAngle, angleOfAttack, cog;
    private Group windStats, boatStats;

    private Vector2D pos;
    private StateSystem stateSystem;

    public Stats(Vector2D pos, String dragPos, String statType, StateSystem stateSystem) {
        super(pos, dragPos);
        this.pos = pos;
        windStats = new Group();
        appWindVelocity = new Text();
        appWindAngle = new Text();
        time = new Text();
        boatVelocity = new Text();
        boatPosition = new Text();
        angleOfAttack = new Text();
        cog = new Text();
        update(stateSystem);
        if (statType.equals("wind")) {
            formatWindStats();
            getChildren().addAll(windStats);
        } else if (statType.equals("boat")) {
            boatStats = new Group();
            //formatBoatStats();
            getChildren().addAll(boatStats);
        }

    }

    private void formatWindStats() {
        windStats.setLayoutX(pos.getX1());
        windStats.setLayoutY(pos.getX2());

        Text wind = new Text("Wind");
        wind.setTranslateX(85);
        wind.setTranslateY(80);
        wind.setStroke(Color.BLUE);
        wind.setFill(Color.GREY);
        wind.setStyle("-fx-font-size: 40pt;");

        Text appWindVelLabel = new Text("App Wind");
        appWindVelLabel.setTranslateX(50);
        appWindVelLabel.setTranslateY(250);

        Text vel = new Text("Vel");
        vel.setTranslateX(appWindVelLabel.getTranslateX() + 30);
        vel.setTranslateY(appWindVelLabel.getTranslateY() + 15);


        Text appWindAngleLabel = new Text("App Wind");
        appWindAngleLabel.setTranslateX(170);
        appWindAngleLabel.setTranslateY(250);

        Text ang = new Text("Angle");
        ang.setTranslateX(appWindAngleLabel.getTranslateX() + 15);
        ang.setTranslateY(appWindAngleLabel.getTranslateY() + 15);

        windStats.getChildren().addAll(wind, appWindVelLabel, vel, appWindAngleLabel, ang, appWindVelocity,  appWindAngle);

        System.out.println(appWindVelocity.getLayoutBounds().getWidth());
        appWindVelocity.setTranslateX(vel.getTranslateX() - 10 - appWindVelocity.getBoundsInLocal().getWidth() / 2);
        appWindVelocity.setTranslateY(vel.getTranslateY() + 40);
        appWindVelocity.setStyle("-fx-font-size: 30pt;");

        appWindAngle.setTranslateX(ang.getTranslateX() + 20 - appWindAngle.getBoundsInLocal().getWidth() / 2);
        appWindAngle.setTranslateY(ang.getTranslateY() + 40);
        appWindAngle.setStyle("-fx-font-size: 30pt;");


    }

    public void update(StateSystem state) {
        setTime(state.getTime());
        setBoatVelocity(new Vector(state.getVelocity().getValue(2), state.getVelocity().getValue(3)).getLength());
        setBoatPosition(state.getPosition().getValue(0), state.getPosition().getValue(1));
        setApparentWindVelocity(state.getWindVelocity().getLength());
        setApparentWindAngle(state.getApparentWindAngle());
    }

    private void setApparentWindVelocity(double apparentWindVelocity) {
        this.appWindVelocity.setText(String.format("%.2f", apparentWindVelocity));
    }

    private void setApparentWindAngle(int apparentWindAngle) {
        this.appWindAngle.setText(String.format("%d", apparentWindAngle));
    }

    public void setTime(double t) {
        String minutes = String.valueOf((int) t / 60);
        String seconds = String.valueOf((int) t % 60);
        time.setText("Time: " + minutes + "m " + seconds + "s");
    }

    public void setBoatVelocity(double v) {
        boatVelocity.setText(String.format("Velocity: %.2f m/s", v));
    }

    public void setBoatPosition(double x, double y) {
        boatPosition.setText(String.format("Position: %.2f, %.2f", x, y));
    }
}