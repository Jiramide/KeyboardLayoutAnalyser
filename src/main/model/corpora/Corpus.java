package model.corpora;

import model.INameable;

/*
 * An interface that represents a typed corpus (body of language).
 */
public interface Corpus extends INameable {

    // EFFECTS: creates a corpus reader that goes through the corpus data
    CorpusReader createCorpusReader();

}
