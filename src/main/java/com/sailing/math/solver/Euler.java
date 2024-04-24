package com.sailing.math.solver;

import com.sailing.math.DifferentialEquation;
import com.sailing.math.StateSystem;
import com.sailing.math.Vector;
import com.sailing.math.functions.Function;

public class Euler implements Solver {

    @Override
    public StateSystem nextStep(Function f, Vector positions, Vector velocities, double mass, double h, double t) {

        Vector[] wi1 = DifferentialEquation.solve(f, positions, velocities, mass, h, t);

        Vector positionsWi1 = positions.add(wi1[0].multiplyByScalar(h));
        Vector velocitiesWi1 = velocities.add(wi1[1].multiplyByScalar(h));
        Vector accelerationsWi1 = wi1[1];

        return new StateSystem(positionsWi1, velocitiesWi1, accelerationsWi1, mass);
    }

}
