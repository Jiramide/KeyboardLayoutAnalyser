package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import model.corpora.Corpus;
import model.effortmodel.EffortModel;

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

    // EFFECTS: Creates an empty tournament with the given corpus and effort computation model
    public Tournament(Corpus corpus, EffortModel effortCalculator) {
        this.corpus = corpus;
        this.effortCalculator = effortCalculator;
        this.keyboards = new ArrayList<>();
        this.scores = null;
    }

    // EFFECTS: returns the corpus used for the tournament
    public Corpus getCorpus() {
        return corpus;
    }

    // EFFECTS: returns the effort model used for the tournament
    public EffortModel getEffortModel() {
        return effortCalculator;
    }

    // EFFECTS: returns the keyboards used for the tournament
    public List<Keyboard> getKeyboards() {
        return keyboards;
    }

    // EFFECTS: computes the score for a given keyboard (according to the effort model)
    private Double computeScore(Keyboard keyboard) {
        return getEffortModel().computeTotalEffort(getCorpus(), keyboard);
    }

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
        getKeyboards().add(keyboard);
    }

    // REQUIRES: hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: removes keyboard from the tournament
    public void removeKeyboard(Keyboard keyboard) {
        getKeyboards().remove(keyboard);
    }

}
