package model.effortmodel;

import model.Keyboard;
import model.corpora.Corpus;
import model.corpora.CorpusReader;

public abstract class EffortModel {

    // EFFECTS: creates an EffortModel that simulates on a corpus
    public EffortModel() {

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

    abstract double computePartialEffort(char key);


}
