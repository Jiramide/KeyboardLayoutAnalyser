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

    private Map<Keyboard, Double> scores;

    // EFFECTS: Creates an empty tournament with the given corpus and effort computation model
    public Tournament(Corpus corpus, EffortModel effortCalculator) {
        this.corpus = corpus;
        this.effortCalculator = effortCalculator;
        this.keyboards = new ArrayList<>();
        this.scores = null;
    }

    private Double computeScore(Keyboard keyboard) {
        return effortCalculator.computeTotalEffort(corpus, keyboard);
    }

    // MODIFIES: this
    // EFFECTS: returns a map associated a keyboard and the total effort spent typing the given corpus
    public Map<Keyboard, Double> computeScores() {
        if (scores != null) {
            return scores;
        }

        Map<Keyboard, Double> scores = new HashMap<>();

        for (Keyboard keyboard : keyboards) {
            scores.put(keyboard, computeScore(keyboard));
        }

        this.scores = scores;
        return scores;
    }

    // MODIFIES: this
    // EFFECTS: returns the keyboards sorted by minimum effort
    public List<Keyboard> getSortedRankings() {
        Map<Keyboard, Double> scores = computeScores();

        ArrayList<Keyboard> keyboardsAsArrayList = (ArrayList<Keyboard>) keyboards;
        List<Keyboard> rankings = (List<Keyboard>) keyboardsAsArrayList.clone();

        rankings.sort((a, b) -> (int) (scores.get(a) - scores.get(b)));

        return rankings;
    }

    // EFFECTS: determines if the given keyboard is already in the tournament
    public boolean hasKeyboard(Keyboard keyboard) {
        return keyboards.contains(keyboard);
    }

    // REQUIRES: !hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: adds keyboard into tournament to test against other keyboards
    public void addKeyboard(Keyboard keyboard) {
        keyboards.add(keyboard);
    }

    // REQUIRES: hasKeyboard(keyboard)
    // MODIFIES: this
    // EFFECTS: removes keyboard from the tournament
    public void removeKeyboard(Keyboard keyboard) {
        keyboards.remove(keyboard);
    }

}
