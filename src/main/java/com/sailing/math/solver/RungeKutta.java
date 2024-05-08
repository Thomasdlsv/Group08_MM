package com.sailing.math.solver;

import com.sailing.math.DifferentialEquation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.Function;

/**
 * 4th order Runge-Kutta method
 */
public class RungeKutta implements Solver {

    @Override
    public StateSystem nextStep(Function f, Vector positions, Vector velocities, double mass, double h, double t) {

        Vector[] k1 = nextK(f,
                positions,
                velocities,
                mass,
                h,
                t);

        Vector[] k2 = nextK(f,
                positions.add(k1[0].multiplyByScalar(1.0 / 2)),
                velocities.add(k1[1].multiplyByScalar(1.0 / 2)),
                mass,
                h,
                t + h/2);

        Vector[] k3 = nextK(f,
                positions.add(k2[0].multiplyByScalar(1.0 / 2)),
                velocities.add(k2[1].multiplyByScalar(1.0 / 2)),
                mass,
                h,
                t + h/2);

        Vector[] k4 = nextK(f,
                positions.add(k3[0]),
                velocities.add(k3[1]),
                mass,
                h,
                t + h);

        Vector positionsWi1 = positions.add(
                k1[0].add(k2[0].multiplyByScalar(2))
                        .add(k3[0].multiplyByScalar(2))
                        .add(k4[0])
                        .multiplyByScalar(1.0/6));

        Vector velocitiesWi1 = velocities.add(
                k1[1].add(k2[1].multiplyByScalar(2))
                        .add(k3[1].multiplyByScalar(2))
                        .add(k4[1])
                        .multiplyByScalar(1.0/6));

        Vector accelerationsWi1 = f.eval(positionsWi1, velocitiesWi1, mass, h, t + h);

        return new StateSystem(positionsWi1, velocitiesWi1, accelerationsWi1, mass, t + h);
    }

    /**
     * calculates the next k
     * @param f function
     * @param positions
     * @param velocities
     * @param mass
     * @param h step size
     * @param t time
     * @return
     */
    private Vector[] nextK(Function f, Vector positions, Vector velocities, double mass, double h, double t) {
        Vector[] k = DifferentialEquation.solve(
                f,
                positions,
                velocities,
                mass,
                h,
                t);
        k[0] = k[0].multiplyByScalar(h);
        k[1] = k[1].multiplyByScalar(h);
        return k;
    }
}
