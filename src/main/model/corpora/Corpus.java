package model.corpora;

/*
 * An interface that represents a typed corpus (body of language).
 */
public interface Corpus {

    // EFFECTS: creates a corpus reader that goes through the corpus data
    CorpusReader createCorpusReader();

}
