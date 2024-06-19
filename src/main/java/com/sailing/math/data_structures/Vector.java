package com.sailing.math.data_structures;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Immutable Vector class.
 * <p>
 * This class is used to represent vectors in n-dimensional space.
 */
public class Vector {

    // immutable!
    private final ArrayList<Double> values = new ArrayList<>();

    public Vector(double... values) {
        for (double value : values) {
            this.values.add(value);
        }
    }

    /**
     * Add two vectors.
     * @param v vector to add.
     * @return new vector.
     */
    public Vector add(Vector v) {
        checkSize(v);
        Vector result = new Vector();
        for (int i = 0; i < values.size(); i++) {
            result.values.add(values.get(i) + v.values.get(i));
        }
        return result;
    }

    /**
     * Multiply vector by scalar.
     * @param scalar.
     * @return new vector.
     */
    public Vector multiplyByScalar(double scalar) {
        Vector result = new Vector();
        for (double value : values) {
            result.values.add(value * scalar);
        }
        return result;
    }

    /**
     * Subtract two vectors.
     * @param v vector to subtract.
     * @return new vector.
     */
    public Vector subtract(Vector v) {
        checkSize(v);
        Vector result = new Vector();
        for (int i = 0; i < values.size(); i++) {
            result.values.add(values.get(i) - v.values.get(i));
        }
        return result;
    }

    /**
     * Get the total length of the vector.
     * Calculated using the formula: sqrt(x1^2 + x2^2 + ... + xn^2)
     * @return length.
     */
    public double getLength() {
        double sum = 0;
        for (double value : values) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }

    /**
     * Get the values of the vector.
     * @return ArrayList of values.
     */
    public ArrayList<Double> getValues() {
        return new ArrayList<>(values);
    }

    /**
     * Get the size (dimension) of the vector.
     * @return size.
     */
    public int getSize() {
        return values.size();
    }

    public double getValue(int index) {
        return values.get(index);
    }

    /**
     * Calculate the dot product of two vectors.
     * @param v vector to calculate dot product with.
     * @return dot product.
     */
    public double dotProduct(Vector v) {
        checkSize(v);
        double result = 0;
        for (int i = 0; i < values.size(); i++) {
            result += values.get(i) * v.values.get(i);
        }
        return result;
    }

    private void checkSize(Vector v) {
        if (values.size() != v.values.size()) {
            throw new VectorSizeException(this, v);
        }
    }

    /**
     * Normalize the vector.
     * @return new vector.
     */
    public Vector normalize() {
        double length = getLength();
        if (length == 0) {
            throw new ArithmeticException("Cannot normalize a vector with length 0.");
        }
        Vector result = new Vector();
        for (double value : values) {
            result.values.add(value / length);
        }
        return result;
    }

    public Vector copy() {
        Vector result = new Vector();
        result.values.addAll(values);
        return result;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector v)) return false;
        if (values.size() != v.values.size()) return false;
        for (int i = 0; i < values.size(); i++) {
            if (!Objects.equals(values.get(i), v.values.get(i))) {
                return false;
            }
        }
        return true;
    }
}
