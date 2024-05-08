package com.sailing.math;

import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.ApparentWind;
import com.sailing.math.functions.DragFunction;
import com.sailing.math.functions.LiftFunction;

public class StateSystem {

    private Vector position; // x0 = x, x1 = y, x2 = angle in degrees
    private Vector velocity; // x0 = wind x, x1 = wind y, x2 = boat x, x3 = boat y
    private Vector acceleration; // x0 = x, x1 = y
    private Vector heading; // angle in degrees
    private ApparentWind apparentWind;

    private double mass;

    public StateSystem(Vector position, Vector velocity, Vector acceleration, double mass, Vector heading) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.heading = heading;
    }

    public Vector setcombinedPosition(Vector position,Vector heading){
        Vector comPosition = new Vector(position.getValue(0),position.getValue(1),heading.getValue(0));
        return comPosition;
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
        return new StateSystem(position.copy(), velocity.copy(), acceleration.copy(), mass,heading.copy());
    }
    public Vector getHeading() {
        return heading;
    }
    public double getHeadingValue() {
        return heading.getValue(0);
    }
    //calculates the heading of the boat based on the velocity of the boat and the  difference between previus and current X and Y position of the boat
    public Vector calculateHeading(Vector position, Vector velocity){
        double x = position.getValue(0);
        double y = position.getValue(1);
        double x1 = x + velocity.getValue(0);
        double y1 = y + velocity.getValue(1);
        double angle = Math.toDegrees(Math.atan2(y1 - y, x1 - x));
        Vector heading = new Vector(angle);
        return heading;
    }
    public Vector calculateForceOnSail(){
        LiftFunction liftFunction = new LiftFunction(); // Create an instance of LiftFunction
        Vector lift = liftFunction.eval(position, velocity, mass, 0,0);
        DragFunction dragFunction = new DragFunction(); // Create an instance of DragFunction
        Vector drag = dragFunction.eval(position, velocity, mass, 0,0); // Call the eval method on the instance
        Vector forceOnSail = new Vector(lift.getValue(0) - drag.getValue(0), lift.getValue(1) - drag.getValue(1));
        return forceOnSail;
    }
    public ApparentWind getApparentWind() {
        return apparentWind;
    }
    public void setApparentWind(ApparentWind apparentWind) {
        this.apparentWind = apparentWind;
    }
    public void calculateApparentWind(){
        apparentWind = new ApparentWind();
        double[] apparentWindValues = apparentWind.apparentWind(velocity.getValue(0), velocity.getValue(1), velocity.getValue(2), velocity.getValue(3));
        Vector apparentWind = new Vector(apparentWindValues[0], apparentWindValues[1], apparentWindValues[2]);
        setVelocity(apparentWind);
    }
}
