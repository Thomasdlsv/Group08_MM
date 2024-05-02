package com.sailing.math.physics;

public class Coefficients {

    public static double calculateLiftCoefficient(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return -7.11145860926042e-19 * Math.pow(x, 10)
        + 7.29835098673025e-16 * Math.pow(x, 9)
        - 3.17294516756617e-13 * Math.pow(x, 8)
        + 7.63300503284026e-11 * Math.pow(x, 7)
        - 1.11225338175080e-08 * Math.pow(x, 6)
        + 1.00853574891538e-06 * Math.pow(x, 5)
        - 5.61414835267720e-05 * Math.pow(x, 4)
        + 0.00181651020401031 * Math.pow(x, 3)
        - 0.0308244844086802 * Math.pow(x, 2)
        + 0.241539695258776 * Math.pow(x, 1)
        - 0.0455606772480888;
    }

    public static double calculateDragCoefficient(double beta) {
        return 0; // TODO: Implement drag coefficient calculation
    }
}
