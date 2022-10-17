package model.corpora;

/*
 * The StringCorpus class is a class that implements a Corpus.
 * It holds Corpus data using a String.
 */
public class StringCorpus implements Corpus {

    private String content;
    private int contentSize;

    // EFFECTS: constructs a StringCorpus with the string as the content
    public StringCorpus(String content) {
        this.content = content;
        this.contentSize = content.length();
    }

    // EFFECTS: Creates a CorpusReader responsible for going through corpus data
    public StringCorpusReader createCorpusReader() {
        return new StringCorpusReader();
    }

    /*
     * The StringCorpusReader is a class responsible for reading through a StringCorpus
     */
    private class StringCorpusReader implements CorpusReader {

        private int index;

        public StringCorpusReader() {
            this.index = 0;
        }

        // REQUIRES: !isFinished()
        // MODIFIES: this
        // EFFECTS: "consumes" the next character from the corpus, returning it and moving onto the next
        //          character. Once a character is consumed, it cannot be returned to again.
        public char consume() {
            char consumedChar = content.charAt(index);
            index += 1;

            return consumedChar;
        }

        // EFFECTS: determines if the corpus is finished (i.e. out of content)
        public boolean isFinished() {
            return index >= contentSize;
        }

    }

}
