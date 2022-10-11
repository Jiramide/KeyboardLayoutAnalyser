package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Finger;

public class FingerTest {

    @Test
    void isLeftHandTest() {
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

}
