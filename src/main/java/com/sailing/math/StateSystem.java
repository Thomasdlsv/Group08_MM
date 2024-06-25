package com.sailing.math;

import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.functions.WaterDragFunction;
import com.sailing.math.functions.WindDragFunction;
import com.sailing.math.functions.WindLiftFunction;
import com.sailing.math.functions.WindWaterForceAccelerationFunction;

/**
 * StateSystem represents the state of the system at a given time.
 * It contains the position (boat), velocity (wind and boat), acceleration (boat) and mass (boat) of the system.
 */
public class StateSystem {

    private final Vector position;      // x0 = x, x1 = y, x2 = angle in degrees boat, x3 = angle in degrees sail (relative to boat)
    private final Vector velocity;      // x0 = wind x, x1 = wind y, x2 = boat x, x3 = boat y
    private final Vector acceleration;  // x0 = x, x1 = y
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

    public Vector2D getWindVelocity() {
        return new Vector2D(velocity.getValue(0), velocity.getValue(1));
    }

    public Vector2D getBoatVelocity() {
        return new Vector2D(velocity.getValue(2), velocity.getValue(3));
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

    public void log() {
        System.out.println("------");
        System.out.println("Wind-Drag:    " + new WindDragFunction().eval(this, 1));
        System.out.println("Wind-Lift:    " + new WindLiftFunction().eval(this, 1));
        System.out.println("Water-Drag:   " + new WaterDragFunction().eval(this, 1));
        System.out.println("Acceleration: " + new WindWaterForceAccelerationFunction().eval(this, 1));

        System.out.println("Wind speed:   " + getWindVelocity().getLength());
        System.out.println("Boat speed:   " + getBoatVelocity().getLength());
    }

    public int getApparentWindAngle() {
        int angle =(int) Math.toDegrees(Math.atan2(velocity.getValue(0) - velocity.getValue(2),
                velocity.getValue(1) - velocity.getValue(3)));

        if (angle < 0) angle *= -1;
        else if (angle > 0) {
            angle -= 360;
            angle *= -1;
        }

        return angle;

    }

    @Override
    public String toString() {
        return "StateSystem{" + "\n" +
                "\t time=" + time + ", \n" +
                "\t position=" + position + ", \n" +
                "\t velocity=" + velocity + ", \n" +
                "\t acceleration=" + acceleration + ", \n" +
                "\t mass=" + mass +
                '}';
    }
}
