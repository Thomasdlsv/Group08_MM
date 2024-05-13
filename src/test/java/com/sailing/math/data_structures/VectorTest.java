package com.sailing.math.data_structures;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class VectorTest extends TestCase {

    public void testAdd() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);
        Vector v2 = new Vector(5, 6, 7, 8);

        // when
        Vector result = v1.add(v2);

        // then
        assertEquals(new Vector(6, 8, 10, 12), result);
    }

    public void testMultiplyByScalar() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);
        double scalar = 2;

        // when
        Vector result = v1.multiplyByScalar(scalar);

        // then
        assertEquals(new Vector(2, 4, 6, 8), result);
    }

    public void testSubtract() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);
        Vector v2 = new Vector(5, 6, 7, 8);

        // when
        Vector result = v1.subtract(v2);

        // then
        assertEquals(new Vector(-4, -4, -4, -4), result);
    }

    public void testGetLength() {
        // given
        Vector v1 = new Vector(1, 1, 1, 1);

        // when
        double result = v1.getLength();

        // then
        assertEquals(2d, result);
    }

    public void testGetValues() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);

        // when
        ArrayList<Double> result = v1.getValues();

        // then
        assertEquals(new ArrayList<>(List.of(1d, 2d, 3d, 4d)), result);
    }

    public void testGetSize() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);

        // when
        int result = v1.getSize();

        // then
        assertEquals(4, result);
    }

    public void testGetValue() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);

        // when
        double result1 = v1.getValue(0);
        double result2 = v1.getValue(1);
        double result3 = v1.getValue(2);
        double result4 = v1.getValue(3);

        // then
        assertEquals(1d, result1);
        assertEquals(2d, result2);
        assertEquals(3d, result3);
        assertEquals(4d, result4);
    }

    public void testDotProduct() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);
        Vector v2 = new Vector(5, 6, 7, 8);

        // when
        double result = v1.dotProduct(v2);

        // then
        assertEquals(70d, result);
    }

    public void testNormalize() {
        // given
        Vector v1 = new Vector(1, 1, 1, 1);

        // when
        Vector result = v1.normalize();

        // then
        assertEquals(new Vector(0.5, 0.5, 0.5, 0.5), result);
    }

    public void testTestEquals() {
        // given
        Vector v1 = new Vector(1, 2, 3, 4);
        Vector v2 = new Vector(1, 2, 3, 4);

        // when
        boolean result = v1.equals(v2);

        // then
        assertTrue(result);
    }
}