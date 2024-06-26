package com.sailing.gui;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.functions.WindDragFunction;
import com.sailing.math.functions.WindLiftFunction;
import com.sailing.math.functions.WindWaterForceAccelerationFunction;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public class Arrows extends Parent {

    private final SailboatGUI boat;

    private final LabelArrow accelerationArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.RED), "a", "");
    private final LabelArrow velocityArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.BLUE), "v", "");

    private final LabelArrow dragForceArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.GREEN), "F", "D");
    private final LabelArrow liftForceArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.YELLOW), "F", "L");
    private final LabelArrow windForceArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.ORANGE), "F", "D+L");

    public Arrows(SailboatGUI boat) {
        this.boat = boat;

        getChildren().addAll(
                accelerationArrow,
                dragForceArrow,
                liftForceArrow,
                velocityArrow,
                windForceArrow);
    }

    public void update(StateSystem currentState) {
        drawVelocityArrows(currentState);
        drawForceArrow(currentState);
    }


    private void drawForceArrow(StateSystem currentState) {
        Vector drag = new WindDragFunction().eval(currentState, 1);
        Vector lift = new WindLiftFunction().eval(currentState, 1);
        Vector windForce = new WindWaterForceAccelerationFunction().calculateAccelerationWind(currentState, 1);
        Vector2D dragForceVector = new Vector2D(drag.getValue(0), drag.getValue(1));
        Vector2D liftForceVector = new Vector2D(lift.getValue(0), lift.getValue(1));
        Vector2D windForceVector = new Vector2D(windForce.getValue(0), windForce.getValue(1));

        double xStart = (boat.getSail().localToScene(boat.getSail().getBoundsInLocal()).getCenterX());
        double yStart = (boat.getSail().localToScene(boat.getSail().getBoundsInLocal()).getCenterY());
        double scalar = 100 / currentState.getWindVelocity().getLength();

        double dragScalar = dragForceVector.getLength() * (1d/currentState.getMass());
        double liftScalar = liftForceVector.getLength() * (1d/currentState.getMass());
        double windScalar = windForceVector.getLength();

        dragForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                dragScalar * scalar,
                dragForceVector.toPolar().getX2());
        liftForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                liftScalar * scalar,
                liftForceVector.toPolar().getX2());
        windForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                windScalar * scalar,
                windForceVector.toPolar().getX2());
    }

    private void drawVelocityArrows(StateSystem currentState) {
        Vector2D boatVector = new Vector2D(currentState.getVelocity().getValue(2), currentState.getVelocity().getValue(3));
        Vector acceleration = new WindWaterForceAccelerationFunction().eval(currentState, 1);
        Vector2D accelerationVector = new Vector2D(acceleration.getValue(0), acceleration.getValue(1));
        double accelerationScalar = accelerationVector.getLength();
        double xStart = (boat.getBoatIV().localToScene(boat.getBoatIV().getBoundsInLocal()).getCenterX());
        double yStart = (boat.getBoatIV().localToScene(boat.getBoatIV().getBoundsInLocal()).getCenterY());

        double scalar = 9;

        velocityArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart), boatVector.getLength() * scalar, boatVector.toPolar().getX2());
        accelerationArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                accelerationScalar * scalar,
                accelerationVector.toPolar().getX2());
    }

}