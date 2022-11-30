package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import model.corpora.Corpus;
import model.effortmodel.EffortModel;
import model.logging.Event;
import model.logging.EventLog;

/*
 * A class that represents a Tournament between keyboard layouts
 * Allows arbitrary addition of keyboards into a tournament
 */
public class Tournament {

    private static final int SORTING_MULTIPLIER = 1000000;

    private EffortModel effortCalculator;
    private Corpus corpus;
    private List<Keyboard> keyboards;

    private Map<Keyboard, Double> scores;

    // EFFECTS: Creates a tournament with no corpus and no effort computation model
    public Tournament() {
        this.keyboards = new ArrayList<>();
    }

    // EFFECTS: Creates an empty tournament with the given corpus and effort computation model
    public Tournament(Corpus corpus, EffortModel effortCalculator) {
        this();

        setCorpus(corpus);
        setEffortModel(effortCalculator);
    }

    // EFFECTS: returns the corpus used for the tournament
    public Corpus getCorpus() {
        return corpus;
    }

    // MODIFIES: this
    // EFFECTS: sets the corpus the tournament uses
    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
    }

    // EFFECTS: returns the effort model used for the tournament
    public EffortModel getEffortModel() {
        return effortCalculator;
    }

    // MODIFIES: this
    // EFFECTS: sets the effort model the tournament uses
    public void setEffortModel(EffortModel effortModel) {
        this.effortCalculator = effortModel;
    }

    // EFFECTS: returns the keyboards used for the tournament
    public List<Keyboard> getKeyboards() {
        return keyboards;
    }

    // REQUIRES: getEffortModel() != null, getCorpus() != null
    // EFFECTS: computes the score for a given keyboard (according to the effort model)
    private Double computeScore(Keyboard keyboard) {
        return getEffortModel().computeTotalEffort(getCorpus(), keyboard);
    }

    // REQUIRES: getEffortModel() != null, getCorpus() != null
    // MODIFIES: this
    // EFFECTS: returns a map associated a keyboard and the total effort spent typing the given corpus
    public Map<Keyboard, Double> computeScores() {
        if (scores != null) {
            return scores;
        }

        Map<Keyboard, Double> scores = new HashMap<>();

        for (Keyboard keyboard : getKeyboards()) {
            scores.put(keyboard, computeScore(keyboard));
        }

        this.scores = scores;
        return scores;
    }

    // REQUIRES: getEffortModel() != null, getCorpus() != null
    // MODIFIES: this
    // EFFECTS: returns the keyboards sorted by minimum effort
    public List<Keyboard> getSortedRankings() {
        Map<Keyboard, Double> scores = computeScores();

        ArrayList<Keyboard> keyboardsAsArrayList = (ArrayList<Keyboard>) getKeyboards();
        List<Keyboard> rankings = (List<Keyboard>) keyboardsAsArrayList.clone();

        rankings.sort((a, b) -> (int) (SORTING_MULTIPLIER * scores.get(a) - SORTING_MULTIPLIER * scores.get(b)));

        return rankings;
    }

    // EFFECTS: determines if the given keyboard is already in the tournament
    public boolean hasKeyboard(Keyboard keyboard) {
        return getKeyboards().contains(keyboard);
    }

    // REQUIRES: !hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: adds keyboard into tournament to test against other keyboards
    public void addKeyboard(Keyboard keyboard) {
        EventLog.getInstance().logEvent(
                new Event(
                        "Added keyboard '"
                        + keyboard.getGeometry().getName() + " + " + keyboard.getLayout().getName()
                        + "' to tournament."
                )
        );

        getKeyboards().add(keyboard);
    }

    // REQUIRES: hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: removes keyboard from the tournament
    public void removeKeyboard(Keyboard keyboard) {
        EventLog.getInstance().logEvent(
                new Event(
                        "Removed keyboard '"
                                + keyboard.getGeometry().getName() + " + " + keyboard.getLayout().getName()
                                + "' from tournament."
                )
        );

        getKeyboards().remove(keyboard);
    }

}
