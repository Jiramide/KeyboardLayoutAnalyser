package persistence;

import static org.junit.jupiter.api.Assertions.*;

import model.Finger;
import model.KeyboardGeometry;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Coord2D;
import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;
import model.Layout;

import java.io.IOException;

public class ReaderTest {

    private KeyboardGeometry standardAlphaKeyboard;

    @BeforeEach
    public void initialize() {
        standardAlphaKeyboard = new KeyboardGeometry("StandardAlphaKeyboard", "Standard keyboard");

        // Construct standard keyboard, with Q as the origin
        // Insert top row
        standardAlphaKeyboard
                .withContactPoint(0.00, 0.00, Finger.LEFT_PINKY)
                .withContactPoint(1.00, 0.00, Finger.LEFT_RING)
                .withContactPoint(2.00, 0.00, Finger.LEFT_MIDDLE)
                .withContactPoint(3.00, 0.00, Finger.LEFT_INDEX)
                .withContactPoint(4.00, 0.00, Finger.LEFT_INDEX)
                .withContactPoint(5.00, 0.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.00, 0.00, Finger.RIGHT_INDEX)
                .withContactPoint(7.00, 0.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.00, 0.00, Finger.RIGHT_RING)
                .withContactPoint(9.00, 0.00, Finger.RIGHT_PINKY);

        // Insert middle row
        standardAlphaKeyboard
                .withContactPoint(0.20, 1.00, Finger.LEFT_PINKY)
                .withContactPoint(1.20, 1.00, Finger.LEFT_RING)
                .withContactPoint(2.20, 1.00, Finger.LEFT_MIDDLE)
                .withContactPoint(3.20, 1.00, Finger.LEFT_INDEX)
                .withContactPoint(4.20, 1.00, Finger.LEFT_INDEX)
                .withContactPoint(5.20, 1.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.20, 1.00, Finger.RIGHT_INDEX)
                .withContactPoint(7.20, 1.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.20, 1.00, Finger.RIGHT_RING)
                .withContactPoint(9.20, 1.00, Finger.RIGHT_PINKY);

        // Insert bottom row
        standardAlphaKeyboard
                .withContactPoint(0.50, 2.00, Finger.LEFT_PINKY)
                .withContactPoint(1.50, 2.00, Finger.LEFT_RING)
                .withContactPoint(2.50, 2.00, Finger.LEFT_MIDDLE)
                .withContactPoint(3.50, 2.00, Finger.LEFT_INDEX)
                .withContactPoint(4.50, 2.00, Finger.LEFT_INDEX)
                .withContactPoint(5.50, 2.00, Finger.RIGHT_INDEX)
                .withContactPoint(6.50, 2.00, Finger.RIGHT_INDEX)
                .withContactPoint(7.50, 2.00, Finger.RIGHT_MIDDLE)
                .withContactPoint(8.50, 2.00, Finger.RIGHT_RING)
                .withContactPoint(9.50, 2.00, Finger.RIGHT_PINKY);

        standardAlphaKeyboard.setInitialFingerPosition(Finger.LEFT_PINKY, 0.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.LEFT_RING, 1.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.LEFT_MIDDLE, 2.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.LEFT_INDEX, 3.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.LEFT_THUMB, 2.50, 3.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.RIGHT_PINKY, 9.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.RIGHT_RING, 8.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.RIGHT_MIDDLE, 7.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.RIGHT_INDEX, 6.20, 1.00);
        standardAlphaKeyboard.setInitialFingerPosition(Finger.RIGHT_THUMB, 0.00, 0.00);

        // Insert space bar
        standardAlphaKeyboard.withContactPoint(2.50, 3.00, Finger.LEFT_THUMB);
    }

    @Test
    public void testReadMissingFile() {
        try {
            Reader reader = new Reader("./data/where_am_i.json");
            reader.readLayout();

            fail("Expected IOException");
        } catch (IOException e) {
            // success!
        } catch (JSONException e) {
            fail("This should not happen; this is an issue with the test rather than the implementation.");
        }
    }

