package com.sailing.math.physics;

public class Coefficients {

    public enum ReynoldsNumber {
        RE40K, RE80K, RE360K
    }

    public static ReynoldsNumber reynoldsNumber = ReynoldsNumber.RE360K;

    /**
     * This method is used to calculate the lift coefficient.
     * The coefficients of the polynomials were calculated using MATLAB's polyfit() method.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double calculateLiftCoefficient(double angleOfAttack) {
        return switch (reynoldsNumber) {
            case RE40K -> cl40K(angleOfAttack);
            case RE80K -> cl80K(angleOfAttack);
            case RE360K -> cl360K(angleOfAttack);
        };
    }

    /**
     * This method is used to calculate the lift coefficient.
     * The coefficients of the polynomials were calculated using MATLAB's polyfit() method.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double calculateDragCoefficient(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return  - 2.34883452212131e-20  * Math.pow(x, 10)
                + 1.16915339371553e-17  * Math.pow(x, 9)
                - 7.19105243730552e-16  * Math.pow(x, 8)
                - 6.78303608765851e-13  * Math.pow(x, 7)
                + 2.00518143371719e-10  * Math.pow(x, 6)
                - 2.61271931318120e-08  * Math.pow(x, 5)
                + 1.92548190311079e-06  * Math.pow(x, 4)
                - 9.01804243068557e-05  * Math.pow(x, 3)
                + 0.00275939330472374   * Math.pow(x, 2)
                - 0.0180052597286692    * Math.pow(x, 1)
                + 0.0230541325838875;
    }

    /**
     * Calculate the lift coefficient for Reynolds number 360k.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double cl360K(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return    3.19357350e-23 * Math.pow(x, 12)
                - 7.67865605e-21 * Math.pow(x, 11)
                - 1.03194204e-17 * Math.pow(x, 10)
                + 7.09734778e-15 * Math.pow(x, 9)
                - 2.09168670e-12 * Math.pow(x, 8)
                + 3.59077274e-10 * Math.pow(x, 7)
                - 3.90391301e-08 * Math.pow(x, 6)
                + 2.74425949e-06 * Math.pow(x, 5)
                - 1.22642094e-04 * Math.pow(x, 4)
                + 3.29473560e-03 * Math.pow(x, 3)
                - 4.77440243e-02 * Math.pow(x, 2)
                + 3.18993255e-01 * Math.pow(x, 1)
                - 1.06475061e-01;
    }

    /**
     * Calculate the lift coefficient for Reynolds number 80k.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double cl80K(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return  - 4.20379520e-22 * Math.pow(x, 12)
                + 4.80886259e-19 * Math.pow(x, 11)
                - 2.41493061e-16 * Math.pow(x, 10)
                + 7.00096347e-14 * Math.pow(x, 9)
                - 1.29583965e-11 * Math.pow(x, 8)
                + 1.59870586e-09 * Math.pow(x, 7)
                - 1.33157745e-07 * Math.pow(x, 6)
                + 7.41387540e-06 * Math.pow(x, 5)
                - 2.66640605e-04 * Math.pow(x, 4)
                + 5.77505996e-03 * Math.pow(x, 3)
                - 6.60150348e-02 * Math.pow(x, 2)
                + 3.25181943e-01 * Math.pow(x, 1)
                - 6.02958100e-02;
    }

    /**
     * Calculate the lift coefficient for Reynolds number 40k.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double cl40K(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return  - 1.25422792e-21 * Math.pow(x, 12)
                + 1.33346104e-18 * Math.pow(x, 11)
                - 6.20601813e-16 * Math.pow(x, 10)
                + 1.66154589e-13 * Math.pow(x, 9)
                - 2.82824177e-11 * Math.pow(x, 8)
                + 3.19283649e-09 * Math.pow(x, 7)
                - 2.41929623e-07 * Math.pow(x, 6)
                + 1.21714377e-05 * Math.pow(x, 5)
                - 3.92370018e-04 * Math.pow(x, 4)
                + 7.53517066e-03 * Math.pow(x, 3)
                - 7.47686593e-02 * Math.pow(x, 2)
                + 3.03500782e-01 * Math.pow(x, 1)
                - 2.43636579e-02;
    }
}
