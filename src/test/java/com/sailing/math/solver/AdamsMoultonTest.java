package com.sailing.math.solver;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.Function;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AdamsMoultonTest {

    @Test
    public void testRungeKuttaOnSimpleFunction() {
        Vector positions = new Vector(0,0,0,0);
        Vector velocities = new Vector(0,0,0,0);
        Vector accelerations = new Vector(0,0,0,0);
        double mass = 1;
        double h = 1;
        double t = 0;
        StateSystem system = new StateSystem(positions, velocities, accelerations, mass);
        Solver solver = new AdamsMoulton(new Euler());
        Function f = (v1, v2, m, h1, t1) -> new Vector(3, 3, 3, 3 );
        ArrayList<StateSystem> history = new ArrayList<>();
        history.add(system);

        for (int i = 0; i < 10; i++) {
            system = solver.nextStep(f, system.getPosition(), system.getVelocity(), system.getMass(), h, t);
            history.add(system);
        }

        assertEquals(11, history.size());
        assertEquals(new Vector(0, 0, 0, 0), history.get(0).getPosition());
        assertEquals(new Vector(0, 0, 0, 0), history.get(0).getVelocity());

        assertEquals(new Vector(1.25, 1.25, 0, 0), history.get(1).getPosition());
        assertEquals(new Vector(0, 0, 3, 3), history.get(1).getVelocity());

        assertEquals(new Vector(5.5, 5.5, 0, 0), history.get(2).getPosition());
        assertEquals(new Vector(0, 0, 6, 6), history.get(2).getVelocity());

        assertEquals(new Vector(12.75, 12.75, 0, 0), history.get(3).getPosition());
        assertEquals(new Vector(0, 0, 9, 9), history.get(3).getVelocity());

        assertEquals(new Vector(23.0, 23.0, 0, 0), history.get(4).getPosition());
        assertEquals(new Vector(0, 0, 12, 12), history.get(4).getVelocity());

        assertEquals(new Vector(119.25, 119.25, 0, 0), history.get(9).getPosition());
        assertEquals(new Vector(0, 0, 27, 27), history.get(9).getVelocity());
    }
}
