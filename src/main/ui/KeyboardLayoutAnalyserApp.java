package ui;

import model.Finger;
import model.Keyboard;
import model.KeyboardGeometry;
import model.Tournament;
import model.corpora.Corpus;
import model.corpora.StringCorpus;
import model.effortmodel.DistanceEffortModel;
import model.effortmodel.EffortModel;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class KeyboardLayoutAnalyserApp {

    private static final double CHAR_SIZE_IN_UNITS = 0.10;
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

        addDefaultsForLayout();
        addDefaultsForCorpus();
        addDefaultForKeyboardGeometry();
        addDefaultsForEffortModel();

        keepTakingCommands = true;

        displayGreeting();
        processCommands();
    }

    private void addDefaultsForLayout() {
        layoutIO.add(new Display<>("QWERTY", "", "qwertyuiopasdfghjkl;zxcvbnm,./ "));
        layoutIO.add(new Display<>("Colemak-DH", "", "qwfpbjluy;arstgmneioxcdvzkh,./ "));
        layoutIO.add(new Display<>("Dvorak", "", "',.pyfgcrlaoeuidhtns;qjkxbmwvz "));
        layoutIO.add(new Display<>("Workman", "", "qdrwbjfup;ashfgyneoizxmcvkl,./ "));
    }

    private void addDefaultsForCorpus() {
        corpusIO.add(new Display<>("Hello world", "Hello world", new StringCorpus("Hello world")));
    }

    private void addDefaultForKeyboardGeometry() {

    }

    private void addDefaultsForEffortModel() {
        effortModelIO.add(new Display<>(
                "DistanceEffortModel",
                "Computes total effort by computing how much distance your fingers travel",
                new DistanceEffortModel()
        ));
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

    private boolean processRemoveCommand(String command) {
        if (command.equals("rl")) {
            layoutIO.remove("Layout name: ");
        } else if (command.equals("rc")) {
            corpusIO.remove("Corpus name: ");
        } else if (command.equals("rk")) {
            keyboardGeometryIO.remove("KeyboardGeometry name: ");
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
        // Corpora commands
        System.out.println("Corpora:");
        System.out.println("\t[vc]: (V)iew (C)orpora");
        System.out.println("\t[ac]: (A)dd (C)orpora");
        System.out.println("\t[rc]: (R)emove (C)orpora");

        // KeyboardGeometry commands
        System.out.println("KeyboardGeometry:");
        System.out.println("\t[vk]: (V)iew (K)eyboardGeometry");
        System.out.println("\t[ak]: (A)dd (K)eyboardGeometry");
        System.out.println("\t[rk]: (R)emove (K)eyboardGeometry");

        // Layout commands
        System.out.println("Layout:");
        System.out.println("\t[vl]: (V)iew (L)ayout");
        System.out.println("\t[al]: (A)dd (L)ayout");
        System.out.println("\t[rl]: (R)emove (L)ayout");

        // EffortModel commands
        System.out.println("EffortModel:");
        System.out.println("\t[ve]: (V)iew (E)ffortModel");

        // Tournament commands
        System.out.println("Tournament:");
        System.out.println("\t[t]: Run tournament");

        // Misc. commands
        System.out.println("General:");
        System.out.println("\t[q]: Quit");
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
