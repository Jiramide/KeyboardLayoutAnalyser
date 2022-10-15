package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Coord2D;

import java.lang.Math;

class Coord2DTest {

    private final double TEST_EPSILON = 0.001;

    private Coord2D origin;
    private Coord2D firstQuadrant;
    private Coord2D secondQuadrant;
    private Coord2D thirdQuadrant;
    private Coord2D fourthQuadrant;
    private Coord2D onXAxis;
    private Coord2D onYAxis;

    @BeforeEach
    public void initializeTests() {
        origin = new Coord2D();

        firstQuadrant = new Coord2D(3.00, 4.00);
        secondQuadrant = new Coord2D(-3.14, 2.81);
        thirdQuadrant = new Coord2D(-801325.012, -9130453.14);
        fourthQuadrant = new Coord2D(500.00, -0.01);

        onXAxis = new Coord2D(42304.00, 0.00);
        onYAxis = new Coord2D(0.00, -3845.00);
    }

    // effects: determines if two floating values are sufficiently close enough to each other (within
    //          epsilon) to account for floating point inaccuracies. This method will be used for
    //          floating point equality within the test methods in this class.
    public boolean areSufficientlyClose(double expected, double actual, double epsilon) {
        return Math.abs(expected - actual) <= epsilon;
    }

    // effects: determines if two Coord2Ds are sufficiently close enough to each other
    //          by testing if each individual coordinate is sufficiently close to each
    //          other. This method will be used for Coord2D equality within test methods
    //          in this class
    public boolean areSufficientlyClose(Coord2D expected, Coord2D actual, double epsilon) {
        return areSufficientlyClose(expected.getX(), actual.getX(), epsilon)
            && areSufficientlyClose(expected.getY(), actual.getY(), epsilon);
    }

    @Test
    public void testSufficientCloseFloating() {
        assertTrue(areSufficientlyClose(0.00, 1.00 / 9999999999999999999.0, 0.01));
        assertTrue(areSufficientlyClose(3.0, 1.0 + 2.0, 0.001));
        assertTrue(areSufficientlyClose(1000000.123, 1000000.122, 0.1));
        assertTrue(areSufficientlyClose(0.00, 1000000.00, 1000000000.00));

        assertFalse(areSufficientlyClose(0.0, 0.1, 0.001));
        assertFalse(areSufficientlyClose(3.0, 1.0 + 2.0 + 0.001, 0.0));
        assertFalse(areSufficientlyClose(0.00, 1000000.00, 1.0));
    }

    @Test
    public void testSufficientlyCloseCoord() {
        assertTrue(areSufficientlyClose(origin, new Coord2D(), 0.01));
        assertTrue(areSufficientlyClose(onXAxis, new Coord2D(42304.01, 0.00), 0.01));

        assertFalse(areSufficientlyClose(onYAxis, origin, 1.00));
        assertFalse(areSufficientlyClose(fourthQuadrant, firstQuadrant, 1.00));
    }

    @Test
    public void testConstructor() {
        assertTrue(areSufficientlyClose(0.00, origin.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(0.00, origin.getY(), TEST_EPSILON));

        assertTrue(areSufficientlyClose(3.00, firstQuadrant.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(4.00, firstQuadrant.getY(), TEST_EPSILON));

        assertTrue(areSufficientlyClose(-3.14, secondQuadrant.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(2.81, secondQuadrant.getY(), TEST_EPSILON));

        assertTrue(areSufficientlyClose(-801325.012, thirdQuadrant.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(-9130453.14, thirdQuadrant.getY(), TEST_EPSILON));

        assertTrue(areSufficientlyClose(500.00, fourthQuadrant.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(-0.01, fourthQuadrant.getY(), TEST_EPSILON));

        assertTrue(areSufficientlyClose(42304.00, onXAxis.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(0.00, onXAxis.getY(), TEST_EPSILON));

        assertTrue(areSufficientlyClose(0.00, onYAxis.getX(), TEST_EPSILON));
        assertTrue(areSufficientlyClose(-3845.00, onYAxis.getY(), TEST_EPSILON));
    }

    @Test
    public void testAdditionSpecificValues() {
        // The addition operation of Coord2D forms an abelian group, there are a couple of properties we can test
        // 1) Closure: this is implciitly passed since typing ensures that Coord2D addition results in Coord2D
        // 2) Associativity: a + (b + c) = (a + b) + c
        // 3) Existence of an identity: there exists a Coord2D i such that for all points p, i + p = p + i = p
        // 4) Existence of an inverse: for all points p, there exists a point -p such that p + -p = i
        // 5) Commutativity: for all points a, b, a + b = b + a

        Coord2D firstPlusXAxis = firstQuadrant.add(onXAxis);

        assertTrue(areSufficientlyClose(firstPlusXAxis, new Coord2D(42307.00, 4.00), TEST_EPSILON));

        Coord2D thirdPlusFourth = thirdQuadrant.add(fourthQuadrant);

        assertTrue(areSufficientlyClose(thirdPlusFourth, new Coord2D(-800825.012, -9130453.15), TEST_EPSILON));

        Coord2D xAxisPlusYAxis = onXAxis.add(onYAxis);

        assertTrue(areSufficientlyClose(xAxisPlusYAxis, new Coord2D(42304.00, -3845.00), TEST_EPSILON));
    }

    @Test
    public void testAdditionAssociativity() {
        // Test associativity
        // (a + b) + c
        Coord2D leftAssociativeBrackets = (firstQuadrant.add(fourthQuadrant)).add(secondQuadrant);
        // a + (b + c)
        Coord2D rightAssociativeBrackets = firstQuadrant.add(fourthQuadrant.add(secondQuadrant));

        assertTrue(areSufficientlyClose(leftAssociativeBrackets, rightAssociativeBrackets, TEST_EPSILON));
    }

    @Test
    public void testAdditionIdentity() {
        Coord2D firstPlusXAxis = firstQuadrant.add(onXAxis);

        // Test identity
        Coord2D originPlusFirst = origin.add(firstQuadrant);
        Coord2D originPlusFirstPlusXAxis = origin.add(firstPlusXAxis);

        assertTrue(areSufficientlyClose(firstQuadrant, originPlusFirst, TEST_EPSILON));
        assertTrue(areSufficientlyClose(firstPlusXAxis, originPlusFirstPlusXAxis, TEST_EPSILON));
    }
}