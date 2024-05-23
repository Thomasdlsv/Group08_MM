package com.sailing.math.solver;

import com.sailing.math.DifferentialEquation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.Function;

import static com.sailing.math.data_structures.Round.round;

public class Euler implements Solver {

    @Override
    public StateSystem nextStep(Function f, Vector positions, Vector velocities, double mass, double h, double t) {

        Vector[] wi1 = DifferentialEquation.solve(f, positions, velocities, mass, h, t);

        Vector positionsWi1 = new Vector(
                positions.getValue(0) + wi1[0].getValue(2) * h,
                positions.getValue(1) + wi1[0].getValue(3) * h,
                positions.getValue(2),
                positions.getValue(3)
        );

        Vector velocitiesWi1 =  new Vector(
                velocities.getValue(0),
                velocities.getValue(1),
                velocities.getValue(2)  + wi1[1].getValue(0) * h,
                velocities.getValue(3)  + wi1[1].getValue(1) * h
        );

        Vector accelerationsWi1 = wi1[1];

        return new StateSystem(positionsWi1, velocitiesWi1, accelerationsWi1, mass, round(t+h));
    }

}
