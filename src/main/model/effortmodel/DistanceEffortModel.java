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
    public DistanceEffortModel(Keyboard keyboard) {
        super();

        this.keyboard = keyboard;
        this.fingerPositions = new HashMap<>();

        for (Finger finger : Finger.ALL_FINGERS) {
            fingerPositions.put(finger, keyboard.getInitialFingerPosition(finger));
        }
    }

    @Override
    public double computeTotalEffort(Corpus corpus, Keyboard keyboard) {
        return 0.0;
    }

    @Override
    // REQUIRES: key is in keyboard
    // MODIFIES: this
    // EFFECTS: computes the effort spent based on the distance spent travelling to press the key
    public double computePartialEffort(char key) {
        key = Character.toLowerCase(key);

        if (!keyboard.hasKey(key)) {
            return 0;
        }

        Finger fingerUsed = keyboard.getKeyFinger(key);
        Coord2D keyPosition = keyboard.getKeyCoord(key);
        Coord2D currentFingerPosition = fingerPositions.get(fingerUsed);

        double distanceTravelled = keyPosition.subtract(currentFingerPosition).getMagnitude();
        fingerPositions.put(fingerUsed, keyPosition);

        return distanceTravelled;
    }

}
