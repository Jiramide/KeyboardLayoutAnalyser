package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Coord2D;

import java.lang.Math;

class Coord2DTest {

    private Coord2D origin;
    private Coord2D firstQuadrant;
    private Coord2D secondQuadrant;
    private Coord2D thirdQuadrant;
    private Coord2D fourthQuadrant;
    private Coord2D onXAxis;
    private Coord2D onYAxis;

    @BeforeEach
    public void initializeTests() {
        origin = new Coord2D(0.00, 0.00);

        firstQuadrant = new Coord2D(3.00, 4.00);
        secondQuadrant = new Coord2D(-3.14, 2.81);
        thirdQuadrant = new Coord2D(-801325.012, -9130453.14);
        fourthQuadrant = new Coord2D(500.00, -0.01);

        onXAxis = new Coord2D(42304.00, 0.00);
        onYAxis = new Coord2D(0.00, -3845.00);
    }

    //
    public boolean areSufficientlyClose(double expected, double actual, double epsilon) {
        return Math.abs(expected - actual) <= epsilon;
    }

    @Test
    public void testSufficientClose() {
        assertTrue(areSufficientlyClose(0.00, 1.00 / 9999999999999999999.0, 0.01));
        assertTrue(areSufficientlyClose(3.0, 1.0 + 2.0, 0.001));
        assertTrue(areSufficientlyClose(1000000.123, 1000000.122, 0.1));
        assertTrue(areSufficientlyClose(0.00, 1000000.00, 1000000000.00));

        assertFalse(areSufficientlyClose(0.0, 0.1, 0.001));
        assertFalse(areSufficientlyClose(3.0, 1.0 + 2.0 + 0.001, 0.0));
        assertFalse(areSufficientlyClose(0.00, 1000000.00, 1.0));
    }

}