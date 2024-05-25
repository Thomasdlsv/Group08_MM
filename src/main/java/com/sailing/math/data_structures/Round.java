package com.sailing.math.data_structures;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Round a double to a certain number of decimal places.
 * <p>
 * Use to avoid floating point errors.
 */
public class Round {

    /**
     * Round to a certain number of decimal places.
     *
     * @param value  the value to round.
     * @param places the number of decimal places.
     * @return the rounded value.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Round to 12 decimal places (default).
     *
     * @param value the value to round.
     * @return the rounded value.
     */
    public static double round(double value) {
        return round(value, 12);
    }
}
