package model.corpora;

import model.Nameable;

/*
 * An interface that represents a typed corpus (body of language).
 */
public interface Corpus extends Nameable {

    // EFFECTS: creates a corpus reader that goes through the corpus data
    CorpusReader createCorpusReader();

}
