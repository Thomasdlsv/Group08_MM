package com.sailing;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.functions.Function;
import com.sailing.math.functions.WindWaterForceAccelerationFunction;
import com.sailing.math.solver.Solver;

import java.util.ArrayList;

/**
 * Simulation class that runs the simulation of the boat and the wind forces.
 * The simulation is run by stepping through the simulation and updating the state of the system.
 */
public class Simulation {

    Solver solver;
    ArrayList<StateSystem> history;
    StateSystem currentState;
    double stepSize;
    Function f = new WindWaterForceAccelerationFunction();

    public static boolean LOG = false; // Set to true to log the state of the system at each step

    public Simulation(Solver solver, StateSystem initialState, double stepSize) {
        this.solver = solver;
        this.currentState = initialState;
        this.stepSize = stepSize;
        this.history = new ArrayList<>();
        history.add(currentState);
    }

    /**
     * Rotate the boat by a given angle. <br>
     * This is one of the two control inputs of the boat.
     * @param angle
     */
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

    public void setBoatAngle(double angle) {
        Vector currentPosition = currentState.getPosition();
        Vector position = new Vector(
                currentPosition.getValues().get(0),
                currentPosition.getValues().get(1),
                angle,
                currentPosition.getValues().get(3));
        currentState = new StateSystem(
                position,
                currentState.getVelocity(),
                currentState.getAcceleration(),
                currentState.getMass(),
                currentState.getTime());
        history.add(currentState);
    }

    /**
     * Rotate the sail by a given angle. <br>
     * This is one of the two control inputs of the boat.
     * @param angle
     */
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

    public void setWindVelocity(Vector2D windVelocity) {
        Vector currentVelocity = currentState.getVelocity();
        Vector velocity = new Vector(
                windVelocity.getX1(),
                windVelocity.getX2(),
                currentVelocity.getValues().get(2),
                currentVelocity.getValues().get(3));
        currentState = new StateSystem(
                currentState.getPosition(),
                velocity,
                currentState.getAcceleration(),
                currentState.getMass(),
                currentState.getTime());
        history.add(currentState);
    }

    /**
     * Step through the simulation by one step of the given step-size. <br>
     * The state of the system is updated and added to the history.
     */
    public void step() {
        currentState = solver.nextStep(
                f,
                currentState.getPosition(),
                currentState.getVelocity(),
                currentState.getMass(),
                stepSize,
                currentState.getTime());
        history.add(currentState);
        if (LOG) System.out.println(currentState);
    }

    public StateSystem getCurrentState() {
        return currentState;
    }

    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
            step();
        }
    }

    public void runUntil(double time) {
        while (currentState.getTime() < time) {
            step();
        }
    }
}
