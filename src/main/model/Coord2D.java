package model;

import org.json.JSONObject;
import persistence.Writable;

/* A class representing coordinates in two dimension
 * Implements common vector operations on 2 dimension
 */
public class Coord2D implements Writable {

    private final double coordinateX;
    private final double coordinateY;

    // EFFECTS: constructs a Coord2D located at the origin
    public Coord2D() {
        coordinateX = 0;
        coordinateY = 0;
    }

    // EFFECTS: constructs a Coord2D located at the position (x, y)
    public Coord2D(double x, double y) {
        coordinateX = x;
        coordinateY = y;
    }

    // EFFECTS: returns the X coordinate
    public double getX() {
        return coordinateX;
    }

    // EFFECTS: returns the Y coordinate
    public double getY() {
        return coordinateY;
    }

    // EFFECTS: adds together two Coord2Ds and returns the sum
    public Coord2D add(Coord2D other) {
        return new Coord2D(getX() + other.getX(), getY() + other.getY());
    }

    // EFFECTS: subtracts together two Coord2Ds and returns the difference
    public Coord2D subtract(Coord2D other) {
        return new Coord2D(getX() - other.getX(), getY() - other.getY());
    }

    // EFFECTS: scales the Coord2D by some scalar, lengthening it by a factor of scalar
    public Coord2D scaleBy(double scalar) {
        return new Coord2D(scalar * getX(), scalar * getY());
    }

    // EFFECTS: negates the compoennts of the Coord2D and returns the negated version
    public Coord2D negate() {
        return new Coord2D(-getX(), -getY());
    }

    // EFFECTS: returns the distance between the Coord2D's position and the origin
    public double getMagnitude() {
        double x = getX();
        double y = getY();

        return Math.sqrt(x * x + y * y);
    }

    // REQUIRES: getMagnitude() > 0.00
    // EFFECTS: returns a Coord2D with the same direction but a magnitude of 1
    public Coord2D getUnit() {
        double x = getX();
        double y = getY();
        double magnitude = getMagnitude();

        return new Coord2D(x / magnitude, y / magnitude);
    }

    // EFFECTS: returns the dot product of two Coord2Ds, defined as the sum of the products of components
    public double dot(Coord2D other) {
        return getX() * other.getX() + getY() * other.getY();
    }

    @Override
    // EFFECTS: determines if this and another object are equal
    public boolean equals(Object obj) {
        boolean isCoord2D = obj instanceof Coord2D;

        // reference checking
        if (this == obj) {
            return true;
        }

        // type checking
        if (!isCoord2D) {
            return false;
        }

        Coord2D other = (Coord2D) obj; // safe

        return (getX() == other.getX()) && (getY() == other.getY());
    }

    @Override
    // EFFECTS: returns this Coord2D as a JSONObject
    public JSONObject toJson() {
        JSONObject coord2DJson = new JSONObject();

        coord2DJson.put("x", coordinateX);
        coord2DJson.put("y", coordinateY);

        return coord2DJson;
    }
}
