package model.corpora;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCorpusTest {

    private StringCorpus pangram;
    private StringCorpus helloWorld;

    @BeforeEach
    public void initializeCorpora() {
        pangram = new StringCorpus("The quick brown fox jumps over the lazy dog.");
        helloWorld = new StringCorpus("Hello world! Programmed to work and not to feel.");
    }

    @Test
    public void testConsume() {
        assertEquals('T', pangram.consume());
        assertEquals('h', pangram.consume());
        assertEquals('e', pangram.consume());
        assertEquals(' ', pangram.consume());
        assertEquals('q', pangram.consume());

        assertEquals('H', helloWorld.consume());
        assertEquals('e', helloWorld.consume());

        assertEquals('u', pangram.consume());
    }

    @Test
    public void testIsFinished() {
        assertFalse(pangram.isFinished());

        // "The quick brown fox jumps over the lazy dog." has 44 characters
        for (int consumptionsLeft = 44; consumptionsLeft > 0; consumptionsLeft--) {
            pangram.consume();
        }

        assertTrue(pangram.isFinished());
    }

}
