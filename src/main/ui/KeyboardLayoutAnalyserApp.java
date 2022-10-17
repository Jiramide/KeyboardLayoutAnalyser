package ui;

import model.Keyboard;
import model.KeyboardGeometry;
import model.Tournament;
import model.corpora.Corpus;
import model.effortmodel.DistanceEffortModel;
import model.effortmodel.EffortModel;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class KeyboardLayoutAnalyserApp {

    private static final double CHAR_SIZE_IN_UNITS = 0.50;
    private static final int MAX_CORPORA_DISPLAY = 10;

    private Scanner input;

    private LayoutIO layoutIO;
    private CorpusIO corpusIO;
    private KeyboardGeometryIO keyboardGeometryIO;
    private EffortModelIO effortModelIO;

    private boolean keepTakingCommands;

    public KeyboardLayoutAnalyserApp() {
        this.input = new Scanner(System.in);

        input.useDelimiter("\n");

        this.layoutIO = new LayoutIO(input);
        this.corpusIO = new CorpusIO(input, MAX_CORPORA_DISPLAY);
        this.keyboardGeometryIO = new KeyboardGeometryIO(input, CHAR_SIZE_IN_UNITS);
        this.effortModelIO = new EffortModelIO(input);

        effortModelIO.addEffortModel(new Display<>(
                "DistanceEffortModel",
                "Computes total effort by computing how much distance your fingers travel",
                new DistanceEffortModel()
        ));

        keepTakingCommands = true;

        displayGreeting();
        processCommands();
    }

    private void processCommands() {
        while (keepTakingCommands) {
            displayCommands();
            String command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            processCommand(command);
        }
    }

    private boolean processViewCommand(String command) {
        if (command.equals("vl")) {
            layoutIO.writeAll();
        } else if (command.equals("vc")) {
            corpusIO.writeAll();
        } else if (command.equals("vk")) {
            keyboardGeometryIO.writeAll();
        } else if (command.equals("ve")) {
            effortModelIO.writeAll();
        } else {
            return false;
        }

        return true;
    }

    private boolean processAddCommand(String command) {
        if (command.equals("al")) {
            layoutIO.read();
        } else if (command.equals("ac")) {
            corpusIO.read();
        } else if (command.equals("ak")) {
            keyboardGeometryIO.read();
        } else {
            return false;
        }

        return true;
    }

    private void processCommand(String command) {
        boolean processedByView = processViewCommand(command);
        boolean processedByAdd = processAddCommand(command);

        if (!processedByView && !processedByAdd) {
            if (command.equals("t")) {
                runTournament();
            } else if (command.equals("q")) {
                quit();
            } else {
                alertNotValidCommand(command);
            }
        }
    }

    private Tournament createTournament() {
        System.out.println("Creating tournament!");

        Corpus corpus = corpusIO.query("Corpus name: ");
        EffortModel effortModel = effortModelIO.query("EffortModel name: ");

        Tournament tournament = new Tournament(corpus, effortModel);

        System.out.print("How many keyboards?: ");

        int keyboards = input.nextInt();

        for (int keyboardIndex = 0; keyboardIndex < keyboards; keyboardIndex++) {
            KeyboardGeometry geometry = keyboardGeometryIO.query("KeyboardGeometry name: ");
            String layout = layoutIO.query("Layout name: ");

            Keyboard newKeyboard = new Keyboard(geometry, layout);
            tournament.addKeyboard(newKeyboard);
        }

        return tournament;
    }

    private void runTournament() {
        Tournament tournament = createTournament();
        Map<Keyboard, Double> scores = tournament.computeScores();
        List<Keyboard> ranking = tournament.getSortedRankings();

        int index = 1;

        for (Keyboard keyboard : ranking) {
            KeyboardGeometry geometry = keyboard.getGeometry();
            String layout = keyboard.getLayout();

            String geometryName = keyboardGeometryIO.getNameByObject(geometry);
            String layoutName = layoutIO.getNameByObject(layout);

            System.out.print("\t");
            System.out.print(index);
            System.out.print(". Geometry '");
            System.out.print(geometryName);
            System.out.print("' with Layout '");
            System.out.print(layoutName);
            System.out.print("':\t");
            System.out.println(scores.get(keyboard));

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
        System.out.println("EffortMode:\n\t[ve]: (V)iew (E)ffortModel");
        System.out.println("Tournament:\n\t[t]: Create (T)ournament");
        System.out.println("General:\n\t[q]: Quit");
        System.out.println("");
    }

    private void displayGreeting() {
        System.out.println("Hello! Welcome to the Keyboard Layout Analyser (using) Corpus Crunching");
        System.out.println("a.k.a. K.L.A.C.C! How can I help you today?");
    }

    private void quit() {
        System.out.println("Bye folks! Happy typing!");

        keepTakingCommands = false;
    }

}
