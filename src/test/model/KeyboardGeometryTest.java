package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
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

        fiveKeyLayout.setInitialFingerPosition(Finger.LEFT_PINKY, 0.00, 0.00);
        fiveKeyLayout.setInitialFingerPosition(Finger.LEFT_RING, 1.00, 0.00);
        fiveKeyLayout.setInitialFingerPosition(Finger.LEFT_MIDDLE, 2.00, 0.00);
        fiveKeyLayout.setInitialFingerPosition(Finger.LEFT_INDEX, 3.00, 0.00);
        fiveKeyLayout.setInitialFingerPosition(Finger.LEFT_THUMB, 4.00, 0.00);

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

        staggeredNumpad.setInitialFingerPosition(Finger.LEFT_PINKY, 3.25, 1.00);
        staggeredNumpad.setInitialFingerPosition(Finger.LEFT_RING, 1.00, 0.00);
        staggeredNumpad.setInitialFingerPosition(Finger.LEFT_MIDDLE, 3.00, 1.00);
        staggeredNumpad.setInitialFingerPosition(Finger.LEFT_INDEX, 0.50, 2.00);
        staggeredNumpad.setInitialFingerPosition(Finger.RIGHT_PINKY, 5.00, 2.00);
        staggeredNumpad.setInitialFingerPosition(Finger.RIGHT_THUMB, 0.00, 0.00);
        staggeredNumpad.setInitialFingerPosition(Finger.RIGHT_INDEX, 2.34, 5.67);
        staggeredNumpad.setInitialFingerPosition(Finger.RIGHT_MIDDLE, 0.50, 0.50);
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
    public void testGetInitialFingerPosition() {
        Coord2D leftPinkyPosition = new Coord2D(0.00, 0.00);
        Coord2D leftRingPosition = new Coord2D(1.00, 0.00);
        Coord2D leftMiddlePosition = new Coord2D(2.00, 0.00);
        Coord2D leftIndexPosition = new Coord2D(3.00, 0.00);
        Coord2D leftThumbPosition = new Coord2D(4.00, 0.00);

        assertEquals(leftPinkyPosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_PINKY));
        assertEquals(leftRingPosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_RING));
        assertEquals(leftMiddlePosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_MIDDLE));
        assertEquals(leftIndexPosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_INDEX));
        assertEquals(leftThumbPosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_THUMB));

        fiveKeyLayout.setInitialFingerPosition(Finger.LEFT_PINKY, 12345.00, 67890.00);
        Coord2D newLeftPinkyPosition = new Coord2D(12345.00, 67890.00);

        assertEquals(newLeftPinkyPosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_PINKY));
        assertEquals(newLeftPinkyPosition, fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_PINKY));
        // Check that getInitialFingerPosition doesn't mutate, only get.
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

    @Test
    public void testGetFingerAssignment() {
        assertEquals(Finger.LEFT_THUMB, fiveKeyLayout.getFingerAssignment(new Coord2D(4.00, 0.00)));
        assertEquals(Finger.LEFT_PINKY, staggeredNumpad.getFingerAssignment(new Coord2D(2.50, 2.00)));

        assertNull(fiveKeyLayout.getFingerAssignment(new Coord2D(3.50, 2.00)));
        assertNull(fiveKeyLayout.getFingerAssignment(new Coord2D(5.00, 0.00)));
    }

    @Test
    public void testToJson() {
        /*
         {
            numContactPoints: int,
            keys: [
                { x: double, y: double, finger: int },
                ...
            ],
            initialFingerPositions: [
                { x: double, y: double },
                ...
            ]
         }
         */
        JSONObject expectedFiveKeyLayout = new JSONObject();

        expectedFiveKeyLayout.put("numContactPoints", 5);

        JSONObject key1 = new JSONObject();
        JSONObject key2 = new JSONObject();
        JSONObject key3 = new JSONObject();
        JSONObject key4 = new JSONObject();
        JSONObject key5 = new JSONObject();

        key1.put("x", 0.00);
        key1.put("y", 0.00);
        key1.put("finger", Finger.LEFT_PINKY.getFingerIndex());

        key2.put("x", 1.00);
        key2.put("y", 0.00);
        key2.put("finger", Finger.LEFT_RING.getFingerIndex());

        key3.put("x", 2.00);
        key3.put("y", 0.00);
        key3.put("finger", Finger.LEFT_MIDDLE.getFingerIndex());

        key4.put("x", 3.00);
        key4.put("y", 0.00);
        key4.put("finger", Finger.LEFT_INDEX.getFingerIndex());

        key5.put("x", 4.00);
        key5.put("y", 0.00);
        key5.put("finger", Finger.LEFT_THUMB.getFingerIndex());

        JSONArray keys = new JSONArray();

        keys.put(key1);
        keys.put(key2);
        keys.put(key3);
        keys.put(key4);
        keys.put(key5);

        JSONArray fingerPositions = new JSONArray();

        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_PINKY).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_RING).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_MIDDLE).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_INDEX).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.LEFT_THUMB).toJson());

        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.RIGHT_PINKY).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.RIGHT_RING).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.RIGHT_MIDDLE).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.RIGHT_INDEX).toJson());
        fingerPositions.put(fiveKeyLayout.getInitialFingerPosition(Finger.RIGHT_THUMB).toJson());

        expectedFiveKeyLayout.put("keys", keys);
        expectedFiveKeyLayout.put("initialFingerPositions", fingerPositions);

        assertTrue(expectedFiveKeyLayout.similar(fiveKeyLayout.toJson()));
    }
}
