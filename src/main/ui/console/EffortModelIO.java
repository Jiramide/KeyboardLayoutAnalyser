package ui.console;

import model.effortmodel.EffortModel;
import java.util.Scanner;

/*
 * A class responsible for IO when it comes to EffortModels.
 */
public class EffortModelIO extends InputOutput<EffortModel> {

    // EFFECTS: constructs a CorpusIO responsible for printing and reading corpora from console.
    public EffortModelIO(Scanner input) {
        super(input);
    }

    @Override
    // EFFECTS: writes the name and description of an EffortModel into the console
    protected void write(int index, EffortModel effortModel) {
        String name = effortModel.getName();
        String desc = effortModel.getDescription();

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
    // EFFECTS: does nothing, EffortModels cannot be read from the console.
    public void read() {

    }
}
