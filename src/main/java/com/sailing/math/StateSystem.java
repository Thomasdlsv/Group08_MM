package com.sailing.math;

import com.sailing.math.data_structures.Vector;

public class StateSystem {

    private Vector position; // x0 = x, x1 = y, x2 = angle in degrees
    private Vector velocity; // x0 = wind x, x1 = wind y, x2 = boat x, x3 = boat y
    private Vector acceleration; // x0 = x, x1 = y

    private double mass;

    public StateSystem(Vector position, Vector velocity, Vector acceleration, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
    }


    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public StateSystem copy() {
        return new StateSystem(position.copy(), velocity.copy(), acceleration.copy(), mass);
    }
}
