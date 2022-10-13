package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KeyboardGeometryTest {

    private KeyboardGeometry fiveKeyLayout;
    private KeyboardGeometry staggeredNumpad;

    @BeforeEach
    public void initialize() {
        fiveKeyLayout = new KeyboardGeometry();
        staggeredNumpad = new KeyboardGeometry();

        /*
         * [0:LP] [1:LR] [2:LM] [3:LI] [4:LT]
         */
        fiveKeyLayout
                .withContactPoint(new Coord2D(0.00, 0.00), Finger.LEFT_PINKY)
                .withContactPoint(new Coord2D(1.00, 0.00), Finger.LEFT_RING)
                .withContactPoint(new Coord2D(2.00, 0.00), Finger.LEFT_MIDDLE)
                .withContactPoint(new Coord2D(3.00, 0.00), Finger.LEFT_INDEX)
                .withContactPoint(new Coord2D(4.00, 0.00), Finger.LEFT_THUMB);

        /*
         * [0:LP] [1:LR] [2:LM]
         *  [3:RT] [4:RM] [5:RP]
         *   [6:LI] [7:RI] [8:LP]
         */
        staggeredNumpad
                .withContactPoint(0.00, 0.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, 0.00, Finger.LEFT_RING)
                .withContactPoint(2.00, 0.00, Finger.LEFT_MIDDLE)
                .withContactPoint(0.25, 1.00, Finger.RIGHT_THUMB)
                .withContactPoint(1.25, 1.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(2.25, 1.00, Finger.RIGHT_PINKY)
                .withContactPoint(0.50, 2.00, Finger.LEFT_INDEX)
                .withContactPoint(1.50, 2.00, Finger.RIGHT_INDEX)
                .withContactPoint(2.50, 2.00, Finger.LEFT_PINKY);
    }

    @Test
    public void testConstructorAndContactPointInsertion() {
        // withContactPoint is tested alongside the constructor because
        // the constructor _should_ only instantiate internal fields to default
        // values rather than setting anything special. withContactPoint is
        // the method used to insert contact points into the geometry, sort of
        // like how insert is the method used to insert values into List<T>s;
        // however, since the constructor for KeyboardGeometry does absolutely
        // nothing special, it's more productive to see if the "construction"
        // phase of the geometry (i.e. both instantiation and insertion of
        // necessary contact points) functions.

        assertEquals(5, fiveKeyLayout.getNumContactPoints());
        assertEquals(9, staggeredNumpad.getNumContactPoints());

        Coord2D firstKeyCoordinate = fiveKeyLayout.getCoord(0);
        assertEquals(0.00, firstKeyCoordinate.getX());
        assertEquals(0.00, firstKeyCoordinate.getY());
        assertEquals(Finger.LEFT_PINKY, fiveKeyLayout.getFingerAssignment(firstKeyCoordinate));

        Coord2D eighthKeyCoordinate = staggeredNumpad.getCoord(7);
        assertEquals(1.50, eighthKeyCoordinate.getX());
        assertEquals(2.00, eighthKeyCoordinate.getY());
        assertEquals(Finger.RIGHT_INDEX, staggeredNumpad.getFingerAssignment(eighthKeyCoordinate));

        staggeredNumpad.withContactPoint(99.00, -33.00, Finger.RIGHT_RING);
        Coord2D newlyAddedKey = staggeredNumpad.getCoord(9);

        assertEquals(10, staggeredNumpad.getNumContactPoints());
        assertEquals(99.00, newlyAddedKey.getX());
        assertEquals(-33.00, newlyAddedKey.getY());
        assertEquals(Finger.RIGHT_RING, staggeredNumpad.getFingerAssignment(newlyAddedKey));
    }

    @Test
    public void testIsValidContactPoint() {
        assertTrue(fiveKeyLayout.isValidContactPoint(4.00, 0.00));
        assertFalse(fiveKeyLayout.isValidContactPoint(5.00, 0.00));

        assertTrue(staggeredNumpad.isValidContactPoint(new Coord2D(2.50, 2.00)));
        assertFalse(staggeredNumpad.isValidContactPoint(new Coord2D(3.50, 2.00)));

        staggeredNumpad.withContactPoint(3.50, 2.00, Finger.RIGHT_THUMB);
        assertTrue(staggeredNumpad.isValidContactPoint(new Coord2D(3.50, 2.00)));
    }
}
