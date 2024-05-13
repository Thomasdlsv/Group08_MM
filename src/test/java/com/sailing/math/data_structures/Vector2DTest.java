package com.sailing.math.data_structures;

import junit.framework.TestCase;

public class Vector2DTest extends TestCase {

    public void testGetX1() {
        // given
        Vector2D v1 = new Vector2D(1, 2);

        // when
        double result = v1.getX1();

        // then
        assertEquals(1d, result);
    }

    public void testGetX2() {
        // given
        Vector2D v1 = new Vector2D(1, 2);

        // when
        double result = v1.getX2();

        // then
        assertEquals(2d, result);
    }

    public void testToPolar() {
        // given
        Vector2D v1 = new Vector2D(0, 1);

        // when
        Vector2D result = v1.toPolar();

        // then
        assertEquals(1d, result.getX1(), 0.00000001);
        assertEquals(Math.PI / 2, result.getX2(), 0.00000001);
    }

    public void testToCartesian() {
        // given
        Vector2D v1 = new Vector2D(1, Math.PI / 2, true);

        // when
        Vector2D result = v1.toCartesian();

        // then
        assertEquals(0d, result.getX1(), 0.00000001);
        assertEquals(1d, result.getX2(), 0.00000001);
    }

    public void testRotatePolarVector() {
        // given
        Vector2D v1 = new Vector2D(1, Math.PI / 2, true);

        // when
        Vector2D result = v1.rotate(90);

        // then
        assertEquals(1d, result.getX1(), 0.00000001);
        assertEquals(Math.PI, result.getX2(), 0.00000001);
    }

    public void testRotateCartesianVector() {
        // given
        Vector2D v1 = new Vector2D(1, 0);

        // when
        Vector2D result = v1.rotate(90);

        // then
        assertEquals(0d, result.getX1(), 0.00000001);
        assertEquals(1d, result.getX2(), 0.00000001);
    }

    public void testAdd() {
        // given
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(2, 2);

        // when
        Vector2D result = v1.add(v2);

        // then
        assertEquals(new Vector2D(3, 3), result);
    }

    public void testSubtract() {
        // given
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(2, 2);

        // when
        Vector2D result = v1.subtract(v2);

        // then
        assertEquals(new Vector2D(-1, -1), result);
    }
}