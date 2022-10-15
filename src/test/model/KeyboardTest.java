package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KeyboardTest {

    // Common layouts
    private final String qwerty = "qwertyuiopasdfghjkl;zxcvbnm,./ ";
    private final String colemakDH = "qwfpbjluy;arstgmneioxcdvzkh,./ ";
    // Keyboard geometries
    private KeyboardGeometry standardAlphaKeyboard;
    private KeyboardGeometry columnarKeyboard;

    // Derived keyboards
    private Keyboard qwertyStandard;
    private Keyboard qwertyColumnar;
    private Keyboard colemakDHStandard;
    private Keyboard colemakDHColumnar;

    @BeforeEach
    public void constructStandardKeyboard() {
        standardAlphaKeyboard = new KeyboardGeometry();

        // Construct standard keyboard, with Q as the origin
        // Insert top row
        standardAlphaKeyboard
                .withContactPoint(0.00, 0.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, 0.00, Finger.LEFT_RING)
                .withContactPoint(2.00, 0.00, Finger.LEFT_MIDDLE)
                .withContactPoint(3.00, 0.00, Finger.LEFT_INDEX)
                .withContactPoint(4.00, 0.00, Finger.LEFT_INDEX)
                .withContactPoint(5.00, 0.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.00, 0.00, Finger.RIGHT_INDEX)
                .withContactPoint(7.00, 0.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.00, 0.00, Finger.RIGHT_RING)
                .withContactPoint(9.00, 0.00, Finger.RIGHT_PINKY);

        // Insert middle row
        standardAlphaKeyboard
                .withContactPoint(0.20, 1.00, Finger.LEFT_PINKY)
                .withContactPoint(1.20, 1.00, Finger.LEFT_RING)
                .withContactPoint(2.20, 1.00, Finger.LEFT_MIDDLE)
                .withContactPoint(3.20, 1.00, Finger.LEFT_INDEX)
                .withContactPoint(4.20, 1.00, Finger.LEFT_INDEX)
                .withContactPoint(5.20, 1.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.20, 1.00, Finger.RIGHT_INDEX)
                .withContactPoint(7.20, 1.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.20, 1.00, Finger.RIGHT_RING)
                .withContactPoint(9.20, 1.00, Finger.RIGHT_PINKY);

        // Insert bottom row
        standardAlphaKeyboard
                .withContactPoint(0.50, 2.00, Finger.LEFT_PINKY)
                .withContactPoint(1.50, 2.00, Finger.LEFT_RING)
                .withContactPoint(2.50, 2.00, Finger.LEFT_MIDDLE)
                .withContactPoint(3.50, 2.00, Finger.LEFT_INDEX)
                .withContactPoint(4.50, 2.00, Finger.LEFT_INDEX)
                .withContactPoint(5.50, 2.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.50, 2.00, Finger.RIGHT_INDEX)
                .withContactPoint(7.50, 2.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.50, 2.00, Finger.RIGHT_RING)
                .withContactPoint(9.50, 2.00, Finger.RIGHT_PINKY);

        // Insert space bar
        standardAlphaKeyboard.withContactPoint(2.50, 3.00, Finger.LEFT_THUMB);
    }

    @BeforeEach
    public void constructColumnarKeyboard() {
        // If you are not sure what a Columnar keyboard is, refer to this image
        // https://i.pinimg.com/originals/86/6e/f4/866ef4ec2f59afb3622abf61ad2664ae.jpg
        // The geometry described in this method is not angled or split, so it looks
        // like a matrix layout with column staggering.
        // The column offset follows this pattern: +0.00, -0.20, -0.50, -0.20, +0.00

        columnarKeyboard = new KeyboardGeometry();

        // Construct a columnar keyboard with Q as the origin.
        // Insert top row
        columnarKeyboard
                .withContactPoint(0.00, 0.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, -0.20, Finger.LEFT_RING)
                .withContactPoint(2.00, -0.50, Finger.LEFT_MIDDLE)
                .withContactPoint(3.00, -0.20, Finger.LEFT_INDEX)
                .withContactPoint(4.00, 0.00, Finger.LEFT_INDEX)
                .withContactPoint(5.00, 0.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.00, -0.20, Finger.RIGHT_INDEX)
                .withContactPoint(7.00, -0.50, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.00, -0.20, Finger.RIGHT_RING)
                .withContactPoint(9.00, 0.00, Finger.RIGHT_PINKY);

        // Insert middle row
        columnarKeyboard
                .withContactPoint(0.00, 1.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, 0.80, Finger.LEFT_RING)
                .withContactPoint(2.00, 0.50, Finger.LEFT_MIDDLE)
                .withContactPoint(3.00, 0.80, Finger.LEFT_INDEX)
                .withContactPoint(4.00, 1.00, Finger.LEFT_INDEX)
                .withContactPoint(5.00, 1.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.00, 0.80, Finger.RIGHT_INDEX)
                .withContactPoint(7.00, 0.50, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.00, 0.80, Finger.RIGHT_RING)
                .withContactPoint(9.00, 1.00, Finger.RIGHT_PINKY);

        // Insert bottom row
        columnarKeyboard
                .withContactPoint(0.00, 2.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, 1.80, Finger.LEFT_RING)
                .withContactPoint(2.00, 1.50, Finger.LEFT_MIDDLE)
                .withContactPoint(3.00, 1.80, Finger.LEFT_INDEX)
                .withContactPoint(4.00, 2.00, Finger.LEFT_INDEX)
                .withContactPoint(5.00, 2.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.00, 1.80, Finger.RIGHT_INDEX)
                .withContactPoint(7.00, 1.50, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.00, 1.80, Finger.RIGHT_RING)
                .withContactPoint(9.00, 2.00, Finger.RIGHT_PINKY);

        // Insert space bar
        // (note: space bar positions for columnar is very variable,
        // for this layout, I'll simply treat it as if it was located at the bottom
        // of all the other keys)
        columnarKeyboard.withContactPoint(3.00, 3.00, Finger.LEFT_THUMB);
    }

    @BeforeEach
    public void initializeKeyboard() {
        qwertyStandard = new Keyboard(standardAlphaKeyboard, qwerty);
        qwertyColumnar = new Keyboard(columnarKeyboard, qwerty);

        colemakDHStandard = new Keyboard(standardAlphaKeyboard, colemakDH);
        colemakDHColumnar = new Keyboard(columnarKeyboard, colemakDH);
    }

    @Test
    public void testHasKey() {
        assertTrue(qwertyStandard.hasKey('q'));
        assertTrue(qwertyColumnar.hasKey('g'));

        // Test difference in geometry does not change
        // existence of keys (which should only be determined
        // by layout)
        assertTrue(colemakDHStandard.hasKey('p'));
        assertTrue(colemakDHColumnar.hasKey('p'));

        assertFalse(qwertyStandard.hasKey('0'));
        assertFalse(qwertyColumnar.hasKey('!'));
        assertFalse(colemakDHStandard.hasKey('+'));
        assertFalse(colemakDHColumnar.hasKey('+'));
    }

    @Test
    public void testGetKeyCoord() {
        assertEquals(new Coord2D(0.00, 0.00), qwertyStandard.getKeyCoord('q'));
        assertEquals(new Coord2D(0.00, 1.00), qwertyColumnar.getKeyCoord('a'));
        assertEquals(new Coord2D(5.00, 0.00), colemakDHColumnar.getKeyCoord('j'));
        assertEquals(new Coord2D(5.00, 0.00), qwertyColumnar.getKeyCoord('y'));
        assertEquals(new Coord2D(3.00, 1.80), qwertyColumnar.getKeyCoord('v'));
        assertEquals(new Coord2D(3.50, 2.00), colemakDHStandard.getKeyCoord('v'));

        assertEquals(new Coord2D(3.00, 3.00), colemakDHColumnar.getKeyCoord(' '));
        assertEquals(new Coord2D(2.50, 3.00), colemakDHStandard.getKeyCoord(' '));
    }

    @Test
    public void testGetFinger() {
        assertEquals(Finger.LEFT_PINKY, qwertyStandard.getKeyFinger('q'));
        assertEquals(Finger.LEFT_PINKY, qwertyColumnar.getKeyFinger('a'));
        assertEquals(Finger.RIGHT_INDEX, colemakDHColumnar.getKeyFinger('j'));
        assertEquals(Finger.RIGHT_INDEX, qwertyColumnar.getKeyFinger('y'));
        assertEquals(Finger.LEFT_INDEX, qwertyColumnar.getKeyFinger('v'));
        assertEquals(Finger.LEFT_INDEX, colemakDHStandard.getKeyFinger('v'));
    }
}
