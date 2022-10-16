package model.effortmodel;

import model.corpora.Corpus;
import model.corpora.CorpusReader;

public abstract class EffortModel {

    // EFFECTS: creates an EffortModel that simulates on a corpus
    public EffortModel() {

    }

    // EFFECTS: computes the total effort of typing
    public double computeTotalEffort(Corpus corpus) {
        CorpusReader reader = corpus.createCorpusReader();

        double totalEffort = 0.00;

        while (!reader.isFinished()) {
            char key = reader.consume();

            totalEffort += computePartialEffort(key);
        }

        return totalEffort;
    }

    public abstract double computePartialEffort(char key);

    public abstract void reset();

}
