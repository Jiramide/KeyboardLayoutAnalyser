package model;

import model.Coord2D;
import model.Finger;

import java.util.List;
import java.util.ArrayList;

public class KeyboardGeometry {

    private List<Coord2D> contactPoints;
    private List<Finger> fingerAssignments;

    public KeyboardGeometry() {
        contactPoints = new ArrayList<>();
        fingerAssignments = new ArrayList<>();
    }

    // REQUIRES: !geometry.isValidContactPoint(position)
    // MODIFIES: this
    // EFFECTS: add position as a contact point into this keyboard, using finger to press this key.
    public KeyboardGeometry withContactPoint(Coord2D position, Finger finger) {
        return null;
    }

    // REQUIRES: !geometry.isValidContactPoint(new Coord2D(positionX, positionY))
    // MODIFIES: this
    // EFFECTS: add position as a contact point into this keyboard, using finger to press this key.
    public KeyboardGeometry withContactPoint(double positionX, double positionY, Finger finger) {
        return null;
    }

    // EFFECTS: determines if a designated position is a valid contact point
    public boolean isValidContactPoint(Coord2D position) {
        return false;
    }

    // EFFECTS: determines if a designated position is a valid contact point
    public boolean isValidContactPoint(double positionX, double positionY) {
        return false;
    }

    // REQUIRES: geometry.isValidContactPoint(position)
    // EFFECTS: returns the corresponding finger used to press on the contact point / key
    public Finger getFingerAssignment(Coord2D position) {
        return null;
    }

    // REQUIRES: 0 <= index < contactPoints.size()
    // EFFECTS: returns the corresponding Coord2D with the position of index
    public Coord2D getCoord(int index) {
        return null;
    }

    // EFFECTS: returns the number of contact points in this geometry
    public int getNumContactPoints() {
        return 0;
    }
}
