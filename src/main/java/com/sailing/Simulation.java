package com.sailing;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.Function;
import com.sailing.math.functions.WindForceAccelerationFunction;
import com.sailing.math.solver.Solver;

import java.util.ArrayList;

public class Simulation {

    Solver solver;
    ArrayList<StateSystem> history;
    StateSystem currentState;
    double stepSize;
    Function f = new WindForceAccelerationFunction();

    public Simulation(Solver solver, StateSystem initialState, double stepSize) {
        this.solver = solver;
        this.currentState = initialState;
        this.stepSize = stepSize;
        this.history = new ArrayList<>();
        history.add(currentState);
    }

    public void rotateBoat(double angle) {
        Vector currentPosition = currentState.getPosition();
        Vector position = new Vector(
                currentPosition.getValues().get(0),
                currentPosition.getValues().get(1),
                (currentPosition.getValue(2) + angle + 360) % 360,
                currentPosition.getValues().get(3));
        currentState = new StateSystem(
                position,
                currentState.getVelocity(),
                currentState.getAcceleration(),
                currentState.getMass(),
                currentState.getTime());
        history.add(currentState);
    }

    public void rotateSail(double angle) {
        Vector currentPosition = currentState.getPosition();
        Vector position = new Vector(
                currentPosition.getValues().get(0),
                currentPosition.getValues().get(1),
                currentPosition.getValues().get(2),
                (currentPosition.getValue(3) + angle + 360) % 360);
        currentState = new StateSystem(
                position,
                currentState.getVelocity(),
                currentState.getAcceleration(),
                currentState.getMass(),
                currentState.getTime());
        history.add(currentState);
    }

    public void step() {
        currentState = solver.nextStep(
                f,
                currentState.getPosition(),
                currentState.getVelocity(),
                currentState.getMass(),
                stepSize,
                currentState.getTime());
        history.add(currentState);
        System.out.println(currentState);
    }

    public StateSystem getCurrentState() {
        return currentState;
    }

    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
            step();
        }
    }
}
