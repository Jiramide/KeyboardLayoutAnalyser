package model;

import java.util.ArrayList;

/*
 * An enum that represents a finger
 */
public enum Finger {

    LEFT_PINKY(0),
    LEFT_RING(1),
    LEFT_MIDDLE(2),
    LEFT_INDEX(3),
    LEFT_THUMB(4),

    RIGHT_PINKY(5),
    RIGHT_RING(6),
    RIGHT_MIDDLE(7),
    RIGHT_INDEX(8),
    RIGHT_THUMB(9);

    public static final Finger[] ALL_FINGERS = {
            LEFT_PINKY, LEFT_RING, LEFT_MIDDLE, LEFT_INDEX, LEFT_THUMB,
            RIGHT_PINKY, RIGHT_RING, RIGHT_MIDDLE, RIGHT_INDEX, RIGHT_THUMB,
    };

    private int fingerIndex;

    Finger(int fingerIndex) {
        this.fingerIndex = fingerIndex;
    }

    // EFFECTS: returns the finger with the given index (for parsing from file)
    public static Finger fromFingerIndex(int fingerIndex) {
        return ALL_FINGERS[fingerIndex];
    }

    // EFFECTS: determines whether the given finger belongs
    //          on the left hand
    public boolean isLeftHand() {
        switch (this) {
            case LEFT_PINKY:
            case LEFT_RING:
            case LEFT_MIDDLE:
            case LEFT_INDEX:
            case LEFT_THUMB:
                return true;
            default:
                return false;
        }
    }

    // EFFECTS: returns the finger index
    public int getFingerIndex() {
        return fingerIndex;
    }

}
