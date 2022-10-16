package model.effortmodel;

import model.corpora.Corpus;

public abstract class EffortModel {

    private Corpus corpus;

    // EFFECTS: creates an EffortModel that simulates on a corpus
    public EffortModel(Corpus corpus) {
        this.corpus = corpus;
    }

    // EFFECTS: computes the total effort of typing
    public int computeTotalEffort() {
        int totalEffort = 0;

        while (!corpus.isFinished()) {
            char key = corpus.consume();

            totalEffort += computePartialEffort(key);
        }

        return totalEffort;
    }

    public abstract int computePartialEffort(char key);

}
