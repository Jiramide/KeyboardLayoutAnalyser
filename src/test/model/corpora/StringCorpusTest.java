package model.corpora;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCorpusTest {

    private StringCorpus pangram;
    private StringCorpus helloWorld;

    private CorpusReader pangramReader;
    private CorpusReader helloWorldReader;

    @BeforeEach
    public void initializeCorpora() {
        pangram = new StringCorpus("The quick brown fox jumps over the lazy dog.");
        helloWorld = new StringCorpus("Hello world! Programmed to work and not to feel.");

        pangramReader = pangram.createCorpusReader();
        helloWorldReader = helloWorld.createCorpusReader();
    }

    @Test
    public void testConsume() {
        assertEquals('T', pangramReader.consume());
        assertEquals('h', pangramReader.consume());
        assertEquals('e', pangramReader.consume());
        assertEquals(' ', pangramReader.consume());
        assertEquals('q', pangramReader.consume());

        assertEquals('H', helloWorldReader.consume());
        assertEquals('e', helloWorldReader.consume());

        assertEquals('u', pangramReader.consume());
    }

    @Test
    public void testIsFinished() {
        assertFalse(pangramReader.isFinished());

        // "The quick brown fox jumps over the lazy dog." has 44 characters
        for (int consumptionsLeft = 44; consumptionsLeft > 0; consumptionsLeft--) {
            assertFalse(pangramReader.isFinished());
            pangramReader.consume();
        }

        assertTrue(pangramReader.isFinished());
        assertFalse(helloWorldReader.isFinished());
    }

    @Test
    public void testIndividualityOfMultipleReaders() {
        CorpusReader otherPangramReader = pangram.createCorpusReader();

        assertEquals('T', pangramReader.consume());
        assertEquals('h', pangramReader.consume());
        assertEquals('e', pangramReader.consume());
        assertEquals(' ', pangramReader.consume());
        assertEquals('q', pangramReader.consume());

        assertEquals('T', otherPangramReader.consume());

        while (!pangramReader.isFinished()) {
            pangramReader.consume();
        }

        assertTrue(pangramReader.isFinished());
        assertFalse(otherPangramReader.isFinished());
    }

    @Test
    public void testToJson() {
        /*
         {
            content: "The quick brown fox jumps over the lazy dog."
         }
         */
        JSONObject expected = new JSONObject();

        expected.put("content", "The quick brown fox jumps over the lazy dog.");

        assertEquals(expected, pangram.toJson());
    }

}
