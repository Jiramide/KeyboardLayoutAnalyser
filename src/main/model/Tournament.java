package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import model.corpora.Corpus;
import model.effortmodel.EffortModel;


public class Tournament {

    private EffortModel effortCalculator;
    private Corpus corpus;
    private List<Keyboard> keyboards;

    // EFFECTS: Creates an empty tournament with the given corpus and effort computation model
    public Tournament(Corpus corpus, EffortModel effortCalculator) {

    }

    private Double computeScore(Keyboard keyboard) {
        return 0.00;
    }

    // EFFECTS: returns a map associated a keyboard and the total effort spent typing the given corpus
    public Map<Keyboard, Double> computeScores() {
        return null;
    }

    // EFFECTS: returns the keyboards sorted by minimum effort
    public List<Keyboard> getSortedRankings() {
        return null;
    }

    // EFFECTS: determines if the given keyboard is already in the tournament
    public boolean hasKeyboard(Keyboard keyboard) {
        return false;
    }

    // REQUIRES: !hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: adds keyboard into tournament to test against other keyboards
    public void addKeyboard(Keyboard keyboard) {

    }

    // REQUIRES: hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: removes keyboard from the tournament
    public void removeKeyboard(Keyboard keyboard) {

    }

}
