package persistence;

import static org.junit.jupiter.api.Assertions.*;

import model.Finger;
import model.KeyboardGeometry;
import org.junit.jupiter.api.Test;


import model.Coord2D;
import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;
import model.Layout;

import java.io.IOException;

public class WriterTest {

    @Test
    public void testReadFromIllegalFileName() {
        try {
            Layout ly = new Layout("a", "b", "c");
            Writer lyWriter = new Writer("./data/\0\0\0\0\0\0\0\0\0\0.layout.json");

            lyWriter.open();

            fail("IOException expected");
        } catch (IOException e) {
            // success!
        }
    }

    @Test
    public void testWriteCoord2D() {
        try {
            Coord2D coord = new Coord2D(3.0, 4.0);
            Writer coordWriter = new Writer("./data/testCoordinate.coord.json");

            coordWriter.open();

            coordWriter.write(coord);

            Reader coordReader = new Reader("./data/testCoordinate.coord.json");
            Coord2D readCoord = coordReader.readCoord2D();

            assertEquals(coord, readCoord);
        } catch (IOException e) {
            fail("IOException should not have been thrown!");
        } catch (InvalidParseException e) {
            fail("This should not happen; this is an issue with the test rather than the implementation.");
        }
    }

    @Test
    public void testWriteLayout() {
        try {
            Layout ly = new Layout(
                    "Alphabetical",
                    "Not a good layout",
                    "abcdefghijklmnopqrstuvwxyz"
            );

            Writer lyWriter = new Writer("./data/testLayout.layout.json");

            lyWriter.open();
            lyWriter.write(ly);

            Reader lyReader = new Reader("./data/testLayout.layout.json");
            Layout readLy = lyReader.readLayout();

            assertEquals(ly.getName(), readLy.getName());
            assertEquals(ly.getDescription(), readLy.getDescription());
            assertEquals(ly.getLayoutString(), readLy.getLayoutString());
        } catch (IOException e) {
            fail("IOException should not have been thrown!");
        } catch (InvalidParseException e) {
            fail("This should not happen; this is an issue with the test rather than the implementation.");
        }
    }

    @Test
    public void testWriteCorpus() {
        try {
            Corpus corpus = new StringCorpus(
                    "Pangram",
                    "A corpus containing all letters at least once",
                    "The quick brown fox jumps over the lazy dog"
            );

            Writer corpusWriter = new Writer("./data/corpora/testCorpora.corpus.json");

            corpusWriter.open();
            corpusWriter.write(corpus);

            Reader corpusReader = new Reader("./data/corpora/testCorpora.corpus.json");
            StringCorpus readCorpus = corpusReader.readStringCorpus();

            CorpusReader reader1 = corpus.createCorpusReader();
            CorpusReader reader2 = readCorpus.createCorpusReader();

            assertEquals(corpus.getName(), readCorpus.getName());
            assertEquals(corpus.getDescription(), readCorpus.getDescription());

            while (!reader1.isFinished()) {
                char readerOneChar = reader1.consume();
                char readerTwoChar = reader2.consume();

                assertEquals(readerOneChar, readerTwoChar);
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown!");
        } catch (InvalidParseException e) {
            fail("This should not happen; this is an issue with the test rather than the implementation.");
        }
    }

    @Test
    public void testWriteKeyboardGeometry() {
        try {
            KeyboardGeometry kg = new KeyboardGeometry("FiveKeyLayout", "Has 5 keys");

            /*
             * [0:LP] [1:LR] [2:LM] [3:LI] [4:LT]
             */
            kg
                    .withContactPoint(new Coord2D(0.00, 0.00), Finger.LEFT_PINKY)
                    .withContactPoint(new Coord2D(1.00, 0.00), Finger.LEFT_RING)
                    .withContactPoint(new Coord2D(2.00, 0.00), Finger.LEFT_MIDDLE)
                    .withContactPoint(new Coord2D(3.00, 0.00), Finger.LEFT_INDEX)
                    .withContactPoint(new Coord2D(4.00, 0.00), Finger.LEFT_THUMB);

            kg.setInitialFingerPosition(Finger.LEFT_PINKY, 0.00, 0.00);
            kg.setInitialFingerPosition(Finger.LEFT_RING, 1.00, 0.00);
            kg.setInitialFingerPosition(Finger.LEFT_MIDDLE, 2.00, 0.00);
            kg.setInitialFingerPosition(Finger.LEFT_INDEX, 3.00, 0.00);
            kg.setInitialFingerPosition(Finger.LEFT_THUMB, 4.00, 0.00);

            Writer kgWriter = new Writer("./data/KeyboardGeometries/testKeyboardGeometry.kg.json");
            kgWriter.open();
            kgWriter.write(kg);

            Reader kgReader = new Reader("./data/KeyboardGeometries/testKeyboardGeometry.kg.json");
            KeyboardGeometry readKg = kgReader.readKeyboardGeometry();

            // Test name / description data
            assertEquals(kg.getName(), readKg.getName());
            assertEquals(kg.getDescription(), readKg.getDescription());

            // Test initial finger positions
            for (Finger finger : Finger.values()) {
                assertEquals(kg.getInitialFingerPosition(finger), readKg.getInitialFingerPosition(finger));
            }

            // Test keys
            assertEquals(kg.getNumContactPoints(), readKg.getNumContactPoints());

            for (int keyIndex = 0; keyIndex < kg.getNumContactPoints(); keyIndex++) {
                Coord2D kg1Coord = kg.getCoord(keyIndex);
                Coord2D kg2Coord = readKg.getCoord(keyIndex);

                assertEquals(kg1Coord.getX(), kg2Coord.getX(), 0.01);
                assertEquals(kg1Coord.getY(), kg2Coord.getY(), 0.01);
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown!");
        } catch (InvalidParseException e) {
            fail("This should not happen; this is an issue with the test rather than the implementation.");
        }
    }

}
