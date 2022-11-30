package model.logging;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.*;

import model.corpora.Corpus;
import model.corpora.StringCorpus;
import model.effortmodel.EffortModel;
import model.effortmodel.DistanceEffortModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoggingTest {


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
        basicKeyboardGeometry = new KeyboardGeometry("BasicKeyboardGeometry", "");

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
        helloWorldCorpus = new StringCorpus("Hello world", "Hello world", "Hello world!");

        distanceEffortModel = new DistanceEffortModel();

        helloWorldTournament = new Tournament(helloWorldCorpus, distanceEffortModel);

        constructKeyboardGeometry();

        wordleh = new Keyboard(basicKeyboardGeometry, new Layout("wordleh", "", "wordleh"));
        helowrd = new Keyboard(basicKeyboardGeometry, new Layout("helowrd", "", "helowrd"));
        drwoleh = new Keyboard(basicKeyboardGeometry, new Layout("drwoleh", "", "drwoleh"));
        hwordle = new Keyboard(basicKeyboardGeometry, new Layout("hwordle", "", "hwordle"));
        worldeh = new Keyboard(basicKeyboardGeometry, new Layout("worldeh", "", "worldeh"));

        EventLog.getInstance().clear();
    }

    @Test
    @Order(1)
    public void testLogsAdded() {
        helloWorldTournament.addKeyboard(wordleh);
        helloWorldTournament.addKeyboard(helowrd);
        helloWorldTournament.addKeyboard(drwoleh);
        helloWorldTournament.addKeyboard(hwordle);
        helloWorldTournament.removeKeyboard(wordleh);
        helloWorldTournament.addKeyboard(worldeh);
        helloWorldTournament.removeKeyboard(drwoleh);
        helloWorldTournament.addKeyboard(wordleh);

        List<String> eventDescriptions = new ArrayList<>();

        for (Event event : EventLog.getInstance()) {
            eventDescriptions.add(event.getDescription());
        }

        List<String> expectedEvents = new ArrayList<>();
        expectedEvents.add("Event log cleared.");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + wordleh' to tournament #0");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + helowrd' to tournament #0");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + drwoleh' to tournament #0");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + hwordle' to tournament #0");
        expectedEvents.add("Removed keyboard 'BasicKeyboardGeometry + wordleh' from tournament #0");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + worldeh' to tournament #0");
        expectedEvents.add("Removed keyboard 'BasicKeyboardGeometry + drwoleh' to tournament #0");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + wordleh' to tournament #0");
    }


    @Test
    @Order(2)
    public void testTournamentUIDLogged() {
        helloWorldTournament.addKeyboard(wordleh);
        helloWorldTournament.addKeyboard(helowrd);
        helloWorldTournament.addKeyboard(drwoleh);
        helloWorldTournament.addKeyboard(hwordle);
        helloWorldTournament.removeKeyboard(wordleh);
        helloWorldTournament.addKeyboard(worldeh);
        helloWorldTournament.removeKeyboard(drwoleh);
        helloWorldTournament.addKeyboard(wordleh);

        List<String> eventDescriptions = new ArrayList<>();

        for (Event event : EventLog.getInstance()) {
            eventDescriptions.add(event.getDescription());
        }

        List<String> expectedEvents = new ArrayList<>();
        expectedEvents.add("Event log cleared.");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + wordleh' to tournament #1");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + helowrd' to tournament #1");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + drwoleh' to tournament #1");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + hwordle' to tournament #1");
        expectedEvents.add("Removed keyboard 'BasicKeyboardGeometry + wordleh' from tournament #1");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + worldeh' to tournament #1");
        expectedEvents.add("Removed keyboard 'BasicKeyboardGeometry + drwoleh' to tournament #1");
        expectedEvents.add("Added keyboard 'BasicKeyboardGeometry + wordleh' to tournament #1");
    }

}