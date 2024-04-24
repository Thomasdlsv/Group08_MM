package com.sailing.math.solver;

import com.sailing.math.StateSystem;
import com.sailing.math.Vector;
import com.sailing.math.functions.Function;

public interface Solver {

    StateSystem nextStep(Function f, Vector v1, Vector v2, double a, double h, double t);
}
