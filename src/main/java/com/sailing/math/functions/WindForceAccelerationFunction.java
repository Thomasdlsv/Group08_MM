package com.sailing.math.functions;

import com.sailing.math.data_structures.Vector;

/**
 * F = D + L                            <br>
 * Where:                               <br>
 * F = wind force in Newtons            <br>
 * D = drag force in Newtons            <br>
 * L = lift force in Newtons            <br>
 *                                      <p>
 * a = F / m                            <br>
 * Where:                               <br>
 * a = acceleration in m/s^2            <br>
 * F = wind force in Newtons            <br>
 * m = mass in kg                       <br>
 */
public class WindForceAccelerationFunction implements Function {

    /**
     *
     * @param v1 position of Boat. (x0 = x, x1 = y, x2 = angle in degrees)
     * @param v2 velocities (Wind and Boat). (Wind: x0-x1, Boat x2-x3)
     * @param m mass
     * @param h step size
     * @param t time
     * @return Vector with the acceleration of the boat.
     */
    public Vector eval(Vector v1, Vector v2, double m, double h, double t) {
        Vector drag = new DragFunction().eval(v1, v2, m, h, t);
        Vector lift = new LiftFunction().eval(v1, v2, m, h, t);

        return drag.add(lift).multiplyByScalar(1d / m);
    };
}
