package ui;

import model.Layout;

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
}
