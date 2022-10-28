package ui;

import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * A class that's responsible for dealing with IO when it comes to Corpora
 */
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
    protected void write(int corpusIndex, Corpus corpus) {
        String name = corpus.getName();
        String desc = corpus.getDescription();
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

        Corpus newCorpus = new StringCorpus(name, desc, corpusContent);
        add(newCorpus);

        System.out.println("Successfully added Corpus '" + name + "'!");
    }

    // MODIFIES: this
    // EFFECTS: reads a file, parses it into a Corpus and stores it
    public void loadFromFile(String name) {
        Path path = Paths.get(".", "data", "Corpora", name + ".corpus.json");
        Reader reader = new Reader(path.toString());

        try {
            Corpus loaded = reader.readStringCorpus();
            add(loaded);
        } catch (IOException e) {
            System.out.println("[ERROR]: That wasn't a valid file!");
        } catch (JSONException e) {
            System.out.println("[ERROR]: The file you are reading is not a Corpus.");
        }
    }

    // EFFECTS: writes the Corpus with the given name to file
    public void saveToFile(String name) {
        Corpus toSave = getByName(name);
        Path path = Paths.get(".", "data", "Corpora", name + ".corpus.json");
        Writer writer = new Writer(path.toString());

        try {
            writer.open();
            writer.write(toSave);
        } catch (IOException e) {
            System.out.println("[ERROR]: Something has gone wrong while saving a Corpus.");
        }
    }
}
