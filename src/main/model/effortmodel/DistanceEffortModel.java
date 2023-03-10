package model.effortmodel;

import java.util.Map;
import java.util.HashMap;

import model.Keyboard;
import model.Coord2D;
import model.Finger;
import model.corpora.Corpus;

/*
 * DistanceEffortModel is an EffortModel that computes total effort
 * spent typing a body of text by calculating the distances required
 * for your fingers to travel.
 */
public class DistanceEffortModel extends EffortModel {

    private Map<Finger, Coord2D> fingerPositions;
    private Keyboard keyboard;

    public DistanceEffortModel() {
        super("DistanceEffortModel", "Computes effort by measuring total distance your fingers travel.");
    }

    @Override
    // MODIFIES: this
    // EFFECTS: computes the total effort spent typing out a corpus
    public double computeTotalEffort(Corpus corpus, Keyboard keyboard) {
        this.keyboard = keyboard;
        this.fingerPositions = new HashMap<>();

        for (Finger finger : Finger.values()) {
            fingerPositions.put(finger, keyboard.getInitialFingerPosition(finger));
        }

        return super.computeTotalEffort(corpus, keyboard);
    }

    @Override
    // REQUIRES: key is in keyboard
    // MODIFIES: this
    // EFFECTS: computes the effort spent based on the distance spent travelling to press the key
    protected double computePartialEffort(char key) {
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
