package com.sailing.math.solver;

import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.Function;

public class ExponentialFunction implements Function {

    @Override
    public Vector eval(Vector positions, Vector velocities, double mass, double h, double t) {
        double y = positions.getValue(0);
        // dy/dx = y
        return new Vector(y, 0, 0, 0);
    }
}
