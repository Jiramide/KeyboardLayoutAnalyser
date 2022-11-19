package ui.gui;

import model.AppState;
import model.effortmodel.DistanceEffortModel;
import java.util.Scanner;

/*
 * A class that represents the application. Stores application wide data
 */
public class App {

    private final MainWindow mainWindow = new MainWindow(this);
    private final AppState appState = new AppState();
    private final Scanner scanner = new Scanner(System.in);

    // EFFECTS: creates an App and inserts defaults
    public App() {
        appState.getEffortModels().add(new DistanceEffortModel());
        scanner.useDelimiter("\n");

        mainWindow.addPages();
    }

    // EFFECTS: returns the main window the app is using
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    // EFFECTS: returns the app state that the app is using
    public AppState getAppState() {
        return appState;
    }

    // EFFECTS: returns the scanner that the app is using
    public Scanner getScanner() {
        return scanner;
    }

}
