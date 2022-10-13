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
        contactPoints.add(position);
        fingerAssignments.add(finger);

        return this;
    }

    // REQUIRES: !geometry.isValidContactPoint(new Coord2D(positionX, positionY))
    // MODIFIES: this
    // EFFECTS: add position as a contact point into this keyboard, using finger to press this key.
    public KeyboardGeometry withContactPoint(double positionX, double positionY, Finger finger) {
        return withContactPoint(
                new Coord2D(positionX, positionY),
                finger
        );
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
    // EFFECTS: returns the corresponding finger used to press on the contact point / key
    public Finger getFingerAssignment(Coord2D position) {
        int index = 0;

        for (; index < fingerAssignments.size(); index++) {
            if (contactPoints.get(index).equals(position)) {
                break;
            }
        }

        return fingerAssignments.get(index);
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
