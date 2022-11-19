package ui.gui.panels;

import model.Finger;
import model.KeyboardGeometry;
import ui.gui.App;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

import static ui.gui.MainWindow.Page;

/*
 * A class responsible for allowing the user to build KeyboardGeometries
 */
public class KeyboardGeometryCreationPanel extends JPanel {

    private static final double CHAR_SIZE_IN_UNITS = 0.1;

    private App app;
    private KeyboardGeometryMainPanel parent;
    private Scanner input;

    private JPanel header;
    private JButton backButton;
    private JLabel title;

    // EFFECTS: creates a KeyboardGeometryCreationPanel with the given app and parent
    public KeyboardGeometryCreationPanel(App app, KeyboardGeometryMainPanel parent) {
        this.app = app;
        this.parent = parent;
        input = app.getScanner();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.KeyboardGeometryMain);
        backButton.setText("<");

        title = new JLabel("  KeyboardGeometry");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        add(header);
        add(Box.createVerticalGlue());
        add(new JLabel("Look at your console"));
        add(Box.createVerticalGlue());
        setVisible(true);
    }

    // MODIFIES: parent
    // EFFECTS: visits the page, incurring a read operation
    public void visit() {
        read();
    }

    // EFFECTS: repeats a string a certain number of times and returns it
    private String repeatString(String str, int times) {
        String repeated = "";

        for (int index = 0; index < times; index++) {
            repeated += str;
        }

        return repeated;
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
                        index * CHAR_SIZE_IN_UNITS,
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
        int charsInASingleUnit = (int) (1.00 / CHAR_SIZE_IN_UNITS);

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

        int requiredLinesPerUnit = (int) (1.00 / CHAR_SIZE_IN_UNITS);
        int lineIndex = 0;
        double coordinateY = 0.00;
        boolean keepBuilding = true;

        while (keepBuilding) {
            printLineMarker(lineIndex, requiredLinesPerUnit);
            lineIndex += 1;

            keepBuilding = readKeyboardGeometryLine(constructedKeyboardGeometry, coordinateY);
            coordinateY += CHAR_SIZE_IN_UNITS;
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

    // MODIFIES: this
    // EFFECTS: reads a KeyboardGeometry from the console and adds it onto the list of available geometries
    public void read() {
        System.out.print("KeyboardGeometry name: ");
        String name = input.next();

        System.out.print("KeyboardGeometry description: ");
        String desc = input.next();

        System.out.println("Hello! To build a KeyboardGeometry, please follow the instructions");
        System.out.println("Each character is treated as " + CHAR_SIZE_IN_UNITS + " units. ");
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
        parent.addKeyboardGeometry(constructedKeyboardGeometry);

        System.out.println("Successfully added KeyboardGeometry '" + name + "'!");;
    }
}
