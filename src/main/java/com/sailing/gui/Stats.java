package com.sailing.gui;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Stats extends Legend {


    private final LabelArrow windVelocityArrow = new LabelArrow(new Arrow(150, 100, 100, 90, Color.ALICEBLUE), "v", "tw");
    private final LabelArrow apparentWindArrow = new LabelArrow(new Arrow(150, 100, 100, 90, Color.AQUA), "v", "aw");
    private Text time, sog, boatPosition, appWindVelocity, appWindAngle, angleOfAttack, cog;
    private Group windStats, boatStats;

    private Vector2D pos;
    private StateSystem stateSystem;
    private SailboatGUI boat;


    public Stats(Vector2D pos, String dragPos, String statType, StateSystem stateSystem, SailboatGUI boat) {
        super(pos, dragPos);
        this.pos = pos;
        this.boat = boat;
        appWindVelocity = new Text();
        appWindAngle = new Text();
        time = new Text();
        sog = new Text();
        boatPosition = new Text();
        angleOfAttack = new Text();
        cog = new Text();
        windStats = new Group();
        boatStats = new Group();
        update(stateSystem);
        if (statType.equals("wind")) {
            formatWindStats();
            getChildren().addAll(windStats);
            getChildren().addAll(windVelocityArrow, apparentWindArrow);
        } else if (statType.equals("boat")) {
            formatBoatStats();
            getChildren().addAll(boatStats);
        }

    }

    private void formatBoatStats() {
        boatStats.setLayoutX(pos.getX1());
        boatStats.setLayoutY(pos.getX2());

        Text boat = new Text("Boat");
        boat.setTranslateX(1400);
        boat.setTranslateY(80);
        boat.setStyle("-fx-font-size: 40pt;");

        Text speedOverGround = new Text("SOG");
        speedOverGround.setTranslateX(boat.getTranslateX() - 30);
        speedOverGround.setTranslateY(boat.getTranslateY() + 50);
        speedOverGround.setStyle("-fx-font-size: 20pt;");

        System.out.println(sog.getBoundsInLocal().getWidth());
        sog.setTranslateX(speedOverGround.getTranslateX()  - sog.getBoundsInLocal().getWidth() / 2);
        sog.setTranslateY(speedOverGround.getTranslateY() + 30);
        sog.setStyle("-fx-font-size: 30pt;");


        Text courseOverGround = new Text("COG");
        courseOverGround.setTranslateX(boat.getTranslateX() + 90);
        courseOverGround.setTranslateY(boat.getTranslateY() + 50);
        courseOverGround.setStyle("-fx-font-size: 18pt;");

        cog.setTranslateX(courseOverGround.getTranslateX() + 20);
        cog.setTranslateY(courseOverGround.getTranslateY() + 30);
        cog.setStyle("-fx-font-size: 30pt;");


        Text position = new Text("Position");
        position.setTranslateX(boat.getTranslateX() - 50);
        position.setTranslateY(boat.getTranslateY() + 150);
        position.setStyle("-fx-font-size: 18pt;");


        boatPosition.setTranslateX(position.getTranslateX() );
        boatPosition.setTranslateY(position.getTranslateY() + 30);
        boatPosition.setStyle("-fx-font-size: 30pt;");


        Text aoa = new Text("AOA");
        aoa.setTranslateX(boat.getTranslateX() + 90);
        aoa.setTranslateY(boat.getTranslateY() + 150);
        aoa.setStyle("-fx-font-size: 18pt;");

        angleOfAttack.setTranslateX(aoa.getTranslateX() + 10);
        angleOfAttack.setTranslateY(aoa.getTranslateY() + 30);
        angleOfAttack.setStyle("-fx-font-size: 30pt;");



        getChildren().addAll(boat, speedOverGround, courseOverGround, sog, cog, position, aoa, boatPosition, angleOfAttack);
    }

    private void formatWindStats() {
        windStats.setLayoutX(pos.getX1());
        windStats.setLayoutY(pos.getX2());

        Text wind = new Text("Wind");
        wind.setTranslateX(85);
        wind.setTranslateY(80);
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

        appWindVelocity.setTranslateX(vel.getTranslateX() - appWindVelocity.getBoundsInLocal().getWidth() / 2);
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
        setApparentWindVelocity(state.getWindVelocity().subtract(state.getBoatVelocity()).getLength());
        setApparentWindAngle(state.getApparentWindAngle());
        setCourseOverGround((int) boat.getRotationAngle());
        setAngleOfAttack(state);

        cog.setTranslateX(boatStats.getLayoutX() + 210 - cog.getBoundsInLocal().getWidth() / 2);
        sog.setTranslateX(boatStats.getLayoutX() + 90 - sog.getBoundsInLocal().getWidth() / 2);
        appWindVelocity.setTranslateX(windStats.getLayoutX() + 90 - appWindVelocity.getBoundsInLocal().getWidth() / 2);
        appWindAngle.setTranslateX(windStats.getLayoutX() + 210 - appWindAngle.getBoundsInLocal().getWidth() / 2);
        angleOfAttack.setTranslateX(boatStats.getLayoutX() + 215 - angleOfAttack.getBoundsInLocal().getWidth() / 2);


        drawVelocityArrows(state);
    }

    private void drawVelocityArrows(StateSystem currentState) {
        Vector2D windVector = new Vector2D(currentState.getVelocity().getValue(0), currentState.getVelocity().getValue(1));
        Vector2D boatVector = new Vector2D(currentState.getVelocity().getValue(2), currentState.getVelocity().getValue(3));
        Vector2D apparentWindVector = windVector.subtract(boatVector);

        double scalar = 9;

        windVelocityArrow.setLengthAndAngle(windVector.getLength() * scalar, windVector.toPolar().getX2());
        apparentWindArrow.setLengthAndAngle(apparentWindVector.getLength() * scalar, apparentWindVector.toPolar().getX2());
    }

    private void setApparentWindVelocity(double apparentWindVelocity) {
        this.appWindVelocity.setText(String.format("%.2f", apparentWindVelocity));
    }

    private void setAngleOfAttack(StateSystem state) {
         double sailAngle = boat.getSailAngleToNorth();
         int apparentWindAngle = state.getApparentWindAngle();

         int res = (int) Math.abs(sailAngle - apparentWindAngle);
         if (res > 90) {
             apparentWindAngle = (apparentWindAngle + 180) % 360;
             res = (int) Math.abs(sailAngle - apparentWindAngle);
             if (res > 90) {
                 res = Math.abs(res - 180);
             }
         }

         this.angleOfAttack.setText(String.format("%d", res));


    }

    private void setApparentWindAngle(int apparentWindAngle) {
        this.appWindAngle.setText(String.format("%d", apparentWindAngle));
    }

    public void setTime(double t) {
        String minutes = String.valueOf((int) t / 60);
        String seconds = String.valueOf((int) t % 60);
        time.setText("Time: " + minutes + "m " + seconds + "s");
    }

    private void setCourseOverGround(int courseOverGround) {
        this.cog.setText(String.format("%d", courseOverGround));
    }

    public void setBoatVelocity(double v) {
        sog.setText(String.format("%.2f", v));
    }

    public void setBoatPosition(double x, double y) {
        boatPosition.setText(String.format("%.2f,\n%.2f", x, y * -1));
    }
}