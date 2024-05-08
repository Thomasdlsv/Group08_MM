package com.sailing;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;

public class Sailboat {

    StateSystem currentState;

    public Sailboat(StateSystem currentState) {
        this.currentState = currentState;
    }

    public Sailboat() {
        this.currentState = new StateSystem(new Vector(0, 0, 0, 0), new Vector(0, 0, 0, 0), new Vector(0, 0), 0);
    }

    public StateSystem getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateSystem currentState) {
        this.currentState = currentState;
    }

    public Vector2D getPosition() {
        return new Vector2D(currentState.getPosition().getValue(0), currentState.getPosition().getValue(1));
    }

    public double getAngle() {
        return currentState.getPosition().getValue(2);
    }

    public Vector2D getVelocity() {
        return new Vector2D(currentState.getVelocity().getValue(0), currentState.getVelocity().getValue(1));
    }

}
