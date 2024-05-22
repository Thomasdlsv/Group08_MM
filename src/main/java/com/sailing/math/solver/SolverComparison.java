package com.sailing.math.solver;

import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.Function;
import com.sailing.math.StateSystem;

import java.io.FileWriter;
import java.io.IOException;

public class SolverComparison {

    public static void main(String[] args) throws IOException {
        double initialCondition = 1.0; // y(0) = e^0 = 1

        ExponentialFunction function = new ExponentialFunction();

        Vector initialPosition = new Vector(initialCondition, 0, 0, 0);
        Vector initialVelocity = new Vector(1, 1, 1, 1); // set initial velocity to zero
        double mass = 1.0;

        double[] stepSizes = {0.000001, 0.00001, 0.0001, 0.001, 0.01, 0.1};
        double targetTime = 4.0;

        compareSolvers(function, initialPosition, initialVelocity, mass, stepSizes, targetTime, "euler_results.csv", new Euler());
        compareSolvers(function, initialPosition, initialVelocity, mass, stepSizes, targetTime, "rk_results.csv", new RungeKutta());
        compareSolvers(function, initialPosition, initialVelocity, mass, stepSizes, targetTime, "am_results.csv", new AdamsMoulton(new RungeKutta()));
    }

    private static void compareSolvers(Function function, Vector initialPosition, Vector initialVelocity, double mass, double[] stepSizes, double targetTime, String fileName, Solver solver) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.append("StepSize,RelativeError\n");

        for (double h : stepSizes) {
            double t = 0.0;
            Vector positions = initialPosition.copy();
            Vector velocities = initialVelocity.copy();

            while (t < targetTime) {
                StateSystem state = solver.nextStep(function, positions, velocities, mass, h, t);
                positions = state.getPosition().copy();
                velocities = state.getVelocity().copy();
                t += h;
            }

            double exactY = Math.exp(targetTime);
            double numericalY = positions.getValue(0);
            double relativeError = Math.abs((numericalY - exactY) / exactY);

            System.out.printf("StepSize: %.6f, NumericalY: %.5f, ExactY: %.5f, RelativeError: %.10f%n", h, numericalY, exactY, relativeError);

            writer.append(h + "," + relativeError + "\n");
        }

        writer.flush();
        writer.close();
    }
}
