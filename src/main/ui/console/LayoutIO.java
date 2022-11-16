package ui.console;

import model.Layout;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * The class responsible for IO when it comes to Layouts
 */
public class LayoutIO extends InputOutput<Layout> {

    private Scanner input;

    // EFFECTS: creates a LayoutIO that's responsible for handling IO with layouts;
    //          consumes commands from the given scanner
    public LayoutIO(Scanner input) {
        super(input);

        this.input = input;
    }

    // EFFECTS: writes a single layout to console
    protected void write(int layoutIndex, Layout layout) {
        String name = layout.getName();
        String keyLayout = layout.getLayoutString();

        System.out.print("\t");
        System.out.print(layoutIndex);
        System.out.print(". ");
        System.out.println(name);

        System.out.print("\t\t");
        System.out.println(keyLayout);
    }

    // MODIFIES: this
    // EFFECTS: reads a layout from console and adds it into the list of available layouts
    public void read() {
        System.out.print("Layout name: ");
        String name = input.next();

        System.out.print("Type in the order of the key positions. Note that this program will fill in");
        System.out.println(" keyboard geometries line-by-line, left-to-right.");

        String layout = input.next();

        Layout newLayout = new Layout(name, "", layout);

        add(newLayout);

        System.out.println("Successfully added Layout '" + name + "'!");
    }

    // MODIFIES: this
    // EFFECTS: reads a file, parses it into a Layout and stores it
    public void loadFromFile(String name) {
        Path path = Paths.get(".", "data", "Layout", name + ".layout.json");
        Reader reader = new Reader(path.toString());

        try {
            Layout loaded = reader.readLayout();
            add(loaded);
        } catch (IOException e) {
            System.out.println("[ERROR]: That wasn't a valid file!");
        } catch (JSONException e) {
            System.out.println("[ERROR]: The file you are reading is not a Corpus.");
        }
    }

    // EFFECTS: writes the Corpus with the given name to file
    public void saveToFile(String name) {
        Layout toSave = getByName(name);
        Path path = Paths.get(".", "data", "Layout", name + ".layout.json");
        Writer writer = new Writer(path.toString());

        try {
            writer.open();
            writer.write(toSave);
        } catch (IOException e) {
            System.out.println("[ERROR]: Something has gone wrong while saving a Corpus.");
        }
    }
}
