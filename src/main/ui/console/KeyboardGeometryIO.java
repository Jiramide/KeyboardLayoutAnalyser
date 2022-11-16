package ui.console;

import model.Coord2D;
import model.Finger;
import model.KeyboardGeometry;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * A class responsible for IO when it comes to KeyboardGeometries
 */
public class KeyboardGeometryIO extends InputOutput<KeyboardGeometry> {

    private final double charSizeInUnits;
    private final double keyEqualityEpsilon;

    private Scanner input;

    // EFFECTS: constructs a KeyboardGeometryIO with the given scanner input, charSizeInUnits, keyEqualityEpsilon
    public KeyboardGeometryIO(Scanner input, double charSizeInUnits, double keyEqualityEpsilon) {
        super(input);

        this.charSizeInUnits = charSizeInUnits;
        this.keyEqualityEpsilon = keyEqualityEpsilon;
        this.input = input;
    }

    @Override
    // EFFECTS: writes a single KeyboardGeometry into the console.
    protected void write(int index, KeyboardGeometry keyboardGeometry) {
        String name = keyboardGeometry.getName();
        String desc = keyboardGeometry.getDescription();

        // print name
        System.out.print("\t");
        System.out.print(index);
        System.out.print(". ");
        System.out.println(name);

        // print description
        System.out.print("\t\t");
        System.out.println(desc);

        // print keyboard geometry
        printKeyboardGeometry(keyboardGeometry);
    }

    // EFFECTS: rounds the given number to the nearest place
    private double roundToNearest(double num, double place) {
        return Math.floor(num / place + 0.5) * place;
    }

    // REQUIRES: shape.getNumContactPoints() > 0
    // EFFECTS: returns the nearest Coord2D in the geometry from (coordX, coordY)
    private Coord2D getNearestCoord2D(KeyboardGeometry shape, double coordX, double coordY) {
        Coord2D closest = shape.getCoord(0);
        Coord2D coordinate = new Coord2D(coordX, coordY);
        double minDistance = closest.subtract(coordinate).getMagnitude();

        for (int index = 1; index < shape.getNumContactPoints(); index++) {
            Coord2D currentCoordinate = shape.getCoord(index);
            double distance = currentCoordinate.subtract(coordinate).getMagnitude();

            if (distance < minDistance) {
                closest = currentCoordinate;
                minDistance = distance;
            }
        }

        return closest;
    }

    // EFFECTS: repeats a string a certain number of times and returns it
    private String repeatString(String str, int times) {
        String repeated = "";

        for (int index = 0; index < times; index++) {
            repeated += str;
        }

        return repeated;
    }

    // EFFECTS: prints out the shape of the keyboard geometry
    private void printKeyboardShape(KeyboardGeometry shape, double minX, double minY, double maxX, double maxY) {
        System.out.print("\n");
        System.out.println("\t\t 1.00 unit");

        int numCharForUnit = (int) (1.00 / charSizeInUnits);

        System.out.print("\t\t|");
        System.out.print(repeatString("-", numCharForUnit - 1));
        System.out.println("|");

        System.out.print("\t\t");

        for (double currentY = minY; currentY <= maxY; currentY += charSizeInUnits) {
            for (double currentX = minX; currentX <= maxX; currentX += charSizeInUnits) {
                Coord2D currentPosition = new Coord2D(currentX, currentY);
                Coord2D closestKeyPosition = getNearestCoord2D(shape, currentX, currentY);

                if (closestKeyPosition.subtract(currentPosition).getMagnitude() < keyEqualityEpsilon) {
                    System.out.print(shape.getFingerAssignment(closestKeyPosition).getFingerIndex());
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
        System.out.print(finger + "'s " + coordinateName + " position: ");
        return input.nextDouble();
    }

    // EFFECTS: prints out a line designating the length o a unit
    private void printUnit() {
        int charsInASingleUnit = (int) (1.00 / charSizeInUnits);

        System.out.print("  ");

        for (int index = 0; index < 10; index++) {
            System.out.print("|");
            System.out.print(repeatString("-",charsInASingleUnit - 1));
        }

        System.out.println("|...");
    }

    // EFFECTS: constructs the keyboard geometry and builds the geometry line by line
    //          the constructed keyboard geometry has the given name and description
    private KeyboardGeometry readKeyboardGeometry(String name, String desc) {
        printUnit();

        KeyboardGeometry constructedKeyboardGeometry = new KeyboardGeometry(name, desc);

        int requiredLinesPerUnit = (int) (1.00 / charSizeInUnits);
        int lineIndex = 0;
        double coordinateY = 0.00;
        boolean keepBuilding = true;

        while (keepBuilding) {
            printLineMarker(lineIndex, requiredLinesPerUnit);
            lineIndex += 1;

            keepBuilding = readKeyboardGeometryLine(constructedKeyboardGeometry, coordinateY);
            coordinateY += charSizeInUnits;
        }

        System.out.println("");

        for (Finger finger : Finger.values()) {
            double fingerX = queryInitialFinger("X", finger);
            double fingerY = queryInitialFinger("Y", finger);

            constructedKeyboardGeometry.setInitialFingerPosition(finger, fingerX, fingerY);
        }

        return constructedKeyboardGeometry;
    }

    // EFFECTS: prints out the vertical line marker, denoting a unit with -
    private void printLineMarker(int lineIndex, int requiredLinesPerUnit) {
        if (lineIndex % requiredLinesPerUnit == 0) {
            System.out.print("- ");
        } else {
            System.out.print("| ");
        }
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

        KeyboardGeometry constructedKeyboardGeometry = readKeyboardGeometry(name, desc);
        add(constructedKeyboardGeometry);

        System.out.println("Successfully added KeyboardGeometry '" + name + "'!");;
    }

    // MODIFIES: this
    // EFFECTS: reads a file, parses it into a KeyboardGeometry and stores it
    public void loadFromFile(String name) {
        Path path = Paths.get(".", "data", "KeyboardGeometries", name + ".kg.json");
        Reader reader = new Reader(path.toString());

        try {
            KeyboardGeometry loaded = reader.readKeyboardGeometry();
            add(loaded);
        } catch (IOException e) {
            System.out.println("[ERROR]: That wasn't a valid file!");
        } catch (JSONException e) {
            System.out.println("[ERROR]: The file you are reading is not a Corpus.");
        }
    }

    // EFFECTS: writes the KeyboardGeometry with the given name to file
    public void saveToFile(String name) {
        KeyboardGeometry toSave = getByName(name);
        Path path = Paths.get(".", "data", "KeyboardGeometries", name + ".kg.json");
        Writer writer = new Writer(path.toString());

        try {
            writer.open();
            writer.write(toSave);
        } catch (IOException e) {
            System.out.println("[ERROR]: Something has gone wrong while saving a Corpus.");
        }
    }
}
