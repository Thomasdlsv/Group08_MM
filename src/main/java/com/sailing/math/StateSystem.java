package com.sailing.math;

import com.sailing.math.data_structures.Vector;

public class StateSystem {

    private final Vector position; // x0 = x, x1 = y, x2 = angle in degrees boat, x3 = angle in degrees sail (relative to boat)
    private final Vector velocity; // x0 = wind x, x1 = wind y, x2 = boat x, x3 = boat y
    private final Vector acceleration; // x0 = x, x1 = y
    private final double mass;

    private final double time;

    public StateSystem(Vector position, Vector velocity, Vector acceleration, double mass) {
        this(position, velocity, acceleration, mass, 0.0);
    }

    public StateSystem(Vector position, Vector velocity, Vector acceleration, double mass, double time) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.time = time;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public double getTime() {
        return time;
    }

    public StateSystem copy() {
        return new StateSystem(position.copy(), velocity.copy(), acceleration.copy(), mass, time);
    }

    @Override
    public String toString() {
        return "StateSystem{" + "\n" +
                "\t position=" + position + ", \n" +
                "\t velocity=" + velocity + ", \n" +
                "\t acceleration=" + acceleration + ", \n" +
                "\t mass=" + mass +
                '}';
    }
}
