package model.effortmodel;

import model.corpora.Corpus;

public abstract class EffortModel {

    // EFFECTS: computes the total effort of typing
    public int computeTotalEffort(Corpus corpus) {
        return 0;
    }

    public abstract int computePartialEffort(char key);

}
