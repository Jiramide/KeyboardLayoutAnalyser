package model.corpora;

public class StringCorpus implements Corpus {

    private String content;
    private int index;

    // EFFECTS: constructs a StringCorpus with the string as the content
    public StringCorpus(String content) {

    }

    // MODIFIES: this
    // EFFECTS: "consumes" the next character from the corpus, returning it and moving onto the next
    //          character. Once a character is consumed, it cannot be returned to again.
    public char consume() {
        return '\0';
    }

    // EFFECTS: determines if the corpus is finished (i.e. out of content)
    public boolean isFinished() {
        return false;
    }

}
