package com.sailing.math.functions;

import com.sailing.math.Vector;

public interface Function {

    /**
     * This method is used to solve a function.
     * @param v1 position.
     * @param v2 velocities (Wind and Boat).
     * @param a mass.
     * @param h step size.
     * @param t time.
     */
    Vector eval(Vector v1, Vector v2, double a, double h, double t);
}
