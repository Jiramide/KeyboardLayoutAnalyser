package ui;

import model.Coord2D;
import model.Finger;
import model.KeyboardGeometry;
import model.Tournament;
import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;
import model.effortmodel.EffortModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class KeyboardLayoutAnalyserApp {

    private static final double CHAR_SIZE_IN_UNITS = 0.20;
    private static final int MAX_CORPORA_DISPLAY = 500;

    private List<Display<Corpus>> corpora;
    private List<Display<KeyboardGeometry>> keyboardGeometries;
    private List<Display<String>> layouts;
    private List<Display<EffortModel>> effortModels;

    private boolean keepTakingCommands;
    private Scanner scanner;

    public KeyboardLayoutAnalyserApp() {
        keepTakingCommands = true;

        corpora = new ArrayList<>();
        keyboardGeometries = new ArrayList<>();
        layouts = new ArrayList<>();

        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        runCommands();
    }

    private void runCommands() {
        displayMenu();

        String command;

        while (keepTakingCommands) {
            displayCommands();

            command = scanner.next();
            command = command.toLowerCase(Locale.ROOT);

            processCommand(command);
        }
    }

    private void processCommand(String command) {
        if (command.equals("vc")) {
            viewCorpora();
        } else if (command.equals("ac")) {
            addCorpus();
        } else if (command.equals("vk")) {
            viewKeyboardGeometries();
        } else if (command.equals("ak")) {
            addKeyboardGeometry();
        } else if (command.equals("vl")) {
            viewLayouts();
        } else if (command.equals("al")) {
            addLayout();
        } else if (command.equals("ve")) {
            viewEffortModels();
        } else if (command.equals("t")) {
            createTournament();
        } else {
            alertNotValidCommand(command);
        }
    }

    private void viewEffortModels() {

    }

    private String queryName(String type) {
        System.out.print("Name of new " + type + ": ");
        return scanner.next();
    }

    private void createTournament() {
        System.out.println("Which corpus? ");
        int corpusIndex = scanner.nextInt();

        Tournament currentTournament = new Tournament();
    }

    private void alertSuccessAddition(String type, String name) {
        System.out.println("Successfully added " + type + " '" + name + "'!");
    }

    private void addLayout() {
        String name = queryName("Layout");

        System.out.print("Type in the order of the key positions. Note that this program will fill in");
        System.out.println(" keyboard geometries line-by-line, left-to-right.");

        String layout = scanner.next();
        Display<String> newLayout = new Display<>(name, layout);
        layouts.add(newLayout);

        alertSuccessAddition("Layout", name);
    }

    private void viewLayouts() {
        System.out.println("Layouts: ");
        int index = 1;

        for (Display<String> layout : layouts) {
            System.out.println("\t" + Integer.toString(index) + ". " + layout.getName());
            System.out.print("\t\t");

            System.out.println(layout.getAssociatedObject());

            index += 1;
        }
    }

    private boolean readKeyboardGeometryLine(KeyboardGeometry geometryToBuild, double coordinateY) {
        String keyboardGeometryLine = scanner.next();

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

    private double queryInitialFinger(String coordinateName, Finger finger) {
        System.out.println(finger + "'s " + coordinateName + " position: ");
        return scanner.nextDouble();
    }

    private KeyboardGeometry readKeyboardGeometry() {
        KeyboardGeometry constructedKeyboardGeometry = new KeyboardGeometry();

        double coordinateY = 0.00;
        boolean keepBuilding = true;

        while (keepBuilding) {
            keepBuilding = readKeyboardGeometryLine(constructedKeyboardGeometry, coordinateY);
            coordinateY += CHAR_SIZE_IN_UNITS;
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

    private void addKeyboardGeometry() {
        String name = queryName("KeyboardGeometry");

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

        KeyboardGeometry constructedKeyboardGeometry = readKeyboardGeometry();
        Display<KeyboardGeometry> newKeyboardGeoemetry = new Display<>(name, constructedKeyboardGeometry);
        keyboardGeometries.add(newKeyboardGeoemetry);

        alertSuccessAddition("KeyboardGeometry", name);
    }

    private double roundToNearest(double num, double place) {
        return Math.floor(num / place + 0.5) * place;
    }

    private void printKeyboardShape(KeyboardGeometry shape, double minX, double minY, double maxX, double maxY) {
        System.out.println(" 1.00 unit");

        int numCharForUnit = (int) (1.00 / CHAR_SIZE_IN_UNITS);

        System.out.print("\t\t|");
        System.out.print(("-").repeat(numCharForUnit - 1));
        System.out.println("|");

        System.out.print("\t\t");

        for (double currentY = minY; currentY <= maxY; currentY += CHAR_SIZE_IN_UNITS) {
            for (double currentX = minX; currentX <= maxX; currentX += CHAR_SIZE_IN_UNITS) {
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

    private void printKeyboardGeometry(Display<KeyboardGeometry> geometry) {
        KeyboardGeometry keyboardShape = geometry.getAssociatedObject();

        double minX = 99999.00;
        double minY = 99999.00;
        double maxX = -99999.00;
        double maxY = -99999.00;

        for (int index = 0; index < keyboardShape.getNumContactPoints(); index++) {
            Coord2D keyCoordinate = keyboardShape.getCoord(index);

            minX = Math.min(minX, keyCoordinate.getX());
            minY = Math.min(minY, keyCoordinate.getY());
            maxX = Math.max(maxX, keyCoordinate.getX());
            maxY = Math.max(maxY, keyCoordinate.getY());
        }

        minX = roundToNearest(minX, CHAR_SIZE_IN_UNITS);
        minY = roundToNearest(minY, CHAR_SIZE_IN_UNITS);
        maxX = roundToNearest(maxX, CHAR_SIZE_IN_UNITS);
        maxY = roundToNearest(maxY, CHAR_SIZE_IN_UNITS);

        printKeyboardShape(keyboardShape, minX, minY, maxX, maxY);
    }

    private void viewKeyboardGeometries() {
        System.out.println("KeyboardGeometries: ");
        int index = 1;

        for (Display<KeyboardGeometry> geometry : keyboardGeometries) {
            System.out.println("\t" + Integer.toString(index) + ". " + geometry.getName());
            System.out.print("\t\t");

            printKeyboardGeometry(geometry);

            index += 1;
        }
    }

    private void addCorpus() {
        String name = queryName("Corpus");

        System.out.print("Content: ");
        String corpusContent = scanner.next();

        Display<Corpus> newCorpus = new Display<>(name, new StringCorpus(corpusContent));
        corpora.add(newCorpus);

        alertSuccessAddition("Corpus", name);
    }

    private void printCorpusContent(Display<Corpus> corpus) {
        CorpusReader reader = corpus.getAssociatedObject().createCorpusReader();

        int charactersConsumed = 0;
        while (!reader.isFinished() && charactersConsumed <= MAX_CORPORA_DISPLAY) {
            charactersConsumed += 1;
            System.out.print(reader.consume());
        }

        if (charactersConsumed < MAX_CORPORA_DISPLAY) {
            System.out.println("");
        } else {
            System.out.println(". . .");
        }
    }

    private void viewCorpora() {
        System.out.println("Corpora: ");
        int index = 1;

        for (Display<Corpus> corpus : corpora) {
            System.out.println("\t" + Integer.toString(index) + ". " + corpus.getName());
            System.out.print("\t\t");

            printCorpusContent(corpus);

            index += 1;
        }
    }

    private void alertNotValidCommand(String command) {
        System.out.println("Warning! " + command + " is not a valid command. Please refer below for commands:");
        displayCommands();
    }

    private void displayCommands() {
        System.out.println("Corpora:\n\t[vc]: (V)iew (C)orpora\n\t[ac]: (A)dd (C)orpora");
        System.out.println("KeyboardGeometry:\n\t[vk]: (V)iew (K)eyboardGeometry\n\t[ak]: (A)dd (K)eyboardGeometry");
        System.out.println("Layout:\n\t[vl]: (V)iew (L)ayout\n\t[al]: (A)dd (L)ayout");
        System.out.println("Tournament:\n\t[t]: Create (T)ournament");
        System.out.println("");
    }

    private void displayMenu() {
        System.out.println("Hello! Welcome to the Keyboard Layout Analyser (using) Corpus Crunching");
        System.out.println("a.k.a. K.L.A.C.C! How can I help you today?");
    }

}
