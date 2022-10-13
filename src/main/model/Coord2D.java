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
        return coordinateX;
    }

    public double getY() {
        return coordinateY;
    }

    public Coord2D add(Coord2D other) {
        return new Coord2D(getX() + other.getX(), getY() + other.getY());
    }

    public Coord2D subtract(Coord2D other) {
        return new Coord2D(getX() - other.getX(), getY() - other.getY());
    }

    public Coord2D scaleBy(double scalar) {
        return new Coord2D(scalar * getX(), scalar * getY());
    }

    public Coord2D negate() {
        return new Coord2D(-getX(), -getY());
    }

    public double getMagnitude() {
        double x = getX();
        double y = getY();

        return Math.sqrt(x * x + y * y);
    }

    public Coord2D getUnit() {
        double x = getX();
        double y = getY();
        double magnitude = getMagnitude();

        return new Coord2D(x / magnitude, y / magnitude);
    }

    public double dot(Coord2D other) {
        return getX() * other.getX() + getY() * other.getY();
    }

    @Override
    public boolean equals(Coord2D other) {
        return (getX() == other.getX()) && (getY() == other.getY());
    }
}
