package com.sailing.math.data_structures;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    public static double round(double value) {
        return round(value, 12);
    }
}
