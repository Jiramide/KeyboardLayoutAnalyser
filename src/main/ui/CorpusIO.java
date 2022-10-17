package ui;

import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CorpusIO {

    private static final int MAX_CORPUS_CONTENT_LENGTH = 500;

    private Scanner input;

    private List<Display<Corpus>> corpora;

    // EFFECTS: constructs a CorpusIO responsible for printing and reading corpora from console.
    public CorpusIO(Scanner input) {
        this.input = input;

        this.corpora = new ArrayList<>();
    }

    // EFFECTS: writes a single corpus into console
    private void writeCorpus(int corpusIndex, Display<Corpus> corpusDisplay) {
        String name = corpusDisplay.getName();
        String desc = corpusDisplay.getDescription();
        Corpus corpus = corpusDisplay.getAssociatedObject();
        CorpusReader reader = corpus.createCorpusReader();

        // print name
        System.out.print("\t");
        System.out.print(corpusIndex);
        System.out.print(". ");
        System.out.println(name);

        // print description
        System.out.print("\t\t");
        System.out.println(desc);

        // print corpus content
        System.out.print("\t\t");

        int charactersConsumed = 0;
        while (!reader.isFinished() && charactersConsumed < MAX_CORPUS_CONTENT_LENGTH) {
            charactersConsumed += 1;
            System.out.print(reader.consume());
        }

        System.out.println((charactersConsumed == MAX_CORPUS_CONTENT_LENGTH) ? ". . ." : "");
    }

    // EFFECTS: writes all corpora into console
    public void writeCorpora() {
        int index = 1;

        for (Display<Corpus> corpusDisplay : corpora) {
            writeCorpus(index, corpusDisplay);
            index += 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: reads a corpus from the console and adds it onto the list of available corpora
    public void readCorpus() {
        System.out.print("Corpus name: ");
        String name = input.next();

        System.out.print("Corpus description: ");
        String desc = input.next();

        System.out.print("Content: ");
        String corpusContent = input.next();

        Display<Corpus> newCorpus = new Display<>(name, desc, new StringCorpus(corpusContent));
        corpora.add(newCorpus);


        System.out.println("Successfully added Corpus '" + name + "'!");
    }

    // REQUIRES: name is in corpora
    // EFFECTS: returns the corpora with the given name
    public Corpus getCorpus(String name) {
        for (Display<Corpus> corpus : corpora) {
            if (corpus.getName().equals(name)) {
                return corpus.getAssociatedObject();
            }
        }

        return null;
    }

    // REQUIRES: name is in corpora
    // MODIFIES: this
    // EFFECTS: removes the corpora with the given name
    public void removeLayout(String name) {
        for (Display<Corpus> corpus : corpora) {
            if (corpus.getName().equals(name)) {
                corpora.remove(corpus);
                return;
            }
        }
    }

}
