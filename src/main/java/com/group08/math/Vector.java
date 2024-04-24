package com.group08.math;

import java.util.ArrayList;

public class Vector {

    private final ArrayList<Double> values = new ArrayList<>();

    public Vector(double... values) {
        for (double value : values) {
            this.values.add(value);
        }
    }

    public Vector add(Vector v) {
        checkSize(v);
        Vector result = new Vector();
        for (int i = 0; i < values.size(); i++) {
            result.values.add(values.get(i) + v.values.get(i));
        }
        return result;
    }

    public Vector multiplyByScalar(double scalar) {
        Vector result = new Vector();
        for (double value : values) {
            result.values.add(value * scalar);
        }
        return result;
    }

    public Vector subtract(Vector v) {
        checkSize(v);
        Vector result = new Vector();
        for (int i = 0; i < values.size(); i++) {
            result.values.add(values.get(i) - v.values.get(i));
        }
        return result;
    }

    public double getLength() {
        double sum = 0;
        for (double value : values) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public int getSize() {
        return values.size();
    }

    public double getValue(int index) {
        return values.get(index);
    }

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

    @Override
    public String toString() {
        return "Vector{" +
                "values=" + values +
                '}';
    }
}
