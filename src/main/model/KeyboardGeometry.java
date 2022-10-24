package model;

import model.Coord2D;
import model.Finger;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/*
 * A class that represents the shape of a keyboard.
 * It holds geometry data through a list of points and the finger needed to hit that key.
 * You can imagine each entry as a button, with the coord being its position
 * and finger being the finger used to hit the button.
 */
public class KeyboardGeometry implements Writable {

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
        Coord2D position = new Coord2D(positionX, positionY);

        initialFingerPositions.put(finger, position);
    }

    // REQUIRES: finger has an initial position set by setInitialFingerPosition
    // EFFECTS: returns the initial position of the finger according to the geometry specified.
    public Coord2D getInitialFingerPosition(Finger finger) {
        return initialFingerPositions.getOrDefault(finger, new Coord2D());
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

    // EFFECTS: returns this keyboard geometry as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject keyboardGeometryJson = new JSONObject();

        int numContactPoints = getNumContactPoints();

        keyboardGeometryJson.put("numContactPoints", numContactPoints);

        JSONArray keys = new JSONArray();

        for (int keyIndex = 0; keyIndex < numContactPoints; keyIndex++) {
            Coord2D keyCoordinate = getCoord(keyIndex);
            Finger keyFinger = getFingerAssignment(keyCoordinate);

            JSONObject keyJson = keyCoordinate.toJson();
            keyJson.put("finger", keyFinger.getFingerIndex());
            keys.put(keyJson);
        }

        JSONArray fingerPositions = new JSONArray();

        for (int fingerIndex = 0; fingerIndex < 10; fingerIndex++) {
            Finger finger = Finger.fromFingerIndex(fingerIndex);

            Coord2D fingerInitialPosition = getInitialFingerPosition(finger);
            fingerPositions.put(fingerInitialPosition.toJson());
        }

        keyboardGeometryJson.put("keys", keys);
        keyboardGeometryJson.put("initialFingerPositions", fingerPositions);

        return keyboardGeometryJson;
    }
}
