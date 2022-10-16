package model;

import model.Coord2D;
import model.Finger;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class KeyboardGeometry {

    private List<Coord2D> contactPoints;
    private List<Finger> fingerAssignments;
    private Map<Finger, Coord2D> initialFingerPositions;

    // EFFECTS: constructs an empty KeyboardGeometry
    public KeyboardGeometry() {
        contactPoints = new ArrayList<>();
        fingerAssignments = new ArrayList<>();
        initialFingerPositions = new HashMap<>();
    }

    // REQUIRES: !geometry.isValidContactPoint(position)
    // MODIFIES: this
    // EFFECTS: add position as a contact point into this keyboard, using finger to press this key.
    public KeyboardGeometry withContactPoint(Coord2D position, Finger finger) {
        contactPoints.add(position);
        fingerAssignments.add(finger);

        return this;
    }

    // REQUIRES: !geometry.isValidContactPoint(new Coord2D(positionX, positionY))
    // MODIFIES: this
    // EFFECTS: add position as a contact point into this keyboard, using finger to press this key.
    public KeyboardGeometry withContactPoint(double positionX, double positionY, Finger finger) {
        return withContactPoint(new Coord2D(positionX, positionY), finger);
    }

    // MODIFIES: this
    // EFFECTS: sets the initial position of the finger as the given position (like setting the home row)
    public void setInitialFingerPosition(Finger finger, double positionX, double positionY) {

    }

    // EFFECTS: returns the initial position of the finger according to the geometry specified.
    public Coord2D getInitialFingerPosition(Finger finger) {
        return new Coord2D();
    }

    // EFFECTS: determines if a designated position is a valid contact point
    public boolean isValidContactPoint(Coord2D position) {
        return contactPoints.contains(position);
    }

    // EFFECTS: determines if a designated position is a valid contact point
    public boolean isValidContactPoint(double positionX, double positionY) {
        return isValidContactPoint(new Coord2D(positionX, positionY));
    }

    // REQUIRES: geometry.isValidContactPoint(position)
    // EFFECTS: returns the corresponding finger used to press on the contact point / key. In the case that
    //          the pre-condition is not satisfied, null is returned.
    public Finger getFingerAssignment(Coord2D position) {
        for (int index = 0; index < fingerAssignments.size(); index++) {
            if (contactPoints.get(index).equals(position)) {
                return fingerAssignments.get(index);
            }
        }

        return null;
    }

    // REQUIRES: 0 <= index < contactPoints.size()
    // EFFECTS: returns the corresponding Coord2D with the position of index
    public Coord2D getCoord(int index) {
        return contactPoints.get(index);
    }

    // EFFECTS: returns the number of contact points in this geometry
    public int getNumContactPoints() {
        return contactPoints.size();
    }
}
