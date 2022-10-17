package ui;

import model.effortmodel.DistanceEffortModel;
import model.effortmodel.EffortModel;

import java.util.Scanner;

public class EffortModelIO extends InputOutput<EffortModel> {

    // EFFECTS: constructs a CorpusIO responsible for printing and reading corpora from console.
    public EffortModelIO(Scanner input) {
        super(input);
    }

    @Override
    protected void write(int index, Display<EffortModel> effortModelDisplay) {
        String name = effortModelDisplay.getName();
        String desc = effortModelDisplay.getDescription();

        // print name
        System.out.print("\t");
        System.out.print(index);
        System.out.print(". ");
        System.out.println(name);

        // print description
        System.out.print("\t\t");
        System.out.println(desc);
    }

    @Override
    public void read() {

    }
}
