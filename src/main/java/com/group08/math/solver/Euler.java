package com.group08.math.solver;

import com.group08.math.DifferentialEquation;
import com.group08.math.StateSystem;
import com.group08.math.Vector;
import com.group08.math.functions.Function;

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
