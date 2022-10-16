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

    private DistanceEffortModel distanceOnHelloWorld;
    private DistanceEffortModel emptyDistance;

    private Corpus helloWorldCorpus;
    private KeyboardGeometry basicKeyboardGeometry;
    private Keyboard basicKeyboard;
    private String layout;

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

        basicKeyboard = new Keyboard(basicKeyboardGeometry, layout);

        distanceOnHelloWorld = new DistanceEffortModel(helloWorldCorpus, basicKeyboard);
        emptyDistance = new DistanceEffortModel(new StringCorpus(""), basicKeyboard);
    }

    @Test
    public void testComputePartialEffort() {
        // It takes 0.00 effort to type in the 'h' and 'e' keys (since the fingers are already on them)
        assertEquals(0.00, distanceOnHelloWorld.computePartialEffort('h'));
        assertEquals(0.00, distanceOnHelloWorld.computePartialEffort('e'));

        // It takes 1.00 effort to type in the 'l' key (since the left middle finger has to traverse
        // 1.00 unit). The position of the left middle finger is now at the L key (1.00, 0.00)
        assertEquals(1.00, distanceOnHelloWorld.computePartialEffort('l'));

        // It takes 0.00 effort to type in the 'l' key since the finger is already on the 'l' position
        // after typing in the last one
        assertEquals(0.00, distanceOnHelloWorld.computePartialEffort('l'));

        // The left index finger (responsible for typing 'o') is currently on 'l'. The distance between
        // O(0.00, 1.00) and L(2.00, 0.00) is sqrt(5) = 2.2360679774998. The left index is now on
        // 'o'
        assertEquals(2.2360679774998, distanceOnHelloWorld.computePartialEffort('o'));

        // The left index finger goes back to 'l'. The distance traversed should be the same regardless
        // of direction (i.e. L <-> O = O <-> L)
        assertEquals(2.2360679774998, distanceOnHelloWorld.computePartialEffort('l'));
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
        assertEquals(9.3005630797458, distanceOnHelloWorld.computeTotalEffort());

        assertEquals(0.00, emptyDistance.computeTotalEffort());
    }

}
