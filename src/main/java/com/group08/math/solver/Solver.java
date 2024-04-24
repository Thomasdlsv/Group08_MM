package com.group08.math.solver;

import com.group08.math.StateSystem;
import com.group08.math.Vector;
import com.group08.math.functions.Function;

public interface Solver {

    StateSystem nextStep(Function f, Vector v1, Vector v2, double a, double h, double t);
}
