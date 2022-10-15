package model;

public class Keyboard {

    private KeyboardGeometry geometry;
    private String layout;

    // EFFECTS: Creates a new Keyboard with the assigned geometry and key layout
    public Keyboard(KeyboardGeometry geometry, String layout) {

    }

    // EFFECTS: determines if the keyboard contains the key in it's layout
    public boolean hasKey(char key) {
        return false;
    }

    // REQUIRES: char is in layout
    // EFFECTS: returns the associated coordinate (according the geometry) of the key
    public Coord2D getKeyCoord(char key) {
        return null;
    }

    // REQUIRES: char is in layout
    // EFFECTS: returns the associated finger (according to the geometry) of the key
    public Finger getKeyFinger(char key) {
        return null;
    }

}
