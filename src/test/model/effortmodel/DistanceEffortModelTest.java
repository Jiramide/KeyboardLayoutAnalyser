package model.effortmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.KeyboardGeometry;
import model.Finger;
import model.Keyboard;
import model.corpora.Corpus;
import model.corpora.StringCorpus;

public class DistanceEffortModelTest {

    private DistanceEffortModel distanceEffortModel;

    private KeyboardGeometry basicKeyboardGeometry;
    private String layout;
    private Keyboard basicKeyboard;
    private Corpus helloWorldCorpus;
    private Corpus emptyCorpus;

    @BeforeEach
    public void initialize() {
        basicKeyboardGeometry = new KeyboardGeometry();

        /*
         * [0:LP] [1:LM] [2:LI]
         * [3:LI] [4:LP] [5:LM]
         *        [6:LP]
         */
        basicKeyboardGeometry
                .withContactPoint(0.00, 0.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, 0.00, Finger.LEFT_MIDDLE)
                .withContactPoint(2.00, 0.00, Finger.LEFT_INDEX)
                .withContactPoint(0.00, 1.00, Finger.LEFT_INDEX)
                .withContactPoint(1.00, 1.00, Finger.LEFT_PINKY)
                .withContactPoint(2.00, 1.00, Finger.LEFT_MIDDLE)
                .withContactPoint(1.00, 2.00, Finger.LEFT_PINKY);

        basicKeyboardGeometry.setInitialFingerPosition(Finger.LEFT_INDEX, 0.00, 0.00);
        basicKeyboardGeometry.setInitialFingerPosition(Finger.LEFT_MIDDLE, 1.00, 0.00);
        basicKeyboardGeometry.setInitialFingerPosition(Finger.LEFT_INDEX, 2.00, 1.00);

        /* [H*] [E*] [L ]
         * [O ] [W ] [R*]
         *     [D ]
         */
        layout = "helowrd";

        helloWorldCorpus = new StringCorpus("Hello world!");
        emptyCorpus = new StringCorpus("");

        basicKeyboard = new Keyboard(basicKeyboardGeometry, layout);

        distanceEffortModel = new DistanceEffortModel();
    }

    @Test
    public void testComputeTotalEffort() {
        // The total distance your fingers travel when typing "Hello world!" is approximately 9.3
        // Here are the distances as you type, ignoring symbols that don't exist in the layout:
        // H: 0
        // e: 0
        // l: 1
        // l: 0
        // o: 2.2360679774998
        // w: 1.4142135623731
        // o: 0
        // r: 1.4142135623731
        // l: 2.2360679774998
        // d: 1
        assertEquals(9.30056307974577, distanceEffortModel.computeTotalEffort(helloWorldCorpus, basicKeyboard));

        assertEquals(0.00, distanceEffortModel.computeTotalEffort(emptyCorpus, basicKeyboard));
    }

}
