package model.effortmodel;

import model.corpora.Corpus;

public abstract class EffortModel {

    private Corpus corpus;

    // EFFECTS: creates an EffortModel that simulates on a corpus
    public EffortModel(Corpus corpus) {
        this.corpus = corpus;
    }

    // EFFECTS: computes the total effort of typing
    public double computeTotalEffort() {
        double totalEffort = 0.00;

        while (!corpus.isFinished()) {
            char key = corpus.consume();

            totalEffort += computePartialEffort(key);
        }

        return totalEffort;
    }

    public abstract double computePartialEffort(char key);

}
