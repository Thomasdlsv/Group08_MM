package com.sailing;

import com.sailing.math.StateSystem;

public class Sail {

    StateSystem currentState;

    public Sail(StateSystem currentState) {
        this.currentState = currentState;
    }

    public StateSystem getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateSystem currentState) {
        this.currentState = currentState;
    }

    public double getAngle() {
        return currentState.getPosition().getValue(3);
    }
}
