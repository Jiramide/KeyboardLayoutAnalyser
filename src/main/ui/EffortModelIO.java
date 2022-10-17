package ui;

import model.effortmodel.DistanceEffortModel;
import model.effortmodel.EffortModel;

public class EffortModelIO extends InputOutput<EffortModel> {

    // EFFECTS: constructs a CorpusIO responsible for printing and reading corpora from console.
    public EffortModelIO() {
        super();
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

    public void addEffortModel(Display<EffortModel> effortModelDisplay) {
        add(effortModelDisplay);
    }
}
