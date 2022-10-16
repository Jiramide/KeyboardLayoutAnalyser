package model.corpora;

public interface CorpusReader {

    char consume();

    boolean isFinished();

}
