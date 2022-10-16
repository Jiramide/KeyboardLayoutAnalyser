package model.effortmodel;

import model.corpora.Corpus;

public abstract class EffortModel {

    // EFFECTS: computes the total effort of typing
    public int computeTotalEffort(Corpus corpus) {
        int totalEffort = 0;

        while (!corpus.isFinished()) {
            char key = corpus.consume();

            totalEffort += computePartialEffort(key);
        }

        return totalEffort;
    }

    public abstract int computePartialEffort(char key);

}
