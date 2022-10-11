package model;

public enum Finger {

    LEFT_PINKY,
    LEFT_RING,
    LEFT_MIDDLE,
    LEFT_INDEX,
    LEFT_THUMB,

    RIGHT_PINKY,
    RIGHT_RING,
    RIGHT_MIDDLE,
    RIGHT_INDEX,
    RIGHT_THUMB;

    // effects: determines whether the given finger belongs
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

}
