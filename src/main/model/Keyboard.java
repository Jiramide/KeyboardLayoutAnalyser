package model;

/*
 * A class that represents a Keyboard. Has a "shape" in the form of a KeybaordGeometry
 * and a layout in the form of a string.
 */
public class Keyboard {

    private KeyboardGeometry geometry;
    private Layout layout;

    // EFFECTS: Creates a new Keyboard with the assigned geometry and key layout
    public Keyboard(KeyboardGeometry geometry, Layout layout) {
        this.geometry = geometry;
        this.layout = layout;
    }

    // EFFECTS: returns the geometry of this keyboard
    public KeyboardGeometry getGeometry() {
        return geometry;
    }

    // EFFECTS: returns the layout of this keyboard
    public Layout getLayout() {
        return layout;
    }

    // EFFECTS: determines if the keyboard contains the key in it's layout
    public boolean hasKey(char key) {
        int firstIndex = layout.getIndexOfKey(key);
        // according to Java documentation, the method above returns -1
        // if the character never occurs in the string.

        return firstIndex != -1;
    }

    // REQUIRES: char is in layout
    // EFFECTS: returns the associated coordinate (according the geometry) of the key
    public Coord2D getKeyCoord(char key) {
        int keyIndex = layout.getIndexOfKey(key);

        return geometry.getCoord(keyIndex);
    }

    // REQUIRES: char is in layout
    // EFFECTS: returns the associated finger (according to the geometry) of the key
    public Finger getKeyFinger(char key) {
        Coord2D associatedCoordinate = getKeyCoord(key);

        return geometry.getFingerAssignment(associatedCoordinate);
    }

    // REQUIRES: finger has initial finger position
    // EFFECTS: returns the initial position of the finger (according to the geometry)
    public Coord2D getInitialFingerPosition(Finger finger) {
        return geometry.getInitialFingerPosition(finger);
    }
}
