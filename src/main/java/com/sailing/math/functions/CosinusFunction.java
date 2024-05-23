package com.sailing.math.functions;

import com.sailing.math.data_structures.Vector;

public class CosinusFunction implements Function {

    @Override
    public Vector eval(Vector positions, Vector velocities, double mass, double h, double t) {
        double y = Math.cos(t);
        return new Vector(y, y, y, y);
    }
}
