package com.sailing.math.functions;

import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.physics.Coefficients;
import com.sailing.math.physics.Constants;

/**
 * D = 1/2 * p * C * (beta) * V^2 * S
 * Where:
 * D = drag
 * p = air density
 * C = drag coefficient
 * (beta) = angle of attack
 * V = apparent wind speed
 * S = sail area
 */
public class DragFunction implements Function {

    /**
     *
     * @param v1 position of Boat. (x0 = x, x1 = y, x2 = angle in degrees)
     * @param v2 velocities (Wind and Boat). (Wind: x0-x1, Boat x2-x3)
     * @param m mass. (ignored in this function)
     * @param h step size. (ignored in this function)
     * @param t time. (ignored in this function)
     * @return Vector with the drag force.
     */
    public Vector eval(Vector v1, Vector v2, double m, double h, double t) {
        Vector2D windVelocity = new Vector2D(v2.getValue(0), v2.getValue(1));
        Vector2D boatVelocity = new Vector2D(v2.getValue(2), v2.getValue(3));
        Vector2D apparentWind = windVelocity.subtract(boatVelocity);
        double v = apparentWind.getLength();
        double beta = Math.toDegrees(apparentWind.toPolar().getX2() - v1.getValue(2));
        double p = Constants.AIR_DENSITY;
        double C = Coefficients.calculateDragCoefficient(beta);
        double s = Constants.SAIL_AREA;

        double drag = 0.5 * p * C * beta * Math.pow(v, 2) * s;
        Vector dragDirection = apparentWind.normalize();
        return dragDirection.multiplyByScalar(drag);
    };
}
