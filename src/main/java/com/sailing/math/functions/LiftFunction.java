package com.sailing.math.functions;

import com.sailing.math.Vector;
import com.sailing.math.Vector2D;
import com.sailing.math.physics.Coefficients;
import com.sailing.math.physics.Constants;

/**
 * L = 1/2 * p * C * (beta) * V^2 * S
 * Where:
 * L = lift
 * p = air density
 * C = lift coefficient
 * (beta) = angle of attack
 * V = apparent wind speed
 * S = sail area
 */
public class LiftFunction implements Function{

    /**
     *
     * @param v1 position of Boat. (x0 = x, x1 = y, x2 = angle in degrees)
     * @param v2 velocities (Wind and Boat). (Wind: x0-x1, Boat x2-x3)
     * @param a mass. (ignored in this function)
     * @param h step size. (ignored in this function)
     * @param t time. (ignored in this function)
     * @return Vector with the lift force.
     */

    public Vector eval(Vector v1, Vector v2, double a, double h, double t) {
        Vector2D windVelocity = new Vector2D(v2.getValue(0), v2.getValue(1));
        Vector2D boatVelocity = new Vector2D(v2.getValue(2), v2.getValue(3));
        Vector2D apparentWind = windVelocity.subtract(boatVelocity);
        double v = apparentWind.getLength();
        double beta = apparentWind.toPolar().getX2() - v1.getValue(2);
        double p = Constants.AIR_DENSITY;
        double C = Coefficients.calculateLiftCoefficient(beta);
        double s = Constants.SAIL_AREA;

        double lift = 0.5 * p * C * beta * Math.pow(v, 2) * s;
        Vector liftDirection = apparentWind.rotate(90).normalize();
        return liftDirection.multiplyByScalar(lift);
    };
}
