package model.effortmodel;

import model.Keyboard;
import model.Nameable;
import model.corpora.Corpus;
import model.corpora.CorpusReader;

/*
 * An effort model is a model used to compute effort spent typing. Effort is the primary
 * metric used to compare keyboard layouts.
 */
public abstract class EffortModel extends Nameable {

    // EFFECTS: creates an EffortModel that simulates on a corpus
    public EffortModel(String name, String description) {
        super(name, description);
    }

    // EFFECTS: computes the total effort of typing
    public double computeTotalEffort(Corpus corpus, Keyboard keyboard) {
        CorpusReader reader = corpus.createCorpusReader();

        double totalEffort = 0.00;

        while (!reader.isFinished()) {
            char key = reader.consume();

            totalEffort += computePartialEffort(key);
        }

        return totalEffort;
    }

    protected abstract double computePartialEffort(char key);


}
