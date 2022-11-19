package model;

import java.util.ArrayList;
import java.util.List;

import model.corpora.Corpus;
import model.effortmodel.EffortModel;

/*
 * A class that holds all of the application state
 */
public class AppState {

    private final PersistentListState<Corpus> corpora = new PersistentListState<>();
    private final PersistentListState<EffortModel> effortModels = new PersistentListState<>();
    private final PersistentListState<KeyboardGeometry> keyboardGeometries = new PersistentListState<>();
    private final PersistentListState<Layout> layouts = new PersistentListState<>();
    private final List<Tournament> tournaments = new ArrayList<>();

    // EFFECTS: returns the list of corpora that the app is using
    public PersistentListState<Corpus> getCorpora() {
        return corpora;
    }

    // EFFECTS: returns the list of effort models that the app is using
    public PersistentListState<EffortModel> getEffortModels() {
        return effortModels;
    }

    // EFFECTS: returns the list of keyboard geometries that the app is using
    public PersistentListState<KeyboardGeometry> getKeyboardGeometries() {
        return keyboardGeometries;
    }

    // EFFECTS: returns the list of layouts that the app is using
    public PersistentListState<Layout> getLayouts() {
        return layouts;
    }

    // EFFECTS: returns the list of tournaments that the app is using
    public List<Tournament> getTournaments() {
        return tournaments;
    }

}
