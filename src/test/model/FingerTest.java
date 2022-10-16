package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Finger;

public class FingerTest {

    @Test
    public void isLeftHandTest() {
        assertTrue(Finger.LEFT_PINKY.isLeftHand());
        assertTrue(Finger.LEFT_RING.isLeftHand());
        assertTrue(Finger.LEFT_MIDDLE.isLeftHand());
        assertTrue(Finger.LEFT_INDEX.isLeftHand());
        assertTrue(Finger.LEFT_THUMB.isLeftHand());

        assertFalse(Finger.RIGHT_PINKY.isLeftHand());
        assertFalse(Finger.RIGHT_RING.isLeftHand());
        assertFalse(Finger.RIGHT_MIDDLE.isLeftHand());
        assertFalse(Finger.RIGHT_INDEX.isLeftHand());
        assertFalse(Finger.RIGHT_THUMB.isLeftHand());
    }

    @Test
    public void testFromIndex() {
        assertEquals(Finger.LEFT_PINKY, Finger.fromFingerIndex(0));
        assertEquals(Finger.LEFT_RING, Finger.fromFingerIndex(1));
        assertEquals(Finger.LEFT_MIDDLE, Finger.fromFingerIndex(2));
        assertEquals(Finger.LEFT_INDEX, Finger.fromFingerIndex(3));
        assertEquals(Finger.LEFT_THUMB, Finger.fromFingerIndex(4));
        assertEquals(Finger.RIGHT_PINKY, Finger.fromFingerIndex(5));
        assertEquals(Finger.RIGHT_RING, Finger.fromFingerIndex(6));
        assertEquals(Finger.RIGHT_MIDDLE, Finger.fromFingerIndex(7));
        assertEquals(Finger.RIGHT_INDEX, Finger.fromFingerIndex(8));
        assertEquals(Finger.RIGHT_THUMB, Finger.fromFingerIndex(9));
    }

    @Test
    public void testGetIndex() {
        assertEquals(0, Finger.LEFT_PINKY.getFingerIndex());
        assertEquals(1, Finger.LEFT_RING.getFingerIndex());
        assertEquals(2, Finger.LEFT_MIDDLE.getFingerIndex());
        assertEquals(3, Finger.LEFT_INDEX.getFingerIndex());
        assertEquals(4, Finger.LEFT_THUMB.getFingerIndex());
        assertEquals(5, Finger.RIGHT_PINKY.getFingerIndex());
        assertEquals(6, Finger.RIGHT_RING.getFingerIndex());
        assertEquals(7, Finger.RIGHT_MIDDLE.getFingerIndex());
        assertEquals(8, Finger.RIGHT_INDEX.getFingerIndex());
        assertEquals(9, Finger.RIGHT_THUMB.getFingerIndex());
    }

}
