package com.sailing.math.functions;

import com.sailing.math.data_structures.Vector;

public interface Function {

    /**
     * This method is used to solve a function.
     * @param v1 position of Boat. (x0 = x, x1 = y, x2 = angle in degrees)
     * @param v2 velocities (Wind and Boat). (Wind: x0-x1, Boat x2-x3)
     * @param m mass of Boat.
     * @param h step size.
     * @param t time.
     */
    Vector eval(Vector v1, Vector v2, double m, double h, double t);
}
