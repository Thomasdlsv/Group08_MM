package com.sailing.math.functions;

import com.sailing.math.data_structures.Vector;

public class ExponentialFunction implements Function {

    @Override
    public Vector eval(Vector positions, Vector velocities, double mass, double h, double t) {
        double y = positions.getValue(0);
        // dy/dx = y
        return new Vector(y, y, y, y);
    }
}
