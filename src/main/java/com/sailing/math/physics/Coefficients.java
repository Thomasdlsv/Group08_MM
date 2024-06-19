package com.sailing.math.physics;

public class Coefficients {

    public enum ReynoldsNumber {
        RE40K, RE80K, RE160K, RE360K
    }

    public static ReynoldsNumber reynoldsNumber = ReynoldsNumber.RE360K;

    /**
     * This method is used to calculate the lift coefficient for the wind forces.
     * The coefficients of the polynomials were calculated using NumPy's polyfit() method.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double calculateLiftCoefficientWind(double angleOfAttack) {
        return switch (reynoldsNumber) {
            case RE40K -> cl40K(angleOfAttack);
            case RE80K -> cl80K(angleOfAttack);
            case RE160K -> cl160K(angleOfAttack);
            case RE360K -> cl360K(angleOfAttack);
        };
    }

    /**
     * This method is used to calculate the drag coefficient for the wind forces.
     * The coefficients of the polynomials were calculated using NumPy's polyfit() method.
     * @param angleOfAttack angle of attack in degrees.
     * @return drag coefficient.
     */
    public static double calculateDragCoefficientWind(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return  Math.max(0,
                - 2.34883452212131e-20  * Math.pow(x, 10)
                + 1.16915339371553e-17  * Math.pow(x, 9)
                - 7.19105243730552e-16  * Math.pow(x, 8)
                - 6.78303608765851e-13  * Math.pow(x, 7)
                + 2.00518143371719e-10  * Math.pow(x, 6)
                - 2.61271931318120e-08  * Math.pow(x, 5)
                + 1.92548190311079e-06  * Math.pow(x, 4)
                - 9.01804243068557e-05  * Math.pow(x, 3)
                + 0.00275939330472374   * Math.pow(x, 2)
                - 0.0180052597286692    * Math.pow(x, 1)
                + 0.0230541325838875);
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
     * Calculate the lift coefficient for Reynolds number 160k.
     * @param angleOfAttack angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double cl160K(double angleOfAttack) {
        double x = angleOfAttack % 180;
        return  - 3.80538880e-22 * Math.pow(x, 12)
                + 4.39074107e-19 * Math.pow(x, 11)
                - 2.22386892e-16 * Math.pow(x, 10)
                + 6.50298778e-14 * Math.pow(x, 9)
                - 1.21444459e-11 * Math.pow(x, 8)
                + 1.51245183e-09 * Math.pow(x, 7)
                - 1.27263345e-07 * Math.pow(x, 6)
                + 7.16670805e-06 * Math.pow(x, 5)
                - 2.61185279e-04 * Math.pow(x, 4)
                + 5.75117150e-03 * Math.pow(x, 3)
                - 6.73014971e-02 * Math.pow(x, 2)
                + 3.43470555e-01 * Math.pow(x, 1)
                - 7.70536143e-02;
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
        return  - 5.24355321e-22 * Math.pow(x, 12)
                + 5.87755779e-19 * Math.pow(x, 11)
                - 2.89008488e-16 * Math.pow(x, 10)
                + 8.19388682e-14 * Math.pow(x, 9)
                - 1.48055281e-11 * Math.pow(x, 8)
                + 1.77848682e-09 * Math.pow(x, 7)
                - 1.43694217e-07 * Math.pow(x, 6)
                + 7.71858604e-06 * Math.pow(x, 5)
                - 2.65548530e-04 * Math.pow(x, 4)
                + 5.41984406e-03 * Math.pow(x, 3)
                - 5.65701593e-02 * Math.pow(x, 2)
                + 2.41847930e-01 * Math.pow(x, 1)
                + 6.87196997e-03;
    }

    /**
     * This method is used to calculate the drag coefficient for the water forces.
     * The coefficients of the polynomials were calculated using NumPy's polyfit() method.
     * @param x angle of attack in degrees.
     * @return drag coefficient.
     */
    public static double calculateDragCoefficientWater(double x) {
        x = Math.abs(x - 180);
        return  Math.max(0,
                          2.75310002e-14   * Math.pow(x, 7)
                        - 1.98659092e-11    * Math.pow(x, 6)
                        + 5.52639998e-09  * Math.pow(x, 5)
                        - 7.27478099e-07    * Math.pow(x, 4)
                        + 4.36445232e-05   * Math.pow(x, 3)
                        - 8.95332536e-04    * Math.pow(x, 2)
                        + 9.62651401e-03  * Math.pow(x, 1)
                        + 1.01516496e-01);
    }

    /**
     * This method is used to calculate the lift coefficient for the water forces.
     * The coefficients of the polynomials were calculated using NumPy's polyfit() method.
     * @param x angle of attack in degrees.
     * @return lift coefficient.
     */
    public static double calculateLiftCoefficientWater(double x) {
        x = Math.abs(x - 180);
        double direction = (x-180 <= 0) ? 1 : -1;
        return (- 6.85204835e-15  * Math.pow(x, 7)
                + 2.81199867e-12  * Math.pow(x, 6)
                - 1.63577106e-10  * Math.pow(x, 5)
                - 9.31434523e-08  * Math.pow(x, 4)
                + 2.07385386e-05  * Math.pow(x, 3)
                - 1.69377627e-03  * Math.pow(x, 2)
                + 4.95875286e-02  * Math.pow(x, 1)
                + 8.05679732e-15) * direction;
    }
}
