package model;

// A class representing coordinates in two dimension
// Implements common vector operations on 2 dimension
public class Coord2D {

    private final double coordinateX;
    private final double coordinateY;

    public Coord2D() {
        // stub
        coordinateX = 0;
        coordinateY = 0;
    }

    public Coord2D(double x, double y) {
        coordinateX = x;
        coordinateY = y;
    }

    public double getX() {
        return 0; // stub
    }

    public double getY() {
        return 0; // stub
    }

    public Coord2D add(Coord2D other) {
        return null; // stub
    }

    public Coord2D subtract(Coord2D other) {
        return null; // stub
    }

    public Coord2D scaleBy(double scalar) {
        return null; // stub
    }

    public Coord2D negate() {
        return null; // stub
    }

    public double getMagnitude() {
        return 0; // stub
    }

    public Coord2D getUnit() {
        return null;
    }

    public double dot(Coord2D other) {
        return 0;
    }
}
