package com.sailing.math.functions;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.physics.Coefficients;
import com.sailing.math.physics.Constants;

/**
 * L = 1/2 * p * C(beta) * V^2 * S                      <br>
 * Where:                                               <br>
 * L = lift                                             <br>
 * p = air density                                      <br>
 * C(beta) = lift coefficient in respect to beta        <br>
 * beta = angle of attack                               <br>
 * V = apparent wind speed                              <br>
 * S = sail area                                        <br>
 * ==> roh * (m/s)^2 * m^2 = (kg/m^3) * (m/s)^2 * m^2 = (kg * m^2 * m^2) / (m^3 * s^2) = kg * m / s^2 = N
 */
public class LiftFunction implements Function {

    /**
     *
     * @param v1 position of Boat. (x0 = x, x1 = y, x2 = angle in degrees boat, x3 = angle in degrees sail (relative to boat)
     * @param v2 velocities (Wind and Boat). (Wind: x0-x1, Boat x2-x3)
     * @param m mass. (ignored in this function)
     * @param h step size. (ignored in this function)
     * @param t time. (ignored in this function)
     * @return Vector with the lift force in Newtons.
     */
    public Vector eval(Vector v1, Vector v2, double m, double h, double t) {
        Vector2D windVelocity = new Vector2D(v2.getValue(0), v2.getValue(1));
        Vector2D boatVelocity = new Vector2D(v2.getValue(2), v2.getValue(3));
        Vector2D apparentWind = windVelocity.subtract(boatVelocity);
        double v = apparentWind.getLength();
        double beta = Math.toDegrees(apparentWind.toPolar().getX2()) - ((v1.getValue(2) + v1.getValue(3)));
        beta = (beta + 360*3) % 360;
        double p = Constants.AIR_DENSITY;
        double Cl = Coefficients.calculateLiftCoefficient(beta);
        double s = Constants.SAIL_AREA;

        double lift = 0.5 * p * Cl * Math.pow(v, 2) * s;
        Vector liftDirection = apparentWind.rotate(90).normalize();
        return liftDirection.multiplyByScalar(lift);
    };

    public Vector eval(StateSystem stateSystem, double h) {
        return eval(stateSystem.getPosition(), stateSystem.getVelocity(), stateSystem.getMass(), h, stateSystem.getTime());
    }
}
