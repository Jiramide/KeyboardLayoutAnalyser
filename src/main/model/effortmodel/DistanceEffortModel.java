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
        super(corpus);

        this.keyboard = keyboard;
        this.fingerPositions = new HashMap<>();

        for (Finger finger : Finger.ALL_FINGERS) {
            fingerPositions.put(finger, keyboard.getInitialFingerPosition(finger));
        }
    }

    // REQUIRES: key is in keyboard
    // EFFECTS: computes the effort spent based on the distance spent travelling to press the key
    public double computePartialEffort(char key) {
        key = Character.toLowerCase(key);

        if (!keyboard.hasKey(key)) {
            return 0;
        }

        Finger fingerUsed = keyboard.getKeyFinger(key);
        Coord2D keyPosition = keyboard.getKeyCoord(key);
    }

}
