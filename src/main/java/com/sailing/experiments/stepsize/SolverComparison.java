package com.sailing.experiments.stepsize;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.CosinusFunction;
import com.sailing.math.functions.ExponentialFunction;
import com.sailing.math.functions.Function;
import com.sailing.math.solver.AdamsMoulton;
import com.sailing.math.solver.Euler;
import com.sailing.math.solver.RungeKutta;
import com.sailing.math.solver.Solver;

import java.io.FileWriter;
import java.io.IOException;

public class SolverComparison {

    enum FunctionType {
        EXPONENTIAL(new ExponentialFunction(), 1.0),
        COS(new CosinusFunction(), 0.0);

        public final Function function;
        public final double initialValue;
        FunctionType(Function function, double initialValue) {
            this.function = function;
            this.initialValue = initialValue;
        }
    }

    public static void main(String[] args) throws IOException {
        FunctionType functionType = FunctionType.COS; // Switch between COS and EXPONENTIAL

        Vector initialPosition = new Vector(functionType.initialValue, functionType.initialValue, functionType.initialValue, functionType.initialValue);
        Vector initialVelocity = new Vector(functionType.initialValue, functionType.initialValue, functionType.initialValue, functionType.initialValue);
        double mass = 1.0; // Mass is not used in the functions

        double[] stepSizes = {0.0000001, 0.000001, 0.00001, 0.0001, 0.001, 0.01, 0.1, 0.25, 0.5, 1.0};
        double targetTime = 4.0;

        String path = "src/main/java/com/sailing/experiments/stepsize/results/";
        compareSolvers(functionType, initialPosition, initialVelocity, mass, stepSizes, targetTime, path + "euler_results_sin.csv", new Euler());
        compareSolvers(functionType, initialPosition, initialVelocity, mass, stepSizes, targetTime, path + "rk_results_sin.csv", new RungeKutta());
        compareSolvers(functionType, initialPosition, initialVelocity, mass, stepSizes, targetTime, path + "am_results_sin.csv", new AdamsMoulton(new Euler()));
    }

    private static void compareSolvers(FunctionType functionType, Vector initialPosition, Vector initialVelocity, double mass, double[] stepSizes, double targetTime, String fileName, Solver solver) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.append("h,RelativeError,AbsoluteError\n");

        for (double h : stepSizes) {
            double t = 0.0;
            Vector positions = initialPosition.copy();
            Vector velocities = initialVelocity.copy();
            StateSystem state = new StateSystem(positions, velocities, new Vector(0, 0, 0, 0), mass, t);

            while (state.getTime() < targetTime) {
                state = solver.nextStep(functionType.function, state.getPosition(), state.getVelocity(), state.getMass(), h, state.getTime());
            }

            if (functionType == FunctionType.EXPONENTIAL) {
                double exactY = Math.exp(state.getTime());
                double numericalY = state.getPosition().getValue(0);
                double absoluteError = Math.abs(exactY - numericalY);
                double relativeError = Math.abs(absoluteError / exactY);

                System.out.printf("StepSize: %.7f, NumericalY: %.7f, ExactY: %.7f, RelativeError: %.16f%n", h, numericalY, exactY, relativeError);
                writer.append(h + "," + relativeError + "," + absoluteError + "\n");
            } else if (functionType == FunctionType.COS) {
                double exactY = Math.sin(state.getTime());
                double numericalY = state.getVelocity().getValue(2);
                double absoluteError = Math.abs(exactY - numericalY);
                double relativeError = Math.abs(absoluteError / exactY);

                System.out.printf("StepSize: %.7f, NumericalY: %.7f, ExactY: %.7f, RelativeError: %.16f%n", h, numericalY, exactY, relativeError);
                writer.append(h + "," + relativeError + "," + absoluteError + "\n");
            }
        }
        System.out.println("---------");

        writer.flush();
        writer.close();
    }
}
