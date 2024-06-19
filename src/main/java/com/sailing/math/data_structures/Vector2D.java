package com.sailing.math.data_structures;

public class Vector2D extends Vector {

    public final boolean POLAR;

    public Vector2D(double x, double y) {
        super(x, y);
        POLAR = false;
    }

    public Vector2D(double x1, double x2, boolean polar) {
        super(x1, x2);
        POLAR = polar;
    }

    public double getX1() {
        return getValue(0);
    }

    public double getX2() {
        return getValue(1);
    }

    public Vector2D toPolar() {
        if (POLAR) return this;
        double r = getLength();
        double theta = Math.atan2(getX2(), getX1());
        return new Vector2D(r, theta, true);
    }

    public Vector2D toCartesian() {
        if (!POLAR) return this;
        double x = getX1() * Math.cos(getX2());
        double y = getX1() * Math.sin(getX2());
        return new Vector2D(x, y);
    }

    public Vector2D rotate(double angleInDegrees) {
        if (POLAR) {
            double theta = getX2() + Math.toRadians(angleInDegrees);
            return new Vector2D(getX1(), theta);
        }
        Vector2D polar = toPolar();
        double theta = polar.getX2() + Math.toRadians(angleInDegrees);
        return new Vector2D(polar.getX1(), theta, true).toCartesian();
    }

    public Vector2D add(Vector2D v) {
        if (POLAR && v.POLAR) {
            return new Vector2D(getX1() + v.getX1(), getX2() + v.getX2(), true);
        }
        if (POLAR) {
            return add(v.toPolar());
        }
        if (!v.POLAR) {
            return new Vector2D(getX1() + v.getX1(), getX2() + v.getX2());
        }
        return add(v.toCartesian());
    }

    public Vector2D subtract(Vector2D v) {
        if (POLAR && v.POLAR) {
            return new Vector2D(getX1() - v.getX1(), getX2() - v.getX2(), true);
        }
        if (POLAR) {
            return subtract(v.toPolar());
        }
        if (!v.POLAR) {
            return new Vector2D(getX1() - v.getX1(), getX2() - v.getX2());
        }
        return subtract(v.toCartesian());
    }

    public Vector2D multiplyByScalar(double scalar) {
        if (POLAR) {
            return new Vector2D(getX1() * scalar, getX2(), true);
        }
        return new Vector2D(getX1() * scalar, getX2() * scalar);
    }
}
