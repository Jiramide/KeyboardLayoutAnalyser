package ui.gui.panels;

import model.Coord2D;
import model.KeyboardGeometry;
import ui.gui.App;

import javax.swing.*;

import java.awt.*;

import static ui.gui.MainWindow.Page;

/*
 * A class resposible for displaying a keyboard geometry
 */
public class KeyboardGeometryViewPanel extends JPanel {

    private static final double CHAR_SIZE_IN_UNITS = 0.10;
    private static final double KEY_EQUALITY_EPSILON = 0.05;

    private App app;
    private KeyboardGeometryMainPanel parent;
    private KeyboardGeometry geometry;
    private JPanel header;
    private JButton backButton;
    private JLabel keyboardGeometryName;

    // EFFECTS: creates a keyboard geometry view panel with the given app and parent
    public KeyboardGeometryViewPanel(App app, KeyboardGeometryMainPanel parent) {
        this.app = app;
        this.parent = parent;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.KeyboardGeometryMain);
        backButton.setText("<");

        keyboardGeometryName = new JLabel("  KeyboardGeometry");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(keyboardGeometryName);
        header.add(Box.createHorizontalGlue());

        JLabel lookAtYourConsole = new JLabel("Look at your console ...");
        lookAtYourConsole.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(header);
        add(Box.createVerticalGlue());
        add(lookAtYourConsole);
        add(Box.createVerticalGlue());
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the geometry that this class is displaying
    public void setGeometry(KeyboardGeometry geometry) {
        this.geometry = geometry;
        printKeyboardGeometry(geometry);
        keyboardGeometryName.setText("  " + geometry.getName());
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

        int numCharForUnit = (int) (1.00 / CHAR_SIZE_IN_UNITS);

        System.out.print("\t\t|");
        System.out.print(repeatString("-", numCharForUnit - 1));
        System.out.println("|");

        System.out.print("\t\t");

        for (double currentY = minY; currentY <= maxY; currentY += CHAR_SIZE_IN_UNITS) {
            for (double currentX = minX; currentX <= maxX; currentX += CHAR_SIZE_IN_UNITS) {
                Coord2D currentPosition = new Coord2D(currentX, currentY);
                Coord2D closestKeyPosition = getNearestCoord2D(shape, currentX, currentY);

                if (closestKeyPosition.subtract(currentPosition).getMagnitude() < KEY_EQUALITY_EPSILON) {
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

        minX = roundToNearest(minX, CHAR_SIZE_IN_UNITS);
        minY = roundToNearest(minY, CHAR_SIZE_IN_UNITS);
        maxX = roundToNearest(maxX, CHAR_SIZE_IN_UNITS);
        maxY = roundToNearest(maxY, CHAR_SIZE_IN_UNITS);

        printKeyboardShape(geometry, minX, minY, maxX, maxY);
    }

}
