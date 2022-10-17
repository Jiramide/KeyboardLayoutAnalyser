package ui;

import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CorpusIO extends InputOutput<Corpus> {

    private final int maxCorpusDisplay;
    private Scanner input;

    // EFFECTS: constructs a CorpusIO responsible for printing and reading corpora from console.
    public CorpusIO(Scanner input, int maxCorpusDisplay) {
        super(input);

        this.maxCorpusDisplay = maxCorpusDisplay;
        this.input = input;
    }

    @Override
    // EFFECTS: writes a single corpus into console
    protected void write(int corpusIndex, Display<Corpus> corpusDisplay) {
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
        while (!reader.isFinished() && charactersConsumed < maxCorpusDisplay) {
            charactersConsumed += 1;
            System.out.print(reader.consume());
        }

        System.out.println((charactersConsumed == maxCorpusDisplay) ? ". . ." : "");
    }

    @Override
    // MODIFIES: this
    // EFFECTS: reads a corpus from the console and adds it onto the list of available corpora
    public void read() {
        System.out.print("Corpus name: ");
        String name = input.next();

        System.out.print("Corpus description: ");
        String desc = input.next();

        System.out.print("Content: ");
        String corpusContent = input.next();

        Display<Corpus> newCorpus = new Display<>(name, desc, new StringCorpus(corpusContent));
        add(newCorpus);

        System.out.println("Successfully added Corpus '" + name + "'!");
    }
}
