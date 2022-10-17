package ui;

import model.Coord2D;
import model.Finger;
import model.KeyboardGeometry;

import java.util.Scanner;

public class KeyboardGeometryIO extends InputOutput<KeyboardGeometry> {

    private final double charSizeInUnits;

    private Scanner input;

    public KeyboardGeometryIO(Scanner input, double charSizeInUnits) {
        super();

        this.charSizeInUnits = charSizeInUnits;
        this.input = input;
    }

    @Override
    protected void write(int index, Display<KeyboardGeometry> objectToWrite) {
        String name = objectToWrite.getName();
        String desc = objectToWrite.getDescription();
        KeyboardGeometry geometry = objectToWrite.getAssociatedObject();

        // print name
        System.out.print("\t");
        System.out.print(index);
        System.out.print(". ");
        System.out.println(name);

        // print description
        System.out.print("\t\t");
        System.out.println(desc);

        // print keyboard geometry
        printKeyboardGeometry(geometry);
    }

    // EFFECTS: rounds the given number to the nearest place
    private double roundToNearest(double num, double place) {
        return Math.floor(num / place + 0.5) * place;
    }

    // EFFECTS: prints out the shape of the keyboard geometry
    private void printKeyboardShape(KeyboardGeometry shape, double minX, double minY, double maxX, double maxY) {
        System.out.print("\n");
        System.out.println("\t\t 1.00 unit");

        int numCharForUnit = (int) (1.00 / charSizeInUnits);

        System.out.print("\t\t|");
        System.out.print(("-").repeat(numCharForUnit - 1));
        System.out.println("|");

        System.out.print("\t\t");

        for (double currentY = minY; currentY <= maxY; currentY += charSizeInUnits) {
            for (double currentX = minX; currentX <= maxX; currentX += charSizeInUnits) {
                if (shape.isValidContactPoint(currentX, currentY)) {
                    System.out.print(shape.getFingerAssignment(new Coord2D(currentX, currentY)).getFingerIndex());
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n\t\t");
        }

        System.out.print("\n");
    }

    // EFFECTS: determines the bounding box of the geometry then prints out the shape
    private void printKeyboardGeometry(KeyboardGeometry geometry) {
        double minX = 99999.00;
        double minY = 99999.00;
        double maxX = -99999.00;
        double maxY = -99999.00;

        for (int index = 0; index < geometry.getNumContactPoints(); index++) {
            Coord2D keyCoordinate = geometry.getCoord(index);

            minX = Math.min(minX, keyCoordinate.getX());
            minY = Math.min(minY, keyCoordinate.getY());
            maxX = Math.max(maxX, keyCoordinate.getX());
            maxY = Math.max(maxY, keyCoordinate.getY());
        }

        minX = roundToNearest(minX, charSizeInUnits);
        minY = roundToNearest(minY, charSizeInUnits);
        maxX = roundToNearest(maxX, charSizeInUnits);
        maxY = roundToNearest(maxY, charSizeInUnits);

        printKeyboardShape(geometry, minX, minY, maxX, maxY);
    }

    // EFFECTS: constructs a single row for KeyboardGeometry and returns whether to keep building rows
    private boolean readKeyboardGeometryLine(KeyboardGeometry geometryToBuild, double coordinateY) {
        String keyboardGeometryLine = input.next();

        for (int index = 0; index < keyboardGeometryLine.length(); index++) {
            char currentChar = keyboardGeometryLine.charAt(index);

            if (currentChar == 'q') {
                return false;
            } else if (currentChar != ' ') {
                int fingerIndex = (int) currentChar - '0';

                geometryToBuild.withContactPoint(
                        index * charSizeInUnits,
                        coordinateY,
                        Finger.fromFingerIndex(fingerIndex)
                );
            }
        }

        return true;
    }

    // EFFECTS: queries a position for a finger
    private double queryInitialFinger(String coordinateName, Finger finger) {
        System.out.println(finger + "'s " + coordinateName + " position: ");
        return input.nextDouble();
    }

    // EFFECTS: constructs the keyboard geometry and builds the geometry line by line
    private KeyboardGeometry readKeyboardGeometry() {
        KeyboardGeometry constructedKeyboardGeometry = new KeyboardGeometry();

        double coordinateY = 0.00;
        boolean keepBuilding = true;

        while (keepBuilding) {
            keepBuilding = readKeyboardGeometryLine(constructedKeyboardGeometry, coordinateY);
            coordinateY += charSizeInUnits;
        }

        System.out.println("");

        for (Finger finger : Finger.values()) {
            double fingerX = queryInitialFinger("X", finger);
            double fingerY = queryInitialFinger("Y", finger);

            constructedKeyboardGeometry.setInitialFingerPosition(
                    finger,
                    fingerX,
                    fingerY
            );
        }

        return constructedKeyboardGeometry;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: reads a KeyboardGeometry from the console and adds it onto the list of available geometries
    public void read() {
        System.out.print("KeyboardGeometry name: ");
        String name = input.next();

        System.out.print("KeyboardGeometry description: ");
        String desc = input.next();

        System.out.println("Hello! To build a KeyboardGeometry, please follow the instructions");
        System.out.println("Each character is treated as " + charSizeInUnits + " units. ");
        System.out.println("Each line is considered a single row in the KeyboardGeometry");
        System.out.println("If you want to insert a key at a specific position, type in a number from 0 to 9");
        System.out.println("where the number represents the finger used to press the key.\n");
        System.out.println("\t0: left pinky   | 5: right pinky");
        System.out.println("\t1: left ring    | 6: right ring");
        System.out.println("\t2: left middle  | 7: right middle");
        System.out.println("\t3: left index   | 8: right index");
        System.out.println("\t4: left thumb   | 9: right thumb");
        System.out.println("\nOnce you're done, simple type 'q' and enter, and the geometry is constructed.\n");

        KeyboardGeometry constructedKeyboardGeometry = readKeyboardGeometry();
        Display<KeyboardGeometry> newKeyboardGeometry = new Display<>(name, desc, constructedKeyboardGeometry);
        add(newKeyboardGeometry);

        System.out.println("Successfully added KeyboardGeometry '" + name + "'!");;
    }
}
