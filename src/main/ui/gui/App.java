package ui.gui;

import model.AppState;

/*
 * A class that represents the application. Stores application wide data
 */
public class App {

    private final MainWindow mainWindow = new MainWindow();
    private final AppState appState = new AppState();

    // EFFECTS: returns the main window the app is using
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    // EFFECTS: returns the app state that the app is using
    public AppState getAppState() {
        return appState;
    }
}
