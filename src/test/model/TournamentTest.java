package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.corpora.Corpus;
import model.corpora.StringCorpus;
import model.effortmodel.EffortModel;
import model.effortmodel.DistanceEffortModel;

import java.security.Key;
import java.util.List;
import java.util.Map;

public class TournamentTest {

    private Tournament helloWorldTournament;

    private Corpus helloWorldCorpus;

    private EffortModel distanceEffortModel;

    private KeyboardGeometry basicKeyboardGeometry;

    private Keyboard wordleh;
    private Keyboard helowrd;
    private Keyboard drwoleh;
    private Keyboard hwordle;
    private Keyboard worldeh;

    public void constructKeyboardGeometry() {
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
    }

    @BeforeEach
    public void initialize() {
        helloWorldCorpus = new StringCorpus("Hello world!");

        distanceEffortModel = new DistanceEffortModel();

        helloWorldTournament = new Tournament(helloWorldCorpus, distanceEffortModel);

        constructKeyboardGeometry();

        wordleh = new Keyboard(basicKeyboardGeometry, "wordleh");
        helowrd = new Keyboard(basicKeyboardGeometry, "helowrd");
        drwoleh = new Keyboard(basicKeyboardGeometry, "drwoleh");
        hwordle = new Keyboard(basicKeyboardGeometry, "hwordle");
        worldeh = new Keyboard(basicKeyboardGeometry, "worldeh");
    }

    @Test
    public void testAddKeyboard() {
        assertFalse(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.addKeyboard(wordleh);

        assertTrue(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.addKeyboard(hwordle);

        assertTrue(helloWorldTournament.hasKeyboard(wordleh));
        assertTrue(helloWorldTournament.hasKeyboard(hwordle));
    }

    @Test
    public void testRemoveKeyboard() {
        assertFalse(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.addKeyboard(wordleh);

        assertTrue(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.addKeyboard(hwordle);

        assertTrue(helloWorldTournament.hasKeyboard(wordleh));
        assertTrue(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.removeKeyboard(hwordle);

        assertTrue(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.removeKeyboard(wordleh);

        assertFalse(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.addKeyboard(wordleh);

        assertTrue(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));

        helloWorldTournament.addKeyboard(hwordle);

        assertFalse(helloWorldTournament.hasKeyboard(wordleh));
        assertFalse(helloWorldTournament.hasKeyboard(hwordle));
    }

    @Test
    public void testGetScores() {
        helloWorldTournament.addKeyboard(wordleh);
        helloWorldTournament.addKeyboard(helowrd);
        helloWorldTournament.addKeyboard(drwoleh);
        helloWorldTournament.addKeyboard(hwordle);
        helloWorldTournament.addKeyboard(worldeh);

        Map<Keyboard, Double> scores = helloWorldTournament.computeScores();

        /*
         * wordleh: 12.128990204492
         * helowrd: 9.3005630797458
         * drwoleh: 13.950844619619
         * hwordle: 10.714776642119
         * worldeh: 15.186912597118
         */
        assertEquals(12.128990204492, scores.get(wordleh), 0.001);
        assertEquals(9.3005630797458, scores.get(helowrd), 0.001);
        assertEquals(13.950844619619, scores.get(drwoleh), 0.001);
        assertEquals(10.714776642119, scores.get(hwordle), 0.001);
        assertEquals(15.186912597118, scores.get(worldeh), 0.001);

        // Recomputation of scores does not change anything
        scores = helloWorldTournament.computeScores();

        /*
         * wordleh: 12.128990204492
         * helowrd: 9.3005630797458
         * drwoleh: 13.950844619619
         * hwordle: 10.714776642119
         * worldeh: 15.186912597118
         */
        assertEquals(12.128990204492, scores.get(wordleh), 0.001);
        assertEquals(9.3005630797458, scores.get(helowrd), 0.001);
        assertEquals(13.950844619619, scores.get(drwoleh), 0.001);
        assertEquals(10.714776642119, scores.get(hwordle), 0.001);
        assertEquals(15.186912597118, scores.get(worldeh), 0.001);
    }

    @Test
    public void testGetSortedRankings() {
        helloWorldTournament.addKeyboard(wordleh);
        helloWorldTournament.addKeyboard(helowrd);
        helloWorldTournament.addKeyboard(drwoleh);
        helloWorldTournament.addKeyboard(hwordle);
        helloWorldTournament.addKeyboard(worldeh);

        List<Keyboard> sortedRanking = helloWorldTournament.getSortedRankings();

        assertEquals(5, sortedRanking.size());
        assertEquals(helowrd, sortedRanking.get(0));
        assertEquals(hwordle, sortedRanking.get(1));
        assertEquals(wordleh, sortedRanking.get(2));
        assertEquals(drwoleh, sortedRanking.get(3));
        assertEquals(worldeh, sortedRanking.get(4));
    }

}
