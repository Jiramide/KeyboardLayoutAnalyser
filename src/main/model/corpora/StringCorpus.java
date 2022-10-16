package model.corpora;

public class StringCorpus implements Corpus {

    private String content;
    private int index;
    private int contentSize;

    // EFFECTS: constructs a StringCorpus with the string as the content
    public StringCorpus(String content) {
        this.content = content;
        this.index = 0;
        this.contentSize = content.length();
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
