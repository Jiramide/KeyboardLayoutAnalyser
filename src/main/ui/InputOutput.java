package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class InputOutput<T> {


    private Scanner input;

    private List<Display<T>> objects;

    public InputOutput() {
        this.objects = new ArrayList<>();
    }

    protected void add(Display<T> object) {
        objects.add(object);
    }

    protected abstract void write(int index, Display<T> objectToWrite);

    public void writeAll() {
        int index = 1;

        for (Display<T> display : objects) {
            write(index, display);
            index += 1;
        }
    }

    public abstract void read();

    public T getByName(String name) {
        for (Display<T> display : objects) {
            if (display.getName().equals(name)) {
                return display.getAssociatedObject();
            }
        }

        return null;
    }

    public void removeByName(String name) {
        for (Display<T> display : objects) {
            if (display.getName().equals(name)) {
                objects.remove(display);
                return;
            }
        }
    }

}
