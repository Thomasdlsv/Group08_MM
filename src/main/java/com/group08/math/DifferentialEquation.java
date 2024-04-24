package com.group08.math;

import com.group08.math.functions.Function;

/**
 * Differential equation solves d/dt for positions and velocities: <br>
 *
 * d/dt ( x(t) ; v(t) ) = ( v(t) ; f(t,x(t)) )
 *
 */
public abstract class DifferentialEquation {

    /**
     * this method calculates the result of the differential equation for positions and velocities
     * @param f function to calculate the derivative of the velocities
     * @param positions vector of all the positions
     * @param velocities vector of all the velocities
     * @param masses masses of the objects
     * @param h step size
     * @param t time
     * @return result of the differential equation (result[0] = velocities, result[1] = acceleration)
     */
    public static Vector[] solve(Function f, Vector positions, Vector velocities, double mass, double h, double t) {
        Vector[] result = new Vector[2];

        result[0] = velocities;
        result[1] = f.eval(positions, velocities, mass, h, t);

        return result;
    }
}
