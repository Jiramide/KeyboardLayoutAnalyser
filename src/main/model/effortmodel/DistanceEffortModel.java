package model.effortmodel;

import java.util.Map;
import java.util.HashMap;

import model.Keyboard;
import model.Coord2D;
import model.Finger;
import model.corpora.Corpus;

public class DistanceEffortModel extends EffortModel {

    private Map<Finger, Coord2D> fingerPositions;
    private Keyboard keyboard;

    // EFFECTS: creates an EffortModel that operates on a corpus with initial finger positions on the home row
    public DistanceEffortModel(Corpus corpus, Keyboard keyboard) {

    }

    // EFFECTS: computes the effort spent based on the distance spent travelling to press the key
    public int computePartialEffort(char key) {
        return 0;
    }

}
