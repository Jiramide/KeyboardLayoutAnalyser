package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LayoutIO {

    private Scanner input;

    private List<Display<String>> layouts;

    // EFFECTS: creates a LayoutIO that's responsible for handling IO with layouts;
    //          consumes commands from the given scanner
    public LayoutIO(Scanner input) {
        this.input = input;

        layouts = new ArrayList<>();
    }

    // EFFECTS: writes a single layout to console
    private void writeLayout(int layoutIndex, Display<String> layout) {
        String name = layout.getName();
        String keyLayout = layout.getAssociatedObject();

        System.out.print("\t");
        System.out.print(layoutIndex);
        System.out.print(". ");
        System.out.println(name);

        System.out.print("\t\t");
        System.out.println(keyLayout);
    }

    // EFFECTS: writes all layouts to console
    public void writeLayouts() {
        int layoutIndex = 1;

        for (Display<String> layout : layouts) {
            writeLayout(layoutIndex, layout);
            layoutIndex += 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: reads a layout from console and adds it into the list of available layouts
    public void readLayout() {
        System.out.print("Layout name: ");
        String name = input.next();

        System.out.print("Type in the order of the key positions. Note that this program will fill in");
        System.out.println(" keyboard geometries line-by-line, left-to-right.");

        String layout = input.next();
        Display<String> newLayout = new Display<>(name, "", layout);
        layouts.add(newLayout);

        System.out.println("Successfully added Layout '" + name + "'!");
    }

    // REQUIRES: name is in layouts
    // EFFECTS: returns the layout with the given name
    public String getLayout(String name) {
        for (Display<String> layout : layouts) {
            if (layout.getName().equals(name)) {
                return layout.getAssociatedObject();
            }
        }

        return null;
    }

    // REQUIRES: name is in layouts
    // MODIFIES: this
    // EFFECTS: removes the layout with the given name
    public void removeLayout(String name) {
        for (Display<String> layout : layouts) {
            if (layout.getName().equals(name)) {
                layouts.remove(layout);
                return;
            }
        }
    }

}
