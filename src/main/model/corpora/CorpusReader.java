package model.corpora;

public interface CorpusReader {

    // MODIFIES: this
    // EFFECTS: consumes a character from the corpus, returning it. Once a character is consumed, you cannot
    //          go back to it
    char consume();

    // EFFECTS: determines if the CorpusReader has finished reading through all the data
    boolean isFinished();

}