    @Test
    public void testReadIncorrectType() {
        try {
            Reader reader = new Reader("./data/KeyboardGeometries/StandardAlphaKeyboard.json");
            reader.readLayout();

            fail("Expected IOException");
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch(JSONException e) {
            // success!
        }
    }

    @Test
    public void testReadCoord2D() {
        try {
            Reader coordReader = new Reader("./data/testReadCoord2D.coord.json");
            Coord2D readCoord = coordReader.readCoord2D();

            assertEquals(-138042.23502, readCoord.getX(), 0.01);
            assertEquals(12340.2035, readCoord.getY(), 0.01);
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (JSONException e) {
            fail("Unexpected JSONException");
        }
    }

    @Test
    public void testReadLayout() {
        try {
            Reader layoutReader = new Reader("./data/Layout/QWERTY.layout.json");
            Layout ly = layoutReader.readLayout();

            assertEquals("QWERTY", ly.getName());
            assertEquals("The quintessential QWERTY layout", ly.getDescription());
            assertEquals("qwertyuiopasdfghjkl;zxcvbnm,./ ", ly.getLayoutString());
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (JSONException e) {
            fail("Unexpected JSONException");
        }
    }

    @Test
    public void testReadCorpus() {
        try {
            Reader corpusReader = new Reader("./data/Corpora/LoremIpsum.corpus.json");
            Corpus lorem = corpusReader.readStringCorpus();

            assertEquals("Lorem Ipsum", lorem.getName());
            assertEquals(
                    "The defacto standard dummy text used in the printing world; generated using lipsum.com",
                    lorem.getDescription()
            );

            CorpusReader reader = lorem.createCorpusReader();
            StringBuilder contentBuilder = new StringBuilder();

            for (int charIndex = 0; charIndex < 100; charIndex++) {
                contentBuilder.append(reader.consume());
            }

            String firstHundredLorem = contentBuilder.toString();

            assertEquals(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porta laoreet orci, at mattis magna c",
                    firstHundredLorem
            );
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (JSONException e) {
            fail("Unexpected JSONException");
        }
    }

    @Test
    public void testReadKeyboardGeometry() {
        try {
            Reader kgReader = new Reader("./data/KeyboardGeometries/StandardAlphaKeyboard.json");
            KeyboardGeometry kg = kgReader.readKeyboardGeometry();

            assertEquals("StandardAlphaKeyboard", kg.getName());
            assertEquals("Standard keyboard", kg.getDescription());
            assertEquals(31, kg.getNumContactPoints());

            for (int keyIndex = 0; keyIndex < 31; keyIndex++) {
                Coord2D key1Coord = standardAlphaKeyboard.getCoord(keyIndex);
                Coord2D key2Coord = kg.getCoord(keyIndex);

                Finger key1Finger = standardAlphaKeyboard.getFingerAssignment(key1Coord);
                Finger key2Finger = kg.getFingerAssignment(key2Coord);

                assertEquals(key1Coord.getX(), key2Coord.getX(), 0.01);
                assertEquals(key1Coord.getY(), key2Coord.getY(), 0.01);
                assertEquals(key1Finger, key2Finger);
            }

            for (int fingerIndex = 0; fingerIndex < 10; fingerIndex++) {
                Finger finger = Finger.fromFingerIndex(fingerIndex);

                Coord2D initialPosition1 = standardAlphaKeyboard.getInitialFingerPosition(finger);
                Coord2D initialPosition2 = kg.getInitialFingerPosition(finger);

                assertEquals(initialPosition1.getX(), initialPosition2.getX(), 0.01);
                assertEquals(initialPosition1.getY(), initialPosition2.getY(), 0.01);
            }
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (JSONException e) {
            fail("Unexpected JSONException");
        }
    }

}
